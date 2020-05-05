package com.indiacensusanalyser;

import org.junit.Assert;
import org.junit.Test;

public class CensusAnalyserTest {
    public final String INDIA_CENSUS_CSV_FILE_PATH = "E:\\StateCensusData.csv";

    //UC1-->TC-1.1
    @Test
    public void givenIndianCensusCSVFileReturnCorrectRecord(){
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        int numOfRecord = 0;
        try {
            numOfRecord = censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29,numOfRecord);
        } catch (CensusAnalyserException exception) {
            exception.printStackTrace();
        }
    }
}
