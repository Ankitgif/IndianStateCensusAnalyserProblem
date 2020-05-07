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

    List<CensusAnalyserBean> censusAnalyserBeanList = null;

    public int loadCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            censusAnalyserBeanList = csvBuilder.getCSVFileList(reader, CensusAnalyserBean.class);
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

    public String getStateWiseSortedCensusData() throws CensusAnalyserException {
            if(censusAnalyserBeanList == null || censusAnalyserBeanList.size() == 0){
                throw new CensusAnalyserException(CensusAnalyserException.exceptionType.NO_CENSUS_DATA,"No Census Data");
            }
            Comparator<CensusAnalyserBean> censusComparator = Comparator.comparing(census -> census.state);
            this.sort(censusComparator);
            String sortedStateCensusJson = new Gson().toJson(censusAnalyserBeanList);
            return sortedStateCensusJson;
    }

    private void sort(Comparator<CensusAnalyserBean> censusComparator){
        for(int i=0;i<censusAnalyserBeanList.size()-i;i++){
            for (int j=0;j<censusAnalyserBeanList.size()-i-1;j++){
                CensusAnalyserBean census1 = censusAnalyserBeanList.get(j);
                CensusAnalyserBean census2 = censusAnalyserBeanList.get(j+1);
                if(censusComparator.compare(census1,census2) > 0){
                    censusAnalyserBeanList.set(j, census2);
                    censusAnalyserBeanList.set(j+1, census1);
                }
            }
        }
    }
}
