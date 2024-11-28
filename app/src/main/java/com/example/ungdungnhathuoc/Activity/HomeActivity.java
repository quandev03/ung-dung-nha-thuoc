package com.example.ungdungnhathuoc.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.ungdungnhathuoc.Adapter.Adaptertrangchunm;
import com.example.ungdungnhathuoc.LoginActivity;
import com.example.ungdungnhathuoc.Model.Thuoc;
import com.example.ungdungnhathuoc.Profile;
import com.example.ungdungnhathuoc.R;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class HomeActivity extends BaseActivity {

    private ListView lvmh;
    private Adaptertrangchunm adaptertrangchunm;
    private ArrayList<Thuoc> listthuoc;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Check access token first, if not present redirect to login
        SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String accessToken = sharedPref.getString("accessToken", null);
        if (accessToken == null) {
            redirectToLogin();
            return;
        }

        // Ánh xạ các view
        lvmh = findViewById(R.id.lvmh);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);

        // Set up Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set up DrawerToggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Xử lý sự kiện chọn item từ NavigationView
        navigationView.setNavigationItemSelectedListener(item -> {
            handleNavigation(item.getItemId());
            drawerLayout.closeDrawers();
            return true;
        });

        // Khởi tạo dữ liệu
        initializeData();

        // Thiết lập adapter cho ListView
        adaptertrangchunm = new Adaptertrangchunm(this, R.layout.thuoc, listthuoc);
        lvmh.setAdapter(adaptertrangchunm);

        // Xử lý sự kiện mở menu
        findViewById(R.id.drawerLayout).setOnClickListener(v -> drawerLayout.openDrawer(navigationView));
    }

    // Phương thức khởi tạo dữ liệu
    private void initializeData() {
        listthuoc = new ArrayList<>();
        String duongdananh = "android.resource://" + getPackageName() + "/";

        listthuoc.add(new Thuoc("Vitamin", "Thuốc bổ", duongdananh + R.drawable.thuoc2, 30, 20, 250000));
        listthuoc.add(new Thuoc("Vitamin tổng hợp", "Thuốc bổ", duongdananh + R.drawable.thuoc1, 50, 45, 500000));
        listthuoc.add(new Thuoc("Vitamin C", "Thuốc bổ", duongdananh + R.drawable.thuoc3, 50, 45, 250000));
    }

    // Phương thức hiển thị Toast
    private void showToast(String message) {
        Toast.makeText(HomeActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    // Hàm logout
    private void logoutUser() {
        // Xóa accessToken trong SharedPreferences
        SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove("accessToken");
        editor.apply();

        // Hiển thị thông báo
        showToast("Đăng xuất thành công");

        // Chuyển hướng đến LoginActivity và xóa stack
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    // Chuyển hướng về LoginActivity nếu không có accessToken
    private void redirectToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    // Handle navigation item selection
    protected void handleNavigation(int itemId) {
        if (itemId == R.id.menu_profile) {
            showToast("Profile được chọn");
        } else if (itemId == R.id.menu_cart) {
            showToast("Giỏ hàng được chọn");
        } else if (itemId == R.id.menu_search) {
            Intent intent = new Intent(HomeActivity.this, timkiem.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        } else if (itemId == R.id.nav_profile) {
            Intent intent  = new Intent(this, Profile.class);
            startActivity(intent);}

        else if (itemId == R.id.nav_logout) {
            logoutUser();
        }
    }
//    handleNavigationUser
}
