package com.delivr.data.model;

public class MyJobsModel {

    public MyJobsModel(){}

    private String str_jobwbno;
    private String str_jobtype;
    private String str_jobpickdate;
    private String str_jobpicktime;
    private String str_jobpickaddress;
    private String str_jobamount;
    private String str_jobdesc;
    private String str_jobcontact;
    private String str_contactperson;
    private String str_jobdeliverdate;
    private String str_jobdelivertime;
    private String str_jobdeliveraddress;

    public MyJobsModel(String str_jobwbno, String str_jobtype, String str_jobpickdate,
                       String str_jobpicktime, String str_jobpickaddress, String str_jobamount,
                       String str_jobdesc, String str_jobcontact, String str_contactperson,
                       String str_jobdeliverdate, String str_jobdelivertime, String str_jobdeliveraddress) {
        this.str_jobwbno = str_jobwbno;
        this.str_jobtype = str_jobtype;
        this.str_jobpickdate = str_jobpickdate;
        this.str_jobpicktime = str_jobpicktime;
        this.str_jobpickaddress = str_jobpickaddress;
        this.str_jobamount = str_jobamount;
        this.str_jobdesc = str_jobdesc;
        this.str_jobcontact = str_jobcontact;
        this.str_contactperson = str_contactperson;
        this.str_jobdeliverdate = str_jobdeliverdate;
        this.str_jobdelivertime = str_jobdelivertime;
        this.str_jobdeliveraddress = str_jobdeliveraddress;
    }

    public String getStr_jobcontact() {
        return str_jobcontact;
    }

    public void setStr_jobcontact(String str_jobcontact) {
        this.str_jobcontact = str_jobcontact;
    }

    public String getStr_contactperson() {
        return str_contactperson;
    }

    public void setStr_contactperson(String str_contactperson) {
        this.str_contactperson = str_contactperson;
    }

    public String getStr_jobwbno() {
        return str_jobwbno;
    }

    public void setStr_jobwbno(String str_jobwbno) {
        this.str_jobwbno = str_jobwbno;
    }

    public String getStr_jobtype() {
        return str_jobtype;
    }

    public void setStr_jobtype(String str_jobtype) {
        this.str_jobtype = str_jobtype;
    }

    public String getStr_jobpickdate() {
        return str_jobpickdate;
    }

    public void setStr_jobpickdate(String str_jobpickdate) {
        this.str_jobpickdate = str_jobpickdate;
    }

    public String getStr_jobpicktime() {
        return str_jobpicktime;
    }

    public void setStr_jobpicktime(String str_jobpicktime) {
        this.str_jobpicktime = str_jobpicktime;
    }

    public String getStr_jobpickaddress() {
        return str_jobpickaddress;
    }

    public void setStr_jobpickaddress(String str_jobpickaddress) {
        this.str_jobpickaddress = str_jobpickaddress;
    }

    public String getStr_jobamount() {
        return str_jobamount;
    }

    public void setStr_jobamount(String str_jobamount) {
        this.str_jobamount = str_jobamount;
    }

    public String getStr_jobdesc() {
        return str_jobdesc;
    }

    public void setStr_jobdesc(String str_jobdesc) {
        this.str_jobdesc = str_jobdesc;
    }

    public String getStr_jobdeliverdate() {
        return str_jobdeliverdate;
    }

    public void setStr_jobdeliverdate(String str_jobdeliverdate) {
        this.str_jobdeliverdate = str_jobdeliverdate;
    }

    public String getStr_jobdelivertime() {
        return str_jobdelivertime;
    }

    public void setStr_jobdelivertime(String str_jobdelivertime) {
        this.str_jobdelivertime = str_jobdelivertime;
    }

    public String getStr_jobdeliveraddress() {
        return str_jobdeliveraddress;
    }

    public void setStr_jobdeliveraddress(String str_jobdeliveraddress) {
        this.str_jobdeliveraddress = str_jobdeliveraddress;
    }





}
