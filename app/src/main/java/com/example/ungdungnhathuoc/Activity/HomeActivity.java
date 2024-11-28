package com.example.ungdungnhathuoc.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.ungdungnhathuoc.Adapter.Adaptertrangchunm;
import com.example.ungdungnhathuoc.Model.Thuoc;
import com.example.ungdungnhathuoc.Profile;
import com.example.ungdungnhathuoc.R;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class HomeActivity extends BaseActivity {

    private ListView lvmh;
    private Adaptertrangchunm adaptertrangchunm;
    private ArrayList<Thuoc> listthuoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
//        Authentication auth = new Authentication(this, sharedPreferences);
//        String anc = String.valueOf(auth.isUserLogin());
//        Log.d("TEST", "Test: "+ anc);
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
        NavigationView navigationView = findViewById(R.id.navigationView);

        // Khởi tạo dữ liệu
        initializeData();

        // Thiết lập adapter cho ListView
        adaptertrangchunm = new Adaptertrangchunm(this, R.layout.thuoc, listthuoc);
        lvmh.setAdapter(adaptertrangchunm);

        // Xử lý sự kiện mở menu
        findViewById(R.id.menuIcon).setOnClickListener(v -> drawerLayout.openDrawer(navigationView));

        // Xử lý sự kiện khi chọn mục trong NavigationView
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                // Sử dụng if-else để xử lý các lựa chọn menu
                if (id == R.id.menu_profile) {
                    showToast("Profile được chọn");
                } else if (id == R.id.menu_cart) {
                    showToast("Giỏ hàng được chọn");
                } else if (id == R.id.menu_search) {
                    Intent intent = new Intent(HomeActivity.this, timkiem.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    return true;
                } else if (id == R.id.menu_order_info) {
                    showToast("Thông tin đơn hàng được chọn");
                } else if (id == R.id.menu_order_history) {
                    showToast("Lịch sử đặt hàng được chọn");
                } else if (id == R.id.menu_logout) {
                    showToast("Đăng xuất được chọn");
                } else {
                    // Trả về false nếu không xử lý được mục
                    return false;
                }

                // Đóng menu sau khi chọn mục
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    // Phương thức khởi tạo dữ liệu
    private void initializeData() {
        listthuoc = new ArrayList<>();
        String duongdananh = "android.resource://" + getPackageName() + "/";

        // Thêm các sản phẩm thuốc vào danh sách
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
