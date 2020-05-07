package com.csvbuilderhandler;

import com.csvbuilderhandler.CSVBuilderException;
import com.csvbuilderhandler.ICSVBuilder;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.util.Iterator;

public class OpenCSVBuilder<E> implements ICSVBuilder {
    public Iterator<E> getCSVFileIterator(Reader reader, Class csvClass) throws CSVBuilderException {
        try{
            CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<E>(reader);
            csvToBeanBuilder.withType(csvClass);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<E> csvToBean = csvToBeanBuilder.build();
            return csvToBean.iterator();
        } catch (IllegalStateException exception){
            throw new CSVBuilderException(CSVBuilderException.exceptionType.UNABLE_TO_PARSE,"Unable To Parse");
        }
    }
}
