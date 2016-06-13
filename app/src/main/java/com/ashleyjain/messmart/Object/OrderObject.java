package com.ashleyjain.messmart.Object;

/**
 * Created by ashleyjain on 24/05/16.
 */
public class OrderObject {

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    private String datetime;
    private String date;
    private int lOIDid,dOIDid;
    private String lDish,dDish;
    private String lMess,dMess;

    public String getLlord() {
        return llord;
    }

    public void setLlord(String llord) {
        this.llord = llord;
    }

    public String getDlord() {
        return dlord;
    }

    public void setDlord(String dlord) {
        this.dlord = dlord;
    }

    private String llord,dlord;

    public int getlDishid() {
        return lDishid;
    }

    public void setlDishid(int lDishid) {
        this.lDishid = lDishid;
    }

    public int getdDishid() {
        return dDishid;
    }

    public void setdDishid(int dDishid) {
        this.dDishid = dDishid;
    }

    private int lDishid,dDishid,lPrice,dPrice;
    private String lStatus,dStatus;

    public int getlMid() {
        return lMid;
    }

    public void setlMid(int lMid) {
        this.lMid = lMid;
    }

    public int getdMid() {
        return dMid;
    }

    public void setdMid(int dMid) {
        this.dMid = dMid;
    }

    private int lMid,dMid;

    public String getlBooktype() {
        return lBooktype;
    }

    public void setlBooktype(String lBooktype) {
        this.lBooktype = lBooktype;
    }

    private String lBooktype;

    public String getdBooktype() {
        return dBooktype;
    }

    public void setdBooktype(String dBooktype) {
        this.dBooktype = dBooktype;
    }

    private String dBooktype;

    public OrderObject(String datetime,String date,int lOIDid, int dOIDid, String lDish, String dDish, String lMess, String dMess, int lPrice, int dPrice, String lStatus, String dStatus,String lBooktype,String dBooktype,int lMid,int dMid,int lDishid,int dDishid,String llord,String dlord) {
        this.datetime = datetime;
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
        this.lBooktype = lBooktype;
        this.dBooktype = dBooktype;
        this.lMid = lMid;
        this.dMid = dMid;
        this.lDishid = lDishid;
        this.dDishid = dDishid;
        this.llord = llord;
        this.dlord = dlord;
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
