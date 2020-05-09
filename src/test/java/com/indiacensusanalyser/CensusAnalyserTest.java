package com.indiacensusanalyser;

import com.google.gson.Gson;
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
            numOfRecord = censusAnalyser.loadStateCensusData(INDIA_CENSUS_CSV_FILE_PATH);
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
            censusAnalyser.loadStateCensusData(WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException exception) {
            Assert.assertEquals("File Not Found",exception.getMessage());
        }
    }
    //UC-1-->TC-1.3
    @Test
    public void givenIndianCensusData_WithWrongType_ShouldThrow_CustomException(){
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        try {
            censusAnalyser.loadStateCensusData(WRONG_CSV_FILE_TYPE_PATH);
        } catch (CensusAnalyserException exception) {
            Assert.assertEquals("File Not Found",exception.getMessage());
        }
    }
    //UC-1-->TC-1.4
    @Test
    public void givenIndianCensusData_Proper_WithImproperDelimeter_ShouldThrow_CustomException(){
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        try {
            censusAnalyser.loadStateCensusData(WRONG_CSV_FILE_DELIMETER_PATH);
        } catch (CensusAnalyserException exception) {
            Assert.assertEquals("Wrong Delimeter Or Header In File",exception.getMessage());
        }
    }
    //UC-1-->TC-1.5
    @Test
    public void givenIndianCensusData_Proper_WithImproperHeader_ShouldThrow_CustomException(){
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        try {
            censusAnalyser.loadStateCensusData(WRONG_CSV_FILE_DELIMETER_PATH);
        } catch (CensusAnalyserException exception) {
            Assert.assertEquals("Wrong Delimeter Or Header In File",exception.getMessage());
        }
    }
    //UC2-->TC-2.1
    @Test
    public void givenStateCSVData_WhenProper_ShouldReturnCorrectRecord(){
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadStateCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            int numOfRecord = censusAnalyser.loadStateCodeData(STATE_CSV_FILE_PATH);
            Assert.assertEquals(29,numOfRecord);
        } catch (CensusAnalyserException exception) {
            exception.printStackTrace();
        }
    }
    //UC@-->TC-2.2
    @Test
    public void givenStateCSVData_WithWrongFile_ShouldThrow_CustomException(){
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        try {
            censusAnalyser.loadStateCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            censusAnalyser.loadStateCodeData(WRONG_STATE_CSV_FILE_PATH);
        } catch (CensusAnalyserException exception) {
            Assert.assertEquals("File Not Found",exception.getMessage());
        }
    }
    //UC2->TC-2.3
    @Test
    public void givenStateCSVData_WithWrongType_ShouldThrowCustomException(){
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        try {
            censusAnalyser.loadStateCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            censusAnalyser.loadStateCodeData(WRONG_STATE_CSV_FILE_TYPE_PATH);
        } catch (CensusAnalyserException exception) {
            Assert.assertEquals("File Not Found",exception.getMessage());
        }
    }
    //UC2->TC-2.4
    @Test
    public void givenStateCSVData_Proper_WithImproperDelimeter_ShouldThrow_CustomException(){
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        try {
            censusAnalyser.loadStateCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            censusAnalyser.loadStateCodeData(WRONG_STATE_CSV_FILE_DELIMETER_PATH);
        } catch (CensusAnalyserException exception) {
            Assert.assertEquals("Wrong Delimeter Or Header In File",exception.getMessage());
        }
    }
    //UC2->TC-2.5
    @Test
    public void givenStateCSVData_Proper_WithImproperHeader_ShouldThrow_CustomException(){
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        try {
            censusAnalyser.loadStateCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            censusAnalyser.loadStateCodeData(WRONG_STATE_CSV_FILE_DELIMETER_PATH);
        } catch (CensusAnalyserException exception) {
            Assert.assertEquals("Wrong Delimeter Or Header In File",exception.getMessage());
        }
    }
    @Test
    public void givenIndianCensusData_WhenSortedOnState_ShouldReturnSortedResult(){
        try{
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadStateCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData();
            IndiaCensusDAO[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusDAO[].class);
            Assert.assertEquals("Andhra Pradesh",censusCSV[0].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void givenIndianCensusData_WhenSortedOnStateCode_ShouldReturnSortedResult(){
        try{
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadStateCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            censusAnalyser.loadStateCodeData(STATE_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCodeData();
            IndiaCensusDAO[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusDAO[].class);
            Assert.assertEquals("AP",censusCSV[0].stateCode);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
   }
   @Test
    public void givenIndianCensusData_WhenSortedOnStatePopulation_ShouldReturnMostPopulous(){
       try {
           CensusAnalyser censusAnalyser = new CensusAnalyser();
           censusAnalyser.loadStateCensusData(INDIA_CENSUS_CSV_FILE_PATH);
           String mostPopulous = censusAnalyser.getStateWiseMostPopulousState();
           IndiaCensusDAO censusCSV[] = new Gson().fromJson(String.valueOf(mostPopulous), IndiaCensusDAO[].class);
           Assert.assertEquals(199812341,censusCSV[censusCSV.length-1].population);
       } catch (CensusAnalyserException e) {
           e.printStackTrace();
       }
   }
}
