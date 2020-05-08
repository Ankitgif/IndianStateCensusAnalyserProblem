package com.csvbuilderhandler;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class OpenCSVBuilder<E> implements ICSVBuilder {

    @Override
    public Iterator getCSVFileIterator(Reader reader, Class csvClass) throws CSVBuilderException {
        return this.getCSVBean(reader, csvClass).iterator();
    }

    @Override
    public List getCSVFileList(Reader reader, Class csvClass) throws CSVBuilderException {
        return this.getCSVBean(reader, csvClass).parse();

    }

//    @Override
//    public Map getCSVFileMap(Reader reader, Class csvClass) throws CSVBuilderException {
//        return (Map) this.getCSVBean(reader, csvClass).parse();
//    }

    private CsvToBean<E> getCSVBean(Reader reader, Class csvClass) throws CSVBuilderException {
        try{
            CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<E>(reader);
            csvToBeanBuilder.withType(csvClass);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            return csvToBeanBuilder.build();
        } catch (IllegalStateException exception){
            throw new CSVBuilderException(CSVBuilderException.exceptionType.UNABLE_TO_PARSE,"Unable To Parse");
        }
    }
}
