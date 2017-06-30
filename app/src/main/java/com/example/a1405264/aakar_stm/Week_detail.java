package com.example.a1405264.aakar_stm;

public class Week_detail

{
    String id;


    String work;
    String date;

    Week_detail()
    {
    }

    public Week_detail(String id, String date, String work) {

        this.id=id;
        this.date=date;
        this.work = work;
    }

    public String getId() {
        return id;
    }


    public String getWork() {
        return work;
    }

    public String getDate() {return date;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public void setDate(String date) {
        this.date = date;
    }



}
