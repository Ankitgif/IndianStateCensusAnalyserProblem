package com.indiacensusanalyser;

public class CensusAnalyserException extends Exception {
    private final exceptionType type;

    public enum exceptionType{
        CENSUS_FILE_PROBLEM
    };
    CensusAnalyserException(exceptionType type,String message){
        super(message);
        this.type = type;
    }
}
