package com.example.ungdungnhathuoc;

import android.content.Context;
import android.content.SharedPreferences;

public class Authentication {
    private final Context context;
    private String username;
    private Boolean role;
    private final SharedPreferences sharedPreferences;


    ;

    // Constructor
    public Authentication(
            Context context,
            SharedPreferences sharedPreferences
    ) {
        this.context = context;
        this.sharedPreferences = sharedPreferences;


//        // Load stored values
        this.username = sharedPreferences.getString("accessToken", "no"); // Mặc định là null
        this.role = sharedPreferences.getBoolean("role", false);
    }

    // Kiểm tra xem người dùng có quyền Admin hay không
    public boolean isAdmin() {
        return this.role != null && this.role;
    }

    // Kiểm tra xem người dùng đã đăng nhập hay chưa
    public boolean isUserLogin() {
        return this.username != null && !this.username.isEmpty();
    }

    // Lấy tên đăng nhập
//    public String getUsername() {
//        return this.username != null ? this.username : "Guest";
//    }

    // Cập nhật thông tin đăng nhập
//    public void login(String username, boolean role) {
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("accessToken", username);
//        editor.putBoolean("role", role);
//        editor.apply();
//
//        // Cập nhật giá trị trong đối tượng
//        this.username = username;
//        this.role = role;
//    }

    // Đăng xuất
//    public void logout() {
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.remove("accessToken");
//        editor.remove("role");
//        editor.apply();
//
//        // Xóa dữ liệu trong đối tượng
//        this.username = null;
//        this.role = false;
//    }
}