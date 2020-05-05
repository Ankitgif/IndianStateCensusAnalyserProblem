package com.indiacensusanalyser;

import org.junit.Assert;
import org.junit.Test;

public class CensusAnalyserTest {
    public final String INDIA_CENSUS_CSV_FILE_PATH = "E:\\StateCensusData.csv";
    public final String WRONG_CSV_FILE_PATH = "E:\\StateCensusDa.csv";

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
    //UC-1-->TC-1.2
    @Test
    public void givenIndianCensusData_WithWrongFile_ShouldThrow_CustomException(){
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        try {
            censusAnalyser.loadCensusData(WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException exception) {
            Assert.assertEquals("File Not Found",exception.getMessage());
        }
    }
}
