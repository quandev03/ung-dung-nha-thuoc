package com.example.ungdungnhathuoc.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.ungdungnhathuoc.Adapter.Adaptertrangchunm;
import com.example.ungdungnhathuoc.AddProduce;
import com.example.ungdungnhathuoc.Authentication;
import com.example.ungdungnhathuoc.Model.Thuoc;
import com.example.ungdungnhathuoc.R;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ListView lvmh;
    private Adaptertrangchunm adaptertrangchunm;
    private ArrayList<Thuoc> listthuoc;
    private Button btnCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        Authentication auth = new Authentication(this, sharedPreferences);
//        Log.d("TEST", "Test: "+ auth.getUsername());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Ánh xạ các view
        lvmh = findViewById(R.id.lvmh);
        drawerLayout = findViewById(R.id.drawerLayout);
        btnCall= findViewById(R.id.btnCall);

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

        btnCall.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, AddProduce.class);
            startActivity(intent);
        });
    }

    // Phương thức khởi tạo dữ liệu
    private void initializeData() {
        listthuoc = new ArrayList<>();
        String duongdananh = "android.resource://" + getPackageName() + "/";

        // Thêm các sản phẩm thuốc vào danh sách
        listthuoc.add(new Thuoc("Vitamin", "Thuốc bổ", duongdananh + R.drawable.thuoc2, 30, 20, 250000, "loai", 1));
        listthuoc.add(new Thuoc("Vitamin tổng hợp", "Thuốc bổ", duongdananh + R.drawable.thuoc1, 50, 45, 500000, "loai", 2));
        listthuoc.add(new Thuoc("Vitamin C", "Thuốc bổ", duongdananh + R.drawable.thuoc3, 50, 45, 250000, "loai", 3));
    }

    // Phương thức hiển thị Toast
    private void showToast(String message) {
        Toast.makeText(HomeActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
