package com.indiacensusanalyser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

public class CensusAnalyser {

    private int noOfEntries;

    public int loadCensusData(String path) throws CensusAnalyserException {

        try {
            Reader reader = Files.newBufferedReader(Paths.get(path));
            CsvToBean<CensusAnalyserBean> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(CensusAnalyserBean.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            Iterator<CensusAnalyserBean> csvUserIterator = csvToBean.iterator();
            while (csvUserIterator.hasNext()) {
                noOfEntries++;
                CensusAnalyserBean csvUser = csvUserIterator.next();
            }
        } catch (IOException exception) {
            throw new CensusAnalyserException(CensusAnalyserException.exceptionType.CENSUS_FILE_PROBLEM,"File Not Found");
        }  catch (RuntimeException exception){
            throw new CensusAnalyserException(CensusAnalyserException.exceptionType.WRONG_DELIMETER_HEADER_IN_FILE,"Wrong Delimeter Or Header In File");
        }
        return noOfEntries;
    }
}
