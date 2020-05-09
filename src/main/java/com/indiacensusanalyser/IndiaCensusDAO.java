package com.indiacensusanalyser;

public class IndiaCensusDAO  {
    public String state;
    private int population;
    private int areaInSqKm;
    private int densityPerSqKm;
    public String stateCode;

    public IndiaCensusDAO(CensusAnalyserBean censusAnalyserBean){
        state = censusAnalyserBean.state;
        population = censusAnalyserBean.population;
        areaInSqKm = censusAnalyserBean.areaInSqKm;
        densityPerSqKm = censusAnalyserBean.densityPerSqKm;


    }
}
