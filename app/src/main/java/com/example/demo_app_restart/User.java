package com.example.demo_app_restart;

public class User {

    public String name, email, password;

    public User(){ // creates empty object to gain access to variables

    }

    public User(String name, String email, String password){
        this.name = name;
        this.email = email;
        this.password = password;
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
}
