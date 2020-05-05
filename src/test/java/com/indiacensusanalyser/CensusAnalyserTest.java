package com.indiacensusanalyser;

import org.junit.Assert;
import org.junit.Test;

public class CensusAnalyserTest {
    public final String INDIA_CENSUS_CSV_FILE_PATH = "E:\\StateCensusData.csv";
    public final String WRONG_CSV_FILE_PATH = "E:\\StateCensusDa.csv";
    public final String WRONG_CSV_FILE_TYPE_PATH = "E:\\StateCensusData.txt";
    public final String WRONG_CSV_FILE_DELIMETER_PATH = "C:\\Users\\ANKIT\\Desktop\\IndianStateCensusAnalyser\\StateCensusData.csv";
    public final String STATE_CSV_FILE_PATH = "E:\\StateCode.csv";
    public final String WRONG_STATE_CSV_FILE_PATH = "E:\\StateCo.csv";
    public final String WRONG_STATE_CSV_FILE_TYPE_PATH = "E:\\StateCode.txt";
    public final String WRONG_STATE_CSV_FILE_DELIMETER_PATH = "C:\\Users\\ANKIT\\Desktop\\IndianStateCensusAnalyser\\StateCode.csv";

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
    //UC-1-->TC-1.3
    @Test
    public void givenIndianCensusData_WithWrongType_ShouldThrow_CustomException(){
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        try {
            censusAnalyser.loadCensusData(WRONG_CSV_FILE_TYPE_PATH);
        } catch (CensusAnalyserException exception) {
            Assert.assertEquals("File Not Found",exception.getMessage());
        }
    }
    //UC-1-->TC-1.4
    @Test
    public void givenIndianCensusData_Proper_WithImproperDelimeter_ShouldThrow_CustomException(){
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        try {
            censusAnalyser.loadCensusData(WRONG_CSV_FILE_DELIMETER_PATH);
        } catch (CensusAnalyserException exception) {
            Assert.assertEquals("Wrong Delimeter Or Header In File",exception.getMessage());
        }
    }
    //UC-1-->TC-1.5
    @Test
    public void givenIndianCensusData_Proper_WithImproperHeader_ShouldThrow_CustomException(){
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        try {
            censusAnalyser.loadCensusData(WRONG_CSV_FILE_DELIMETER_PATH);
        } catch (CensusAnalyserException exception) {
            Assert.assertEquals("Wrong Delimeter Or Header In File",exception.getMessage());
        }
    }
    //UC2-->TC-2.1
    @Test
    public void givenStateCSVData_WhenProper_ShouldReturnCorrectRecord(){
        StateCodeAnalyser stateCodeAnalyser = new StateCodeAnalyser();
        try {
            int numOfRecord = stateCodeAnalyser.loadStateCodeData(STATE_CSV_FILE_PATH);
            Assert.assertEquals(37,numOfRecord);
        } catch (CensusAnalyserException exception) {
            exception.printStackTrace();
        }
    }
    //UC@-->TC-2.2
    @Test
    public void givenStateCSVData_WithWrongFile_ShouldThrow_CustomException(){
        StateCodeAnalyser stateCodeAnalyser = new StateCodeAnalyser();
        try {
            stateCodeAnalyser.loadStateCodeData(WRONG_STATE_CSV_FILE_PATH);
        } catch (CensusAnalyserException exception) {
            Assert.assertEquals("File Not Found",exception.getMessage());
        }
    }
    //UC2->TC-2.3
    @Test
    public void givenStateCSVData_WithWrongType_ShouldThrowCustomException(){
        StateCodeAnalyser stateCodeAnalyser = new StateCodeAnalyser();
        try {
            stateCodeAnalyser.loadStateCodeData(WRONG_STATE_CSV_FILE_TYPE_PATH);
        } catch (CensusAnalyserException exception) {
            Assert.assertEquals("File Not Found",exception.getMessage());
        }
    }
    //UC2->TC-2.4
    @Test
    public void givenStateCSVData_Proper_WithImproperDelimeter_ShouldThrow_CustomException(){
        StateCodeAnalyser stateCodeAnalyser = new StateCodeAnalyser();
        try {
            stateCodeAnalyser.loadStateCodeData(WRONG_STATE_CSV_FILE_DELIMETER_PATH);
        } catch (CensusAnalyserException exception) {
            Assert.assertEquals("Wrong Delimeter Or Header In File",exception.getMessage());
        }
    }
    //UC2->TC-2.5
    @Test
    public void givenStateCSVData_Proper_WithImproperHeader_ShouldThrow_CustomException(){
        StateCodeAnalyser stateCodeAnalyser = new StateCodeAnalyser();
        try {
            stateCodeAnalyser.loadStateCodeData(WRONG_STATE_CSV_FILE_DELIMETER_PATH);
        } catch (CensusAnalyserException exception) {
            Assert.assertEquals("Wrong Delimeter Or Header In File",exception.getMessage());
        }
    }


}
