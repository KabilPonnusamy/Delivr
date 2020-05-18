package com.delivr.backend.models;

public class CAdddress {

    private int PostalCode;
    private String Address;
    private double Lat;
    private double Long;

    public CAdddress() {
        super();
    }

    public CAdddress(int PostalCode, String Address, double Lat, double Long) {
        super();
        this.PostalCode = PostalCode;
        this.Address = Address;
        this.Lat = Lat;
        this.Long = Long;
    }

    @Override
    public String toString() {
        return this.Address;
    }

    public double Lat() {
        return this.Lat;
    }

    public double Long() {
        return this.Long;
    }

    public int PostalCode() {
        return this.PostalCode;
    }
}



