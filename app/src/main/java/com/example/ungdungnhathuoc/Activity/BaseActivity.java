package com.example.ungdungnhathuoc.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.ungdungnhathuoc.AddProduce;
import com.example.ungdungnhathuoc.Authentication;
import com.example.ungdungnhathuoc.LoginActivity;
import com.example.ungdungnhathuoc.MainActivity;
import com.example.ungdungnhathuoc.Profile;
import com.example.ungdungnhathuoc.R;
import com.google.android.material.navigation.NavigationView;
public abstract class BaseActivity extends AppCompatActivity {
    protected DrawerLayout drawerLayout;
    protected Authentication auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        // Ánh xạ các thành phần
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Xử lý sự kiện menu
        navigationView.setNavigationItemSelectedListener(item -> {
            handleNavigation(item.getItemId()); // Xử lý điều hướng
            drawerLayout.closeDrawers(); // Đóng Drawer sau khi chọn item
            return true;
        });

        // Kiểm tra accessToken để bảo vệ quyền truy cập

        String accessToken = sharedPref.getString("accessToken", null);

        if (accessToken == null) {
            redirectToLogin();
        }
        Authentication auth = new Authentication( sharedPref);
        this.auth = auth;
    }

    protected void isPermissionUser(){
        if(auth.isAdmin()) {
            Intent intent = new Intent(this, HomeAdminActivity.class);
            startActivity(intent);
        }
    }
    protected void isPermissionAdmin(){
        if(!auth.isAdmin()) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }
    }
    protected void isLogin(){
        if (!auth.isUserLogin()) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    // Hàm điều hướng chung
    protected void handleNavigation(int itemId) {
        Intent intent = null;

        if (itemId == R.id.nav_home) {
            intent = new Intent(this, HomeAdminActivity.class);
        } else if (itemId == R.id.nav_statistic) {
            intent = new Intent(this, ThongKeDonHangActivity.class);
        } else if (itemId == R.id.nav_warehouse) {
            intent = new Intent(this, ThuocKhoHangActivity.class);
        } else if (itemId == R.id.nav_logout) {
            logoutUser();
            return; // Kết thúc phương thức, không cần thực thi `startActivity(intent)`
        }else if(itemId==R.id.navAddP)
            intent = new Intent(this, AddProduce.class);
        if (intent != null) {
            startActivity(intent);
        }
    }
    protected void handleNavigatioUser(int itemId) {
        if (itemId == R.id.menu_profile) {
//            showToast("Profile được chọn");
        } else if (itemId == R.id.menu_search) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
//            showToast("Giỏ hàng được chọn");
        } else if (itemId == R.id.menu_search) {
            Intent intent = new Intent(this, timkiem.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        } else if (itemId == R.id.nav_profile) {
            Intent intent  = new Intent(this, Profile.class);
            startActivity(intent);}

        else if (itemId == R.id.nav_logout) {
            logoutUser();
        }
    }

    // Hàm logout và xóa thông tin đăng nhập
    private void logoutUser() {
        // Xoá accessToken
        SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove("accessToken");
        editor.apply();

        // Chuyển đến LoginActivity
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Xóa ngăn xếp hoạt động
        startActivity(intent);
        finish(); // Đóng BaseActivity
    }

    // Phương thức để set layout cho từng activity con
    protected void setContentLayout(int layoutResID) {
        FrameLayout contentFrame = findViewById(R.id.content_frame);
        getLayoutInflater().inflate(layoutResID, contentFrame, true);
    }

    // Hàm chuyển hướng đến LoginActivity
    private void redirectToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish(); // Đóng BaseActivity
    }
}