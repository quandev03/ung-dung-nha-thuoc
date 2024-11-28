package com.example.ungdungnhathuoc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.ungdungnhathuoc.Activity.BaseActivity;

public class Authentication {
    private String username;
    private Boolean role;
    private final SharedPreferences sharedPreferences;


    ;

    // Constructor
    public Authentication(
            SharedPreferences sharedPreferences
    ) {
        this.sharedPreferences = sharedPreferences;
        this.username = sharedPreferences.getString("accessToken", "no"); // Mặc định là null
        this.role = sharedPreferences.getBoolean("role", false);
    }

    // Kiểm tra xem người dùng có quyền Admin hay không
    public boolean isAdmin() {
        return this.role;
    }
    // Kiểm tra xem người dùng đã đăng nhập hay chưa
    public boolean isUserLogin() {
        return this.username != null && !this.username.isEmpty();
    }


    // Lấy tên đăng nhập
    public String getUsername() {
        return this.username != null ? this.username : "Guest";
    }
}