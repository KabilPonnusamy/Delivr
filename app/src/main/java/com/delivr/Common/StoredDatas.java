package com.delivr.Common;

public class StoredDatas {

    public static StoredDatas myObj;
    public static StoredDatas getMyObj() {
        return myObj;
    }
    public static void setMyObj(StoredDatas myObj) {
        StoredDatas.myObj = myObj;
    }
    public static StoredDatas getInstance() {
        if (myObj == null) {
            myObj = new StoredDatas();
        }
        return myObj;
    }

    String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }



}