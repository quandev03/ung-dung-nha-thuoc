package com.example.ungdungnhathuoc.Model;

public class Account {
    private int id;
    private String username;
    private String fullname;
    private String password;
    private String email;
    private String phone;
    private String address;
    private boolean role;

    public Account() {
        this.id = 0;
        this.username = "";
        this.fullname = "";
        this.password = "";
        this.email = "";
        this.phone = "";
        this.address = "";
        this.role = false;
    }

    public Account(
            int id,
            String username,
            String fullname,
            String password,
            String email,
            String phone,
            String address,
            boolean role
    ){
        this.address=address;
        this.email=email;
        this.fullname=fullname;
        this.id=id;
        this.password=password;
        this.phone=phone;
        this.role=role;
        this.username=username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isRole() {
        return role;
    }

    public void setRole(boolean role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Account{" +
                "address='" + address + '\'' +
                ", id=" + id +
                ", username='" + username + '\'' +
                ", fullname='" + fullname + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", role=" + role +
                '}';
    }
}
