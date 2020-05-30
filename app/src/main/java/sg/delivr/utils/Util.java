package sg.delivr.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.location.Location;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.SortedSet;
import java.util.TimeZone;
import java.util.TreeSet;

/**
 * Created by latticegulf on 9/21/16.
 */
public class Util {

    Activity activity;
    Dialog dialog;

    public static String screensize(Activity activity1) {
        Activity activity = activity1;

        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        double x = Math.pow(dm.widthPixels / dm.xdpi, 2);
        double y = Math.pow(dm.heightPixels / dm.ydpi, 2);
        double screenInches = Math.sqrt(x + y);
        int a = (int) Math.round(screenInches);
        String b = String.valueOf(a);
        Log.d("debug", "Screen inches : " + screenInches);


        return b;
    }

    public static View screen(Activity activity1, View view , int size) {
        Activity activity = activity1;

        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        double x = Math.pow(dm.widthPixels / dm.xdpi, 2);
        double y = Math.pow(dm.heightPixels / dm.ydpi, 2);
        double screenInches = Math.sqrt(x + y);
        int a = (int) Math.round(screenInches);
        String b = String.valueOf(a);
        Log.d("debug", "Screen inches : " + screenInches);

        return view;
    }

    @SuppressLint("MissingPermission")
    public static String deviceId(Activity activity) {
        TelephonyManager telephonyManager = (TelephonyManager)activity.getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.getDeviceId();

        String a = telephonyManager.getDeviceId();

        return a;
    }

