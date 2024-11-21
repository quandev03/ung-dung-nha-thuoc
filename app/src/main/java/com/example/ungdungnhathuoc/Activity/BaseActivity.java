package com.example.ungdungnhathuoc.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.ungdungnhathuoc.LoginActivity;
import com.example.ungdungnhathuoc.MainActivity;
import com.example.ungdungnhathuoc.R;
import com.google.android.material.navigation.NavigationView;

public class BaseActivity  extends AppCompatActivity {
    protected DrawerLayout drawerLayout;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        // Ánh xạ các thành phần
        drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout = findViewById(R.id.drawer_menu);

        NavigationView navigationView = findViewById(R.id.nav_view);

        // Xử lý sự kiện menu
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    // Chuyển sang HomeActivity
                    startActivity(new Intent(this, MainActivity.class));
                    break;
                case R.id.nav_orders:
                    // Chuyển sang OrdersActivity
                    startActivity(new Intent(this, ThongTinDonHangNBActivity.class));
                    break;
                case R.id.nav_statistic:
                    // Chuyển sang StatisticsActivity
                    startActivity(new Intent(this, ThongKeDonHangActivity.class));
                    break;
                case R.id.nav_logout:
                    // Đăng xuất và quay về màn hình đăng nhập
                    Intent intent = new Intent(this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Xóa stack
                    startActivity(intent);
                    break;
            }
            drawerLayout.closeDrawers(); // Đóng Navigation Drawer sau khi chọn
            return true;
        });
    }

    // Phương thức để set layout cho từng activity con
    protected void setContentLayout(int layoutResID) {
        FrameLayout contentFrame = findViewById(R.id.content_frame);
        getLayoutInflater().inflate(layoutResID, contentFrame, true);
    }
}
