package com.example.a1405264.aakar_stm;

/**
 * Created by 1405264 on 6/24/2017.
 */

public class TasK_try {

    String task;
    String name;
    String status;
    String desc;


    public   TasK_try(){

    }



    public  TasK_try(String task, String name, String desc, String status)
    {
        this.task=task;
        this.name=name;
        this.status=status;

        this.desc=desc;
    }


    public String getTask() {
        return task;
    }

    public String getName() {
        return name;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() { return status;}
    public String getDesc() { return desc; }

    public void setDesc(String desc) { this.desc = desc;
    }

    @Override
    public String toString() {
        return this.status+":"+this.name+":"+this.task+":";
    }
}
