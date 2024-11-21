package com.example.ungdungnhathuoc.Request;

public class UpdateProfileInput {
    private String fullname;
    private String email;
    private String phone;
    private String address;
    public  UpdateProfileInput(
            String fullname,
            String email,
            String phone,
            String address
    ){
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    @Override
    public String toString() {
        return "UpdateProfileInput{" +
                "address='" + address + '\'' +
                ", fullname='" + fullname + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
