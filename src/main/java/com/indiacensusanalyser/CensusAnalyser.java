package com.indiacensusanalyser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;

public class CensusAnalyser {

    public int loadCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            Iterator<CensusAnalyserBean> censusAnalyserBeanIterator = this.getCSVFileIterator(reader, CensusAnalyserBean.class);
            Iterable<CensusAnalyserBean> censusAnalyserBeanIterable = () -> censusAnalyserBeanIterator;
            int numOfEnteries = (int) StreamSupport.stream(censusAnalyserBeanIterable.spliterator(), false).count();
            return numOfEnteries;
        } catch (IOException exception) {
            throw new CensusAnalyserException(CensusAnalyserException.exceptionType.CENSUS_FILE_PROBLEM, "File Not Found");
        } catch (RuntimeException exception) {
            throw new CensusAnalyserException(CensusAnalyserException.exceptionType.WRONG_DELIMETER_HEADER_IN_FILE, "Wrong Delimeter Or Header In File");
        }
    }

    public int loadStateCodeData(String csvFilePath) throws CensusAnalyserException{
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));){
            Iterator<CSVStatesBean> csvStatesBeanIterator = this.getCSVFileIterator(reader,CSVStatesBean.class);
            Iterable<CSVStatesBean> csvStatesBeanIterable = () -> csvStatesBeanIterator;
            int numOfEnteries = (int) StreamSupport.stream(csvStatesBeanIterable.spliterator(),false).count();
            return numOfEnteries;
        }catch (IOException exception){
            throw new CensusAnalyserException(CensusAnalyserException.exceptionType.CENSUS_FILE_PROBLEM,"File Not Found");
        } catch (RuntimeException exception){
            throw new CensusAnalyserException(CensusAnalyserException.exceptionType.WRONG_DELIMETER_HEADER_IN_FILE,"Wrong Delimeter Or Header In File");
        }
    }

    private <E> Iterator<E> getCSVFileIterator(Reader reader,
                                           Class<E> csvClass) throws CensusAnalyserException{
        try{
            CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<E>(reader);
            csvToBeanBuilder.withType(csvClass);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<E> csvToBean = csvToBeanBuilder.build();
            return csvToBean.iterator();
        } catch (IllegalStateException exception){
            throw new CensusAnalyserException(CensusAnalyserException.exceptionType.UNABLE_TO_PARSE,"Unable To Parse");
        }
    }
}
