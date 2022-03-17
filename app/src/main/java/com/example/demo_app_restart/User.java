package com.example.demo_app_restart;

public class User {

    public String name, email, password, phoneNo;

    public User(){ // creates empty object to gain access to variables

    }

    public User(String name, String email, String password, String phoneNo){
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNo = phoneNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password){this.password = password;}

    public void getPassword(String password){this.password = password;}

    public  void getPhoneNo(String phoneNo){this.phoneNo = phoneNo;}

    public  void setPhoneNo(String phoneNo){this.phoneNo = phoneNo;}
}
