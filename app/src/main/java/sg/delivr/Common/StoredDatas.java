package sg.delivr.Common;

import sg.delivr.backend.responsemodels.ResponseAssignedQueue;
import sg.delivr.backend.responsemodels.ResponseCompletedJobs;
import sg.delivr.backend.responsemodels.ResponseOrderHistory;
import sg.delivr.backend.responsemodels.ResponseRiderQueue;

import java.util.ArrayList;

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

    ArrayList<ResponseRiderQueue> riderQueues;
    int rQuePos;
    String screenValidation;
    String CheckOutAmount;
    ArrayList<ResponseCompletedJobs> completedJobs;
    ArrayList<ResponseOrderHistory> orderHistoryList;

    public ArrayList<ResponseOrderHistory> getOrderHistoryList() {
        return orderHistoryList;
    }

    public void setOrderHistoryList(ArrayList<ResponseOrderHistory> orderHistoryList) {
        this.orderHistoryList = orderHistoryList;
    }

    public ResponseAssignedQueue getAssignedjobQueues() {
        return assignedjobQueues;
    }

    public void setAssignedjobQueues(ResponseAssignedQueue assignedjobQueues) {
        this.assignedjobQueues = assignedjobQueues;
    }

    ResponseAssignedQueue assignedjobQueues;

    public ArrayList<ResponseCompletedJobs> getCompletedJobs() {
        return completedJobs;
    }

    public void setCompletedJobs(ArrayList<ResponseCompletedJobs> completedJobs) {
        this.completedJobs = completedJobs;
    }

    public String getScreenValidation() {
        return screenValidation;
    }

    public void setScreenValidation(String screenValidation) {
        this.screenValidation = screenValidation;
    }

    public String getCheckOutAmount() {
        return CheckOutAmount;
    }

    public void setCheckOutAmount(String checkOutAmount) {
        this.CheckOutAmount = checkOutAmount;
    }

    public ArrayList<ResponseRiderQueue> getRiderQueues() {
        return riderQueues;
    }

    public void setRiderQueues(ArrayList<ResponseRiderQueue> riderQueues) {
        this.riderQueues = riderQueues;
    }


    public int getrQuePos() {
        return rQuePos;
    }

    public void setrQuePos(int rQuePos) {
        this.rQuePos = rQuePos;
    }
}