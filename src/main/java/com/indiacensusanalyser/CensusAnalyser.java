package com.indiacensusanalyser;

import com.csvbuilderhandler.CSVBuilderException;
import com.csvbuilderhandler.CSVBuilderFactory;
import com.csvbuilderhandler.ICSVBuilder;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.StreamSupport;

public class CensusAnalyser {

    Map<String, IndiaCensusDAO> indiaCensusDAOMap = null;

    public CensusAnalyser() {
        indiaCensusDAOMap = new HashMap<String, IndiaCensusDAO>();
    }

    public int loadStateCensusData(String csvFilePath) throws CensusAnalyserException {
        return this.loadCensusData(csvFilePath, CensusAnalyserBean.class);
    }

    private <E> int loadCensusData(String csvFilePath, Class<E> censusCSVClass) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<E> csvIterator = csvBuilder.getCSVFileIterator(reader, censusCSVClass);
            Iterable<E> csvIterable = () -> csvIterator;
            StreamSupport.stream(csvIterable.spliterator(), false)
                    .map(CensusAnalyserBean.class::cast)
                    .forEach(censusCSV -> indiaCensusDAOMap.put(censusCSV.state, new IndiaCensusDAO(censusCSV)));
            return indiaCensusDAOMap.size();
        } catch (IOException exception) {
            throw new CensusAnalyserException(CensusAnalyserException.exceptionType.CENSUS_FILE_PROBLEM, "File Not Found");
        } catch (RuntimeException exception) {
            throw new CensusAnalyserException(CensusAnalyserException.exceptionType.WRONG_DELIMETER_HEADER_IN_FILE, "Wrong Delimeter Or Header In File");
        } catch (CSVBuilderException exception) {
            throw new CensusAnalyserException(exception.getMessage(),exception.type.name());
        }
    }

    public int loadStateCodeData(String csvCensusFilePath, String csvStateFilePath) throws CensusAnalyserException{
        this.loadStateCensusData(csvCensusFilePath);
        try (Reader reader = Files.newBufferedReader(Paths.get(csvStateFilePath));){
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<CSVStatesBean> csvStatesBeanIterator = csvBuilder.getCSVFileIterator(reader, CSVStatesBean.class);
            Iterable<CSVStatesBean> csvStatesBeanIterable = () -> csvStatesBeanIterator;
            StreamSupport.stream(csvStatesBeanIterable.spliterator(), false)
                    .filter(stateCSV -> indiaCensusDAOMap.get(stateCSV.statename) != null)
                    .forEach(stateCSV -> indiaCensusDAOMap.get(stateCSV.statename).stateCode = stateCSV.statecode);
            return indiaCensusDAOMap.size();
        }catch (IOException exception){
            throw new CensusAnalyserException(CensusAnalyserException.exceptionType.CENSUS_FILE_PROBLEM,"File Not Found");
        } catch (RuntimeException exception){
            throw new CensusAnalyserException(CensusAnalyserException.exceptionType.WRONG_DELIMETER_HEADER_IN_FILE,"Wrong Delimeter Or Header In File");
        } catch (CSVBuilderException exception) {
            throw new CensusAnalyserException(exception.getMessage(),exception.type.name());
        }
    }

    private <E> int getCount(Iterator<E> iterator){
        Iterable<E> csvIterable = () -> iterator;
        int numOfEnteries = (int) StreamSupport.stream(csvIterable.spliterator(),false).count();
        return numOfEnteries;
    }

    public String getStateWiseSortedCensusData() throws CensusAnalyserException {
        if(indiaCensusDAOMap == null || indiaCensusDAOMap.size() == 0){
            throw new CensusAnalyserException(CensusAnalyserException.exceptionType.NO_CENSUS_DATA,"No Census Data");
        }
        Comparator<IndiaCensusDAO> censusComparator = Comparator.comparing(census -> census.state);
        List<IndiaCensusDAO> sortedStateList  = this.sort(censusComparator, new ArrayList<>(indiaCensusDAOMap.values()));
        String sortedStateCensusJson = new Gson().toJson(sortedStateList);
        return sortedStateCensusJson;
    }

    public String getStateWiseSortedCodeData() throws CensusAnalyserException {
        if(indiaCensusDAOMap == null || indiaCensusDAOMap.size() == 0){
            throw new CensusAnalyserException(CensusAnalyserException.exceptionType.NO_CENSUS_DATA,"No Census Data");
        }
        Comparator<IndiaCensusDAO> censusComparator = Comparator.comparing(census -> census.stateCode);
        List<IndiaCensusDAO> sortedStateCode = this.sort(censusComparator, new ArrayList<>(indiaCensusDAOMap.values()));
        String sortedStateCodeJson = new Gson().toJson(sortedStateCode);
        return sortedStateCodeJson;
    }

    public String getStateWiseMostPopulousState() throws CensusAnalyserException {
        if(indiaCensusDAOMap == null || indiaCensusDAOMap.size() == 0){
            throw new CensusAnalyserException(CensusAnalyserException.exceptionType.NO_CENSUS_DATA,"No Census Data");
        }
        Comparator<IndiaCensusDAO> censusComparator = Comparator.comparing(census -> census.population);
        List<IndiaCensusDAO> sortedStateCode = this.sort(censusComparator, new ArrayList<>(indiaCensusDAOMap.values()));
        String sortedStateCodeJson = new Gson().toJson(sortedStateCode);
        return sortedStateCodeJson;
    }

    public String getStateWiseMostPopulationDensityState() throws CensusAnalyserException {
        if(indiaCensusDAOMap == null || indiaCensusDAOMap.size() == 0){
            throw new CensusAnalyserException(CensusAnalyserException.exceptionType.NO_CENSUS_DATA,"No Census Data");
        }
        Comparator<IndiaCensusDAO> censusComparator = Comparator.comparing(census -> census.densityPerSqKm);
        List<IndiaCensusDAO> sortedStateCode = this.sort(censusComparator, new ArrayList<>(indiaCensusDAOMap.values()));
        String sortedStateCodeJson = new Gson().toJson(sortedStateCode);
        return sortedStateCodeJson;
    }

    public String getStateWiseLargestAndSmallestState() throws CensusAnalyserException {
        if(indiaCensusDAOMap == null || indiaCensusDAOMap.size() == 0){
            throw new CensusAnalyserException(CensusAnalyserException.exceptionType.NO_CENSUS_DATA,"No Census Data");
        }
        Comparator<IndiaCensusDAO> censusComparator = Comparator.comparing(census -> census.areaInSqKm);
        List<IndiaCensusDAO> sortedStateCode = this.sort(censusComparator, new ArrayList<>(indiaCensusDAOMap.values()));
        String sortedStateCodeJson = new Gson().toJson(sortedStateCode);
        return sortedStateCodeJson;
    }

    private <E> List<E> sort(Comparator<E> comparator, List<E> censusComparator){
        for(int i=0;i<censusComparator.size()-i;i++){
            for (int j=0;j<censusComparator.size()-i-1;j++){
                E census1 = censusComparator.get(j);
                E census2 = censusComparator.get(j+1);
                if(comparator.compare(census1,census2) > 0){
                    censusComparator.set(j, census2);
                    censusComparator.set(j+1, census1);
                }
            }
        }
        return censusComparator;
    }
}