    public static File getOutputMediaFile(){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM).getAbsolutePath(),"Camera");

        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                Log.d("CameraDemo", "failed to create directory");
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getAbsolutePath() + File.separator +
                "IMG_"+ timeStamp + ".jpg");
    }

    public static String getdate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String strDate = sdf.format(c.getTime());

        return strDate;
    }




    public static String getdate_Change() {
        //"1/12/2020 12:00:00 PM"
        Calendar c = Calendar.getInstance();
      //  SimpleDateFormat sdf = new SimpleDateFormat("yyyy/mm/dd HH:MM:SS");
        SimpleDateFormat sdf = new SimpleDateFormat("mm/dd/yyyy hh:mm:ss a");
        String strDate = sdf.format(c.getTime());

        return strDate;
    }



    public static String getdate_Formate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        String strDate = sdf.format(c.getTime());

        return strDate;
    }

    public static String getDateFormate(String s) {
        Calendar c = Calendar.getInstance();
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy");

        Date date = null;
        String strDate = null;

        try {
            date = inputFormat.parse(s);
            strDate = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }



        return strDate;
    }

    public static String getdateandtime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String strDate = sdf.format(c.getTime());

        return strDate;
    }

    public static String getCurrent_dateandtime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss a");
        String strDate = sdf.format(c.getTime());

        return strDate;
    }

    public static String getTime() {
        DateFormat df = DateFormat.getTimeInstance();
        df.setTimeZone(TimeZone.getTimeZone("UTC+04:00"));
//        df.switchTimezone(/* your desired timezone in string format */);
        String gmtTime = df.format(new Date());


        return gmtTime;
    }

    public static String getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm");
        String strDate = mdformat.format(calendar.getTime());

        return strDate;
    }

    public static boolean checktimings(String time, String currenttime) {

        String pattern = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        try {
            Date date1 = sdf.parse(time);
            Date date2 = sdf.parse(currenttime);

            return date1.before(date2);
        } catch (ParseException e){
            e.printStackTrace();
        }
        return false;
    }

    public static String getDateYYYY_MM_DD(Activity activity, String s1){
        String date = null;

     /*   SimpleDateFormat inputFormat=new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat outputFormat=new SimpleDateFormat("yyyy-MM-dd");*/
        DateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
        DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
        String inputDateStr = s1;
        Date date1 = null;
        String outputDateStr = null;

        try {
            date1 = inputFormat.parse(inputDateStr);
            outputDateStr = outputFormat.format(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        date = outputDateStr;

        return date;
    }

    public static String getDateDD_MMM_YYYY(Activity activity, String s1){
        String date = null;

        DateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
        DateFormat outputFormat = new SimpleDateFormat("dd-MMM-yyyy");
        String inputDateStr = s1;
        Date date1 = null;
        String outputDateStr = null;

        try {
            date1 = inputFormat.parse(inputDateStr);
            outputDateStr = outputFormat.format(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        date = outputDateStr;

        return date;
    }

    public static String getTimeUsingZone() {
        String s = null;

        try {
            String dtc = "2014-04-02T07:59:02.111Z";
            SimpleDateFormat readDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            readDate.setTimeZone(TimeZone.getTimeZone("GMT")); // missing line
            Date date = null;
            date = readDate.parse(dtc);
            SimpleDateFormat writeDate = new SimpleDateFormat("dd.MM.yyyy, HH.mm");
            writeDate.setTimeZone(TimeZone.getTimeZone("GMT+04:00"));
            s = writeDate.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  s;
    }

    public static String compressImage(Activity activity, String imageUri) {

        String filePath = getRealPathFromURI(activity,imageUri);
        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath,options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;
        float maxHeight = 816.0f;
        float maxWidth = 612.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

        options.inSampleSize = Util.calculateInSampleSize(options, actualWidth, actualHeight);
        options.inJustDecodeBounds = false;
        options.inDither = false;
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16*1024];

        try{
            bmp = BitmapFactory.decodeFile(filePath,options);
        }
        catch(OutOfMemoryError exception){
            exception.printStackTrace();

        }
        try{
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        }
        catch(OutOfMemoryError exception){
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float)options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth()/2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));


        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileOutputStream out = null;
        String filename = Util.getFilename();
        try {
            out = new FileOutputStream(filename);
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG,80, out);
            Util.deleteImageFromGaller(activity,imageUri);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return filename;

    }

    private static String getRealPathFromURI(Activity activity, String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = activity.getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;

        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }

    public static String getFilename() {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "Pictures/CameraDemo");
        if (!file.exists()) {
            file.mkdirs();
        }
        String uriSting = (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
        return uriSting;

    }

    public static void dialog(Activity activity , String s) {
        AlertDialog.Builder builder=new AlertDialog.Builder(activity);

        builder.setMessage(s);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();
    }

    public static void alertScreenChangeDialog(final Activity activity , String s, final Class aClass) {
        AlertDialog.Builder builder=new AlertDialog.Builder(activity);

        builder.setMessage(s);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                activity.startActivity(new Intent(activity,aClass));
            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();
    }

    public static String DateFormate(String fromDateFormat, String toDateFormat, String date){

        String result;

        DateFormat inputFormat = new SimpleDateFormat(fromDateFormat, Locale.US);
        DateFormat outputFormat = new SimpleDateFormat(toDateFormat, Locale.US);
        String inputDateStr = date;
        Date date1 = null;
        String outputDateStr = null;

        try {
            date1 = inputFormat.parse(inputDateStr);
            outputDateStr = outputFormat.format(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        result = outputDateStr;

        return result;
    }

    public static String TimeFormate(String fromDateFormat, String toDateFormat, String date){

        String result;

        DateFormat inputFormat = new SimpleDateFormat(fromDateFormat);
        DateFormat outputFormat = new SimpleDateFormat(toDateFormat);
        String inputDateStr = date;
        Date date1 = null;
        String outputDateStr = null;

        try {
            date1 = inputFormat.parse(inputDateStr);
            outputDateStr = outputFormat.format(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        result = outputDateStr;

        return result;
    }

    public static float convertion(Double lat, Double lng, Double currentLat, Double currentLon){

        Location locationA = new Location("point A");
        locationA.setLatitude(lat);
        locationA.setLongitude(lng);
        Location locationB = new Location("point B");
        locationB.setLatitude(currentLat);
        locationB.setLongitude(currentLon);
        float distanc = locationA.distanceTo(locationB)/1000 ;

        return distanc;

    }

    public static String specialCharecterRestriction(String s){

        String result = s;

        result = result.replaceAll("'","");
        result = result.replaceAll("^","");
        result = result.replaceAll("@","");
        result = result.replaceAll("~","");
        result = result.replaceAll("`","");
        result = result.replaceAll("|","");
        result = result.replaceAll("^","");
        result = result.replaceAll("003eu003cdiv style=\"font-size:0.9em\"u003","");


        return  result;
    }

    public static String getDirectionsUrl(LatLng origin, LatLng dest){

        // Origin of route
        String str_origin = "origin="+origin.latitude+","+origin.longitude;

        // Destination of route
        String str_dest = "destination="+dest.latitude+","+dest.longitude;


        // Sensor enabled
        String sensor = "sensor=false";

        String makani = "destination="+"1379071964";

        // Building the parameters to the web service
//        String parameters = str_origin+"&"+str_dest+"&"+sensor;
        String parameters = str_origin+"&"+makani+"&"+sensor;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;


        return url;
    }

    public static ArrayList<String> getFilePaths(Activity activity) {
        Uri u = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Images.ImageColumns.DATA};
        Cursor c = null;
        SortedSet<String> dirList = new TreeSet<String>();
        ArrayList<String> resultIAV = new ArrayList<String>();

        String[] directories = null;
        if (u != null)
        {
            c = activity.managedQuery(u, projection, null, null, null);
        }

        if ((c != null) && (c.moveToFirst()))
        {
            do
            {
                String tempDir = c.getString(0);
                tempDir = tempDir.substring(0, tempDir.lastIndexOf("/"));
                try{
                    dirList.add(tempDir);
                }
                catch(Exception e)
                {

                }
            }
            while (c.moveToNext());
            directories = new String[dirList.size()];
            dirList.toArray(directories);

        }

        for(int i=0;i<dirList.size();i++)
        {
            File imageDir = new File(directories[i]);
            File[] imageList = imageDir.listFiles();
            if(imageList == null)
                continue;
            for (File imagePath : imageList) {
                try {

                    if(imagePath.isDirectory())
                    {
                        imageList = imagePath.listFiles();

                    }
                    if ( imagePath.getName().contains(".jpg")|| imagePath.getName().contains(".JPG")
                            || imagePath.getName().contains(".jpeg")|| imagePath.getName().contains(".JPEG")
                            || imagePath.getName().contains(".png") || imagePath.getName().contains(".PNG")
                            || imagePath.getName().contains(".gif") || imagePath.getName().contains(".GIF")
                            || imagePath.getName().contains(".bmp") || imagePath.getName().contains(".BMP")
                            )
                    {



                        String path= imagePath.getAbsolutePath();
                        resultIAV.add(path);

                    }
                }
                //  }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return resultIAV;


    }

    public static void deleteImageFromGaller(Activity activity, String path) {
        File target = new File(path);
        if (target.exists() && target.isFile() && target.canWrite()) {
            try {
                target.getCanonicalFile().delete();
                activity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(path))));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void deleteLastCapturedImage(Activity activity) {
        String[] projection = {
                MediaStore.Images.ImageColumns.SIZE,
                MediaStore.Images.ImageColumns.DISPLAY_NAME,
                MediaStore.Images.ImageColumns.DATA,
                BaseColumns._ID
        };

        Cursor c = null;
        Uri u = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        try {
            if (u != null) {
                c = activity.managedQuery(u, projection, null, null, null);
            }
            if ((c != null) && (c.moveToLast())) {

                ContentResolver cr = activity.getContentResolver();
                int i = cr.delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, BaseColumns._ID + "=" + c.getString(c.getColumnIndex(BaseColumns._ID)), null);

                Log.v("vvvvvv", "Number of column deleted : " + i);

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
//        finally {
//            if (c != null) {
//                c.close();
//            }
//        }

    }

    public static void netWorkIssueDialg(Context activity){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setMessage("Network Issue");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    /*public static void NoRecord_Dialg(Context activity){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setMessage(activity.getResources().getString(R.string.norecords_found));
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }*/

    public static boolean isEmailValid(String email) {
        boolean b = false;
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        try {
            b = email.matches(emailPattern);
        }
        catch (Exception e) {

        }
        return b;
    }

    public static void showAlert(Context context, String message){
        try
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT);
            builder.setMessage(message);
            builder.setPositiveButton("OK", null);


            AlertDialog dialog = builder.show();
            TextView messageText = dialog.findViewById(android.R.id.message);
            messageText.setGravity(Gravity.CENTER);
            dialog.show();
        }
        catch(Exception e)
        {

        }
    }

    public static String getdateandtime2() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh-mm-ss-SSS");
        String strDate = sdf.format(c.getTime());

        return strDate;
    }




}
