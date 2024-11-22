package com.example.ungdunghieuthuoc.Models;


public class User {
    public String username;
    public String password;
    public String fullname;
    public String address;
    public String email;
    public String phone;

    //Khai báo không tham số
    public User(){}

    //Khai báo có tham số

    public User(String username, String password, String fullname, String address, String email, String phone) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.address = address;
        this.email = email;
        this.phone = phone;
    }

    //Getter and setter

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
