package com.example.ungdungnhathuoc.Model;

import java.io.Serializable;

public class Thuoc implements Serializable {
    String tenthuoc,congdung, hinhanh, loai;
    int slhientai,sldb, id;
    float dongia;
    public Thuoc(){};

    public Thuoc(String tenthuoc, String congdung, String hinhanh, int slhientai, int sldb, float dongia, String loai, int id) {
        this.tenthuoc = tenthuoc;
        this.congdung = congdung;
        this.hinhanh = hinhanh;
        this.slhientai = slhientai;
        this.sldb = sldb;
        this.dongia = dongia;
        this.loai = loai;
        this.id = id;
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

    public String getLoai() {
        return loai;
    }
    public void setLoai(String loai) {
        this.loai = loai;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Thuoc{" +
                "congdung='" + congdung + '\'' +
                ", tenthuoc='" + tenthuoc + '\'' +
                ", hinhanh='" + hinhanh + '\'' +
                ", loai='" + loai + '\'' +
                ", slhientai=" + slhientai +
                ", sldb=" + sldb +
                ", id=" + id +
                ", dongia=" + dongia +
                '}';
    }
}
