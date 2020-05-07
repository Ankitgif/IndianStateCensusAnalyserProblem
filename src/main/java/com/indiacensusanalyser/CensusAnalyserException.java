package com.indiacensusanalyser;

import jdk.nashorn.internal.objects.NativeBoolean;

public class CensusAnalyserException extends Exception {
    private final exceptionType type;

    public enum exceptionType{
        CENSUS_FILE_PROBLEM,WRONG_DELIMETER_HEADER_IN_FILE,UNABLE_TO_PARSE;

    };

    public CensusAnalyserException(String message, String name){
        super(message);
        this.type = exceptionType.valueOf(name);
    }
    CensusAnalyserException(exceptionType type,String message){
        super(message);
        this.type = type;
    }
}
