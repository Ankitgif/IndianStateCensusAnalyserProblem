package com.csvbuilderhandler;



public class CSVBuilderException extends Exception {
    public enum exceptionType{
        CENSUS_FILE_PROBLEM,WRONG_DELIMETER_HEADER_IN_FILE,UNABLE_TO_PARSE
    };

    public exceptionType type;
    public CSVBuilderException(CSVBuilderException.exceptionType type, String message){
        super(message);
        this.type = type;
    }
}
