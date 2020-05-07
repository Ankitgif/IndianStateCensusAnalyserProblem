package com.indiacensusanalyser;

import com.csvbuilderhandler.CSVBuilderException;
import com.csvbuilderhandler.CSVBuilderFactory;
import com.csvbuilderhandler.ICSVBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
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
}
