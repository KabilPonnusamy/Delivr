package com.delivr.ui.interfaces;

import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public interface SHAInterface {

    public static String SHA1(String text) {
        byte[] sha1hash = new byte[40];
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(text.getBytes("iso-8859-1"), 0, text.length());
            sha1hash = md.digest();
        } catch (Exception e) {
        }
        return convertToHex(sha1hash);
    }

    public static String convertToHex(byte[] data) {
        StringBuilder sb = new StringBuilder(data.length * 2);

        java.util.Formatter fmt = new java.util.Formatter(sb);
        for (byte b : data) {
            fmt.format("%02x", b);
        }

        return sb.toString().toUpperCase();
    }

    public static String getDateFormatted(String mydate) throws ParseException {
        SimpleDateFormat spf=new SimpleDateFormat("MMM  dd yyyy  hh:mma");
        Date newDate=spf.parse(mydate);
        spf= new SimpleDateFormat("MMM dd yyyy");
        String newdate = spf.format(newDate);
        return newdate;
    }

    public static String getTimeFormatted(String mydate) throws ParseException {
        SimpleDateFormat spf=new SimpleDateFormat("MMM  dd yyyy  hh:mma");
        Date newDate=spf.parse(mydate);
        spf= new SimpleDateFormat("hh:mm a");
        String newdate = spf.format(newDate);
        return newdate;
    }

}
