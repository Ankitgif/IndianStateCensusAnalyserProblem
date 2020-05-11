package com.indiacensusanalyser;

import com.opencsv.bean.CsvBindByName;

public class CSVStatesBean {
    @CsvBindByName(column = "SrNo", required = true)
    public int srno;

    @CsvBindByName(column = "StateName", required = true)
    public String statename;

    @CsvBindByName(column = "TIN", required = true)
    public int tin;

    @CsvBindByName(column = "StateCode", required = true)
    public String statecode;

    public CSVStatesBean() {

    }

    @Override
    public String toString() {
        return "CSVStatesBean{" +
                "srno=" + srno +
                ", statename='" + statename + '\'' +
                ", tin=" + tin +
                ", statecode='" + statecode + '\'' +
                '}';
    }
}
