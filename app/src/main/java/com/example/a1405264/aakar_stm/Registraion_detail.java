package com.example.a1405264.aakar_stm;

/**
 * Created by 1405264 on 6/23/2017.
 */

public class Registraion_detail {
    String name;
    String email;
    String phone;

    public Registraion_detail()
    {

    }

    public Registraion_detail(String name,String email,String phone)
    {
        this.name=name;
        this.email=email;
        this.phone=phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
}
