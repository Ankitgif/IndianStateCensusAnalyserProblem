package com.indiacensusanalyser;

public class IndiaCensusDAO  {
    public String state;
    public int population;
    public int areaInSqKm;
    public int densityPerSqKm;
    public String stateCode;
    public IndiaCensusDAO(CensusAnalyserBean censusAnalyserBean){
        state = censusAnalyserBean.state;
        population = censusAnalyserBean.population;
        areaInSqKm = censusAnalyserBean.areaInSqKm;
        densityPerSqKm = censusAnalyserBean.densityPerSqKm;
    }
}
