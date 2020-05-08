package com.csvbuilderhandler;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public interface ICSVBuilder<E> {

    public Iterator<E> getCSVFileIterator(Reader reader, Class csvClass) throws CSVBuilderException;
    public List<E> getCSVFileList(Reader reader, Class csvClass) throws CSVBuilderException;

   // public Map<String , E> getCSVFileMap(Reader reader, Class csvClass) throws CSVBuilderException;

}
