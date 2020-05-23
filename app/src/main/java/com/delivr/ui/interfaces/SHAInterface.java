package com.delivr.ui.interfaces;

import android.util.Log;

import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
        final Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 1);

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        String tdyDate =  day + "-" +  month + "-" +  year;

        c.add(Calendar.DAY_OF_MONTH, 1);
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        String tommDate =  day + "-" +  month + "-" +  year;

        mydate = mydate.replaceAll("\\s+"," ");

        SimpleDateFormat tdyspf = new SimpleDateFormat("dd-MM-yyyy");
        Date chktdyDate = tdyspf.parse(tdyDate);
        tdyspf= new SimpleDateFormat("MMM dd yyyy");
        String tdynewdate = tdyspf.format(chktdyDate);

        SimpleDateFormat tommspf = new SimpleDateFormat("dd-MM-yyyy");
        Date chktommDate = tommspf.parse(tommDate);
        tommspf= new SimpleDateFormat("MMM dd yyyy");
        String tommnewdate = tommspf.format(chktommDate);

        SimpleDateFormat spf=new SimpleDateFormat("MMM dd yyyy hh:mma");
        Date newDate=spf.parse(mydate);
        spf= new SimpleDateFormat("MMM dd yyyy");
        String newdate = spf.format(newDate);

        if(tdynewdate.equalsIgnoreCase(newdate)) {
            return "Today";
        } else if(tommnewdate.equalsIgnoreCase(newdate)) {
            return "Tomorrow";
        }

        return newdate;
    }

    public static String getTimeFormatted(String mydate) throws ParseException {
        mydate = mydate.replaceAll("\\s+"," ");

        SimpleDateFormat spf=new SimpleDateFormat("MMM dd yyyy hh:mma");
        Date newDate=spf.parse(mydate);
        spf= new SimpleDateFormat("hh:mm a");
        String newdate = spf.format(newDate);
        return newdate;
    }

    public static String getDateFormattedTwo(String mydate) throws ParseException {
        SimpleDateFormat spf=new SimpleDateFormat("MMM dd yyyy  hh:mma");
        Date newDate=spf.parse(mydate);
        spf= new SimpleDateFormat("MMM dd yyyy");
        String newdate = spf.format(newDate);
        return newdate;
    }

    public static String getTimeFormattedTwo(String mydate) throws ParseException {
        SimpleDateFormat spf=new SimpleDateFormat("MMM dd yyyy  hh:mma");
        Date newDate=spf.parse(mydate);
        spf= new SimpleDateFormat("hh:mm a");
        String newdate = spf.format(newDate);
        return newdate;
    }

}
