package com.example.ungdungnhathuoc.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.ungdungnhathuoc.LoginActivity;
import com.example.ungdungnhathuoc.MainActivity;
import com.example.ungdungnhathuoc.Profile;
import com.example.ungdungnhathuoc.R;
import com.google.android.material.navigation.NavigationView;

public abstract class BaseActivity extends AppCompatActivity {
    protected DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        // Ánh xạ các thành phần
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Xử lý sự kiện menu
        navigationView.setNavigationItemSelectedListener(item -> {
            // Gọi hàm điều hướng chung với ID item đã chọn
            handleNavigation(item.getItemId());
            drawerLayout.closeDrawers(); // Đóng Drawer sau khi chọn item
            return true;
        });
    }

    // Hàm điều hướng chung
    protected void handleNavigation(int itemId) {
        Intent intent = null;
        // Thay thế switch bằng if-else
        if (itemId == R.id.nav_home) {
            intent = new Intent(this, MainActivity.class);
        } else if (itemId == R.id.nav_orders) {
            intent = new Intent(this, ThongTinDonHangNBActivity.class);
        } else if (itemId == R.id.nav_statistic) {
            intent = new Intent(this, ThongKeDonHangActivity.class);
        } else if (itemId == R.id.nav_profile) {
            intent = new Intent(this, Profile.class);
            }else if (itemId == R.id.nav_logout) {
            intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Xóa stack
        }
        // Nếu intent không null thì bắt đầu activity
        if (intent != null) {
            startActivity(intent);
        }
    }

    // Phương thức để set layout cho từng activity con
    protected void setContentLayout(int layoutResID) {
        FrameLayout contentFrame = findViewById(R.id.content_frame);
        getLayoutInflater().inflate(layoutResID, contentFrame, true);
    }

    // Override this method from BaseActivity to add additional behavior if needed

}
