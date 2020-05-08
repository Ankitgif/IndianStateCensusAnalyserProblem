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

    Map<String, CensusAnalyserBean> censusAnalyserBeanMap = null;
    Map<String, CSVStatesBean> csvStatesBeanMap = null;

    public CensusAnalyser() {
        censusAnalyserBeanMap = new HashMap<String, CensusAnalyserBean>();
        csvStatesBeanMap = new HashMap<String, CSVStatesBean>();

    }

    public int loadStateCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<CensusAnalyserBean> censusAnalyserBeanIterator = csvBuilder.getCSVFileIterator(reader, CensusAnalyserBean.class);
            Iterable<CensusAnalyserBean> censusAnalyserBeanIterable = () -> censusAnalyserBeanIterator;
            StreamSupport.stream(censusAnalyserBeanIterable.spliterator(), false)
                          .forEach(censusCSV -> censusAnalyserBeanMap.put(censusCSV.state, censusCSV) );
            return censusAnalyserBeanMap.size();
        } catch (IOException exception) {
            throw new CensusAnalyserException(CensusAnalyserException.exceptionType.CENSUS_FILE_PROBLEM, "File Not Found");
        } catch (RuntimeException exception) {
            throw new CensusAnalyserException(CensusAnalyserException.exceptionType.WRONG_DELIMETER_HEADER_IN_FILE, "Wrong Delimeter Or Header In File");
        } catch (CSVBuilderException exception) {
            throw new CensusAnalyserException(exception.getMessage(),exception.type.name());
        }
    }

    public int loadStateCodeData(String csvFilePath) throws CensusAnalyserException{
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));){
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<CSVStatesBean> csvStatesBeanIterator = csvBuilder.getCSVFileIterator(reader, CSVStatesBean.class);
            Iterable<CSVStatesBean> csvStatesBeanIterable = () -> csvStatesBeanIterator;
            StreamSupport.stream(csvStatesBeanIterable.spliterator(), false)
                    .forEach(censusCSV -> csvStatesBeanMap.put(censusCSV.statecode, censusCSV) );
            return csvStatesBeanMap.size();
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
        if(censusAnalyserBeanMap == null || censusAnalyserBeanMap.size() == 0){
            throw new CensusAnalyserException(CensusAnalyserException.exceptionType.NO_CENSUS_DATA,"No Census Data");
        }
        Comparator<CensusAnalyserBean> censusComparator = Comparator.comparing(census -> census.state);
        List<CensusAnalyserBean> sortedStateList  = this.sort(censusComparator, new ArrayList<>(censusAnalyserBeanMap.values()));
        String sortedStateCensusJson = new Gson().toJson(sortedStateList);
        return sortedStateCensusJson;
    }

    public String getStateWiseSortedCodeData() throws CensusAnalyserException {
        if(csvStatesBeanMap == null || csvStatesBeanMap.size() == 0){
            throw new CensusAnalyserException(CensusAnalyserException.exceptionType.NO_CENSUS_DATA,"No Census Data");
        }
        Comparator<CSVStatesBean> censusComparator = Comparator.comparing(census -> census.statecode);
        List<CSVStatesBean> sortedStateCode = this.sort(censusComparator, new ArrayList<>(csvStatesBeanMap.values()));
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
