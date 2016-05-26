package com.ashleyjain.messmart.Object;

/**
 * Created by ashleyjain on 19/05/16.
 */
public class MessObject {
    private int dishId,id;

    public String getLord() {
        return lord;
    }

    public void setLord(String lord) {
        this.lord = lord;
    }

    public int getDishId() {
        return dishId;
    }

    public void setDishId(int dishId) {
        this.dishId = dishId;
    }

    private String lord,title;
    private String description;
    private int price;
    private boolean isVeg;
    private String pic;
    private String name;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }

    private String address;
    private String timing;

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public int getBook() {
        return book;
    }

    public void setBook(int book) {
        this.book = book;
    }

    private String datetime;
    private int book;

    public MessObject(int dishId,String lord,int id, String title, String description, int price, boolean isVeg, String pic,String name,String address,String timing,String datetime,int book) {
        this.dishId = dishId;
        this.lord = lord;
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.isVeg = isVeg;
        this.pic = pic;
        this.name = name;
        this.address = address;
        this.timing = timing;
        this.datetime = datetime;
        this.book = book;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isVeg() {
        return isVeg;
    }

    public void setIsVeg(boolean isVeg) {
        this.isVeg = isVeg;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}
