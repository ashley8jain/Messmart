package com.ashleyjain.messmart.Object;

/**
 * Created by ashleyjain on 24/05/16.
 */
public class OrderObject {

    private String date;
    private int lOIDid,dOIDid;
    private String lDish,dDish;
    private String lMess,dMess;
    private int lPrice,dPrice;
    private String lStatus,dStatus;

    public OrderObject(String date,int lOIDid, int dOIDid, String lDish, String dDish, String lMess, String dMess, int lPrice, int dPrice, String lStatus, String dStatus) {
        this.date = date;
        this.lOIDid = lOIDid;
        this.dOIDid = dOIDid;
        this.lDish = lDish;
        this.dDish = dDish;
        this.lMess = lMess;
        this.dMess = dMess;
        this.lPrice = lPrice;
        this.dPrice = dPrice;
        this.lStatus = lStatus;
        this.dStatus = dStatus;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getlOIDid() {
        return lOIDid;
    }

    public void setlOIDid(int lOIDid) {
        this.lOIDid = lOIDid;
    }

    public int getdOIDid() {
        return dOIDid;
    }

    public void setdOIDid(int dOIDid) {
        this.dOIDid = dOIDid;
    }

    public String getlDish() {
        return lDish;
    }

    public void setlDish(String lDish) {
        this.lDish = lDish;
    }

    public String getdDish() {
        return dDish;
    }

    public void setdDish(String dDish) {
        this.dDish = dDish;
    }

    public String getlMess() {
        return lMess;
    }

    public void setlMess(String lMess) {
        this.lMess = lMess;
    }

    public String getdMess() {
        return dMess;
    }

    public void setdMess(String dMess) {
        this.dMess = dMess;
    }

    public int getlPrice() {
        return lPrice;
    }

    public void setlPrice(int lPrice) {
        this.lPrice = lPrice;
    }

    public int getdPrice() {
        return dPrice;
    }

    public void setdPrice(int dPrice) {
        this.dPrice = dPrice;
    }

    public String getlStatus() {
        return lStatus;
    }

    public void setlStatus(String lStatus) {
        this.lStatus = lStatus;
    }

    public String getdStatus() {
        return dStatus;
    }

    public void setdStatus(String dStatus) {
        this.dStatus = dStatus;
    }
    
}
