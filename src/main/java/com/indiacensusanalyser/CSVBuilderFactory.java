package com.indiacensusanalyser;

public class CSVBuilderFactory {
    public static ICSVBuilder createCSVBuilder(){
        return new OpenCSVBuilder();
    }
}
