package com.example.a1405264.aakar_stm;

/**
 * Created by 1405264 on 6/24/2017.
 */

public class TasK_try {

    String task;
    String name;
    String status;


    public   TasK_try(){

    }
    public  TasK_try(String task,String name, String status)
    {
        this.task=task;
        this.name=name;
        this.status=status;
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

    public String getStatus() {
        return status;

    }

}
