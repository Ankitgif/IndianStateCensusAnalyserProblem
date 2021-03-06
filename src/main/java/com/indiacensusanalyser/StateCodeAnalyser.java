package com.indiacensusanalyser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

public class StateCodeAnalyser {
    private int noOfEntries;

    public int loadStateCodeData(String path) throws CensusAnalyserException{
        try{
            Reader reader = Files.newBufferedReader(Paths.get(path));
            CsvToBean<CSVStatesBean> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(CSVStatesBean.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            Iterator<CSVStatesBean> csvUserIterator = csvToBean.iterator();
            while(csvUserIterator.hasNext()){
                noOfEntries++;
                CSVStatesBean csvUser = csvUserIterator.next();
            }
        } catch (IOException exception) {
            throw new CensusAnalyserException(CensusAnalyserException.exceptionType.CENSUS_FILE_PROBLEM,"File Not Found");
        } catch (RuntimeException exception){
            throw new CensusAnalyserException(CensusAnalyserException.exceptionType.WRONG_DELIMETER_HEADER_IN_FILE,"Wrong Delimeter Or Header In File");
        }
        return noOfEntries;
    }
}
