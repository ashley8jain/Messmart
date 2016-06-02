package com.ashleyjain.messmart.Object;

/**
 * Created by ashleyjain on 02/06/16.
 */
public class PastOrderObject {

    private String datetime;
    private String date;
    private String lord;
    private int OIDid;
    private String Dish;
    private String Mess;

    public PastOrderObject(String datetime, String date, String lord, int OIDid, String dish, String mess, int dishid, int price, String status, int mid, String booktype) {
        this.datetime = datetime;
        this.date = date;
        this.lord = lord;
        this.OIDid = OIDid;
        Dish = dish;
        Mess = mess;
        Dishid = dishid;
        Price = price;
        Status = status;
        Mid = mid;
        Booktype = booktype;
    }

    private int Dishid,Price;
    private String Status;
    private int Mid;
    private String Booktype;

    public String getLord() {
        return lord;
    }

    public void setLord(String lord) {
        this.lord = lord;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getOIDid() {
        return OIDid;
    }

    public void setOIDid(int OIDid) {
        this.OIDid = OIDid;
    }

    public String getDish() {
        return Dish;
    }

    public void setDish(String dish) {
        Dish = dish;
    }

    public String getMess() {
        return Mess;
    }

    public void setMess(String mess) {
        Mess = mess;
    }

    public int getDishid() {
        return Dishid;
    }

    public void setDishid(int dishid) {
        Dishid = dishid;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public int getMid() {
        return Mid;
    }

    public void setMid(int mid) {
        Mid = mid;
    }

    public String getBooktype() {
        return Booktype;
    }

    public void setBooktype(String booktype) {
        Booktype = booktype;
    }

}
