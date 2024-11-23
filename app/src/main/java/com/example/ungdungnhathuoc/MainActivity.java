package com.example.ungdungnhathuoc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.ungdungnhathuoc.Activity.BaseActivity;
import com.example.ungdungnhathuoc.Activity.HomeAdminActivity;

public class MainActivity extends BaseActivity {
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Kiểm tra accessToken trong SharedPreferences
        SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String accessToken = sharedPref.getString("accessToken", null);

        if (accessToken == null || accessToken.isEmpty()) {
            // Chưa đăng nhập, chuyển về LoginActivity
            Toast.makeText(this, "Bạn chưa đăng nhập. Vui lòng đăng nhập để tiếp tục.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // Kết thúc MainActivity để không quay lại được
        } else {
            // Đã đăng nhập, chuyển đến HomeAdminActivity
            Toast.makeText(this, "Chào mừng bạn quay lại!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, HomeAdminActivity.class);
            startActivity(intent);
            finish(); // Kết thúc MainActivity để không quay lại được
        }
    }


    // Hàm xử lý đăng xuất
//    public void logout() {
//        // Xóa accessToken khi đăng xuất
//        SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPref.edit();
//        editor.remove("accessToken");
//        editor.apply();
//
//        Toast.makeText(this, "Đã đăng xuất thành công", Toast.LENGTH_SHORT).show();
//
//        // Chuyển đến màn hình đăng nhập sau khi đăng xuất
//        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//        startActivity(intent);
//        finish();
//    }
}
