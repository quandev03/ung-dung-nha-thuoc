package com.example.ungdungnhathuoc;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.Adapter.Adaptertrangchunm;
import com.example.Model.thuoc;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
public class trangchunmActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    ListView lvmh;
    Adaptertrangchunm adaptertrangchunm;
    ArrayList<thuoc>listthuoc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ánh xạ
        lvmh=findViewById(R.id.lvmh);
        listthuoc= new ArrayList<>();
        String duongdananh="android.resource://"+R.class.getPackage().getName()+"/";
        //chạy thử dữ liệu
        listthuoc.add(new thuoc("Vitamin","Thuốc bổ",duongdananh+R.drawable.thuoc2,30,20,250000));
        listthuoc.add(new thuoc("Vitamin tổng hợp","Thuốc bổ",duongdananh+R.drawable.thuoc1,50,45,500000));
        listthuoc.add(new thuoc("Vitamin C","Thuốc bổ",duongdananh+R.drawable.thuoc3,50,45,250000));
        adaptertrangchunm= new Adaptertrangchunm(MainActivity.this,R.layout.thuoc,listthuoc);
        lvmh.setAdapter(adaptertrangchunm);
        // Khởi tạo DrawerLayout và NavigationView
        drawerLayout = findViewById(R.id.drawerLayout);
        NavigationView navigationView = findViewById(R.id.navigationView);

        // Xử lý khi nhấn vào biểu tượng menu
        findViewById(R.id.menuIcon).setOnClickListener(v -> {
            drawerLayout.openDrawer(navigationView); // Mở menu điều hướng
        });

        // Xử lý khi chọn một mục trong menu điều hướng
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.menu_profile) {
                    Toast.makeText(MainActivity.this, "Profile được chọn", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.menu_cart) {
                    Toast.makeText(MainActivity.this, "Giỏ hàng được chọn", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.menu_search) {
                    // Chuyển đến trang tìm kiếm
                    Intent intent = new Intent(MainActivity.this, timkiem.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    return true;
                } else if (id == R.id.menu_order_info) {
                    Toast.makeText(MainActivity.this, "Thông tin đơn hàng được chọn", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.menu_order_history) {
                    Toast.makeText(MainActivity.this, "Lịch sử đặt hàng được chọn", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.menu_logout) {
                    Toast.makeText(MainActivity.this, "Đăng xuất được chọn", Toast.LENGTH_SHORT).show();
                } else {
                    return false; // Không xử lý item này
                }
                drawerLayout.closeDrawers(); // Đóng menu sau khi chọn
                return true; // Đã xử lý xong item
            }
        });
    }
}