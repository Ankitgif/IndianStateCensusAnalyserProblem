package com.csvbuilderhandler;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;

public interface ICSVBuilder<E> {

    public List<E> getCSVFileList(Reader reader, Class csvClass) throws CSVBuilderException;
}
