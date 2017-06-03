package com.example.a1405264.aakar_stm;

/**
 * Created by 1405264 on 2/18/2017.
 */

public class New_Project_Details
{
    String p_id;
     String project_name;
     String field;
    String date;

    public New_Project_Details() {

        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public New_Project_Details(String p_id,String project_name, String field,String date) {
        this.p_id=p_id;
        this.project_name = project_name;
        this.field = field;
        this.date=date;
    }

    public String getP_id() {
        return p_id;
    }

    public String getProject_name() {
        return project_name;
    }

    public String getField() {
        return field;
    }

    public String getDate() {
        return date;
    }
}
