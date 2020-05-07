package com.indiacensusanalyser;

import com.csvbuilderhandler.CSVBuilderException;
import com.csvbuilderhandler.CSVBuilderFactory;
import com.csvbuilderhandler.ICSVBuilder;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class CensusAnalyser {

    public int loadCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            List<CensusAnalyserBean> censusAnalyserBeanList = csvBuilder.getCSVFileList(reader, CensusAnalyserBean.class);
            return censusAnalyserBeanList.size();
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
            List<CensusAnalyserBean> csvStatesBeanList = csvBuilder.getCSVFileList(reader, CSVStatesBean.class);
            return csvStatesBeanList.size();
        }catch (IOException exception){
            throw new CensusAnalyserException(CensusAnalyserException.exceptionType.CENSUS_FILE_PROBLEM,"File Not Found");
        } catch (RuntimeException exception){
            throw new CensusAnalyserException(CensusAnalyserException.exceptionType.WRONG_DELIMETER_HEADER_IN_FILE,"Wrong Delimeter Or Header In File");
        } catch (CSVBuilderException exception) {
            throw new CensusAnalyserException(exception.getMessage(),exception.type.name());
        }
    }

    public String getStateWiseSortedCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            List<CensusAnalyserBean> csvStatesBeanList = csvBuilder.getCSVFileList(reader, CensusAnalyserBean.class);
            Comparator<CensusAnalyserBean> censusComparator = Comparator.comparing(census -> census.state);
            this.sort(csvStatesBeanList, censusComparator);
            String sortedStateCensusJson = new Gson().toJson(csvStatesBeanList);
            return sortedStateCensusJson;
        } catch (IOException exception){
            throw new CensusAnalyserException(CensusAnalyserException.exceptionType.CENSUS_FILE_PROBLEM,"File Not Found");
        } catch (CSVBuilderException exception) {
            throw new CensusAnalyserException(exception.getMessage(),exception.type.name());
        }
    }

    private void sort(List<CensusAnalyserBean> censusList, Comparator<CensusAnalyserBean> censusComparator){
        for(int i=0;i<censusList.size()-i;i++){
            for (int j=0;j<censusList.size()-i-1;j++){
                CensusAnalyserBean census1 = censusList.get(j);
                CensusAnalyserBean census2 = censusList.get(j+1);
                if(censusComparator.compare(census1,census2) > 0){
                    censusList.set(j, census2);
                    censusList.set(j+1, census1);
                }
            }
        }
    }
}
