package com.example.ungdungnhathuoc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ungdungnhathuoc.Activity.HomeActivity;
import com.example.ungdungnhathuoc.Activity.HomeAdminActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Kiểm tra accessToken trong SharedPreferences
        SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String accessToken = sharedPref.getString("accessToken", null);

        // Kiểm tra xem accessToken có tồn tại và không rỗng
        if (accessToken == null || accessToken.trim().isEmpty()) {
            // Chưa đăng nhập, chuyển về LoginActivity
            Toast.makeText(this, "Bạn chưa đăng nhập. Vui lòng đăng nhập để tiếp tục.", Toast.LENGTH_SHORT).show();
            // Chuyển đến LoginActivity
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // Kết thúc MainActivity để không quay lại được
        } else {
            // Đã đăng nhập, chuyển đến HomeAdminActivity
            Toast.makeText(this, "Chào mừng bạn quay lại!", Toast.LENGTH_SHORT).show();
            // Chuyển đến HomeAdminActivity
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
            finish(); // Kết thúc MainActivity để không quay lại được
        }
    }
}
