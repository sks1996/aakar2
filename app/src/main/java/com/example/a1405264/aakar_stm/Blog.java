package com.example.a1405264.aakar_stm;

/**
 * Created by 1405264 on 5/29/2017.
 */

public class Blog {
    private String title;
    private String desc;
    private String  Date;
    private String image;


    public Blog(){

    }

    public Blog(String title, String desc, String date, String image) {
        this.title = title;
        this.desc = desc;
        Date = date;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
