package com.example.ungdungnhathuoc.Model;

import java.io.Serializable;

public class thuoc implements Serializable {
    String tenthuoc,congdung, hinhanh;
    int slhientai,sldb;
    float dongia;
    public thuoc(){};

    public thuoc(String tenthuoc, String congdung, String hinhanh, int slhientai, int sldb, float dongia) {
        this.tenthuoc = tenthuoc;
        this.congdung = congdung;
        this.hinhanh = hinhanh;
        this.slhientai = slhientai;
        this.sldb = sldb;
        this.dongia = dongia;
    }

    public String getTenthuoc() {
        return tenthuoc;
    }

    public String getCongdung() {
        return congdung;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public int getSlhientai() {
        return slhientai;
    }

    public int getSldb() {
        return sldb;
    }

    public float getDongia() {
        return dongia;
    }

    public void setTenthuoc(String tenthuoc) {
        this.tenthuoc = tenthuoc;
    }

    public void setCongdung(String congdung) {
        this.congdung = congdung;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public void setSlhientai(int slhientai) {
        this.slhientai = slhientai;
    }

    public void setSldb(int sldb) {
        this.sldb = sldb;
    }

    public void setDongia(float dongia) {
        this.dongia = dongia;
    }

    @Override
    public String toString() {
        return "thuoc{" +
                "tenthuoc='" + tenthuoc + '\'' +
                ", congdung='" + congdung + '\'' +
                ", hinhanh='" + hinhanh + '\'' +
                ", slhientai=" + slhientai +
                ", sldb=" + sldb +
                ", dongia=" + dongia +
                '}';
    }

}
