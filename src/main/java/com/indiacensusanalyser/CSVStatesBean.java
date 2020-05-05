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

    public int getSrno() {
        return srno;
    }

    public void setSrno(int srno) {
        this.srno = srno;
    }

    public String getStatename() {
        return statename;
    }

    public void setStatename(String statename) {
        this.statename = statename;
    }

    public int getTin() {
        return tin;
    }

    public void setTin(int tin) {
        this.tin = tin;
    }

    public String getStatecode() {
        return statecode;
    }

    public void setStatecode(String statecode) {
        this.statecode = statecode;
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
