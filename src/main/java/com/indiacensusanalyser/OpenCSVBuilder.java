package com.indiacensusanalyser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.util.Iterator;

public class OpenCSVBuilder {
    public <E> Iterator<E> getCSVFileIterator(Reader reader,
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
