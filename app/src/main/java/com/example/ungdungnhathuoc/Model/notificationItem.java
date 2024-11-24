package com.example.ungdungnhathuoc.Model;

import java.io.Serializable;

public class notificationItem implements Serializable {
    String tieude;

    public notificationItem(){}

    public notificationItem(String tieude ) {
        this.tieude = tieude;
    }

    public String getTieude() {
        return tieude;
    }

    public void setTieude(String tieude) {
        this.tieude = tieude;
    }

    @Override
    public String toString() {
        return "notificationItem{" +
                "tieude='" + tieude + '\'' +
                '}';
    }
}
