package com.example.ungdungnhathuoc.Model;

public class DonHang {
    String tenDH;
    double giaDH;
    int logoDH;
    String maDH;
    public DonHang(){

    }

    public DonHang(String tenDH, double giaDH, int logoDH, String maDH) {
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

    public int getLogoDH() {
        return logoDH;
    }

    public void setLogoDH(int logoDH) {
        this.logoDH = logoDH;
    }

    public String getMaDH() {
        return maDH;
    }

    public void setMaDH(String maDH) {
        this.maDH = maDH;
    }
}
