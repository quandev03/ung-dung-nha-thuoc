package com.example.ungdungnhathuoc.Model;

import android.graphics.Bitmap;

public class DonHang {
    String tenDH;
    double giaDH;
    String logoDH;
    String maDH;
    public DonHang(){

    }

    public DonHang(String tenDH, double giaDH, String logoDH, String maDH) {
        this.tenDH = tenDH;
        this.giaDH = giaDH;
        this.logoDH = logoDH;
        this.maDH = maDH;
    }

    public String getTenDH() {
        return tenDH;
    }

    public void setTenDH(String tenDH) {
        this.tenDH = tenDH;
    }

    public double getGiaDH() {
        return giaDH;
    }

    public void setGiaDH(double giaDH) {
        this.giaDH = giaDH;
    }

    public String getLogoDH() {
        return logoDH;
    }

    public void setLogoDH(String logoDH) {
        this.logoDH = logoDH;
    }

    public String getMaDH() {
        return maDH;
    }

    public void setMaDH(String maDH) {
        this.maDH = maDH;
    }
}
