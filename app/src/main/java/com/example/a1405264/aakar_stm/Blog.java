package com.example.a1405264.aakar_stm;

/**
 * Created by Anas Khan on 5/27/2017.
 */

public class Blog {

private String title;
    private String desc;
    private String  Date;
    private String image;
    private String status;


    public Blog(){

    }

    public Blog(String title, String desc, String date,String status, String image) {
        this.title = title;
        this.desc = desc;
        this.status=status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
