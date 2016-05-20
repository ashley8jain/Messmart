package com.ashleyjain.messmart;

/**
 * Created by ashleyjain on 19/05/16.
 */
public class MessObject {
    private int id;
    private String title;
    private String description;
    private int price;
    private boolean isVeg;
    private String pic;
    private String name;

    public MessObject(int id, String title, String description, int price, boolean isVeg, String pic,String name) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.isVeg = isVeg;
        this.pic = pic;
        this.name = name;
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
