package com.example.ungdungnhathuoc.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.ungdungnhathuoc.Adapter.Adaptertrangchunm;
import com.example.ungdungnhathuoc.Authentication;
import com.example.ungdungnhathuoc.Data.SQLiteConnect;
import com.example.ungdungnhathuoc.LoginActivity;
import com.example.ungdungnhathuoc.Model.Thuoc;
import com.example.ungdungnhathuoc.Profile;
import com.example.ungdungnhathuoc.R;
import com.example.ungdungnhathuoc.Request.FilterOrder;
import com.example.ungdungnhathuoc.Xemchitiet;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class HomeActivity extends BaseActivity {

    private ListView lvmh;
    private Adaptertrangchunm adaptertrangchunm;
    private ArrayList<Thuoc> listthuoc;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private SQLiteConnect sqLiteConnect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sqLiteConnect = new SQLiteConnect(this);

        // Check access token first, if not present redirect to login
        SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        this.isPermissionUser();
        FilterOrder filterOrder,filterOrder1;


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
        lvmh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent xemchitietmh = new Intent(HomeActivity.this, Xemchitiet.class);
                Bundle data = new Bundle();
                Thuoc thuoc=adaptertrangchunm.getListhuoc().get(i);
                data.putSerializable("thuoc_value", thuoc);
                xemchitietmh.putExtras(data);
                startActivity(xemchitietmh);

                Toast.makeText(HomeActivity.this, listthuoc.get(i).getTenthuoc(), Toast.LENGTH_SHORT).show();
            }
        });

        // Xử lý sự kiện mở menu
        findViewById(R.id.drawerLayout).setOnClickListener(v -> drawerLayout.openDrawer(navigationView));
    }

    // Phương thức khởi tạo dữ liệu
    private void initializeData() {
        listthuoc = sqLiteConnect.getAllThuoc();

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
        super.handleNavigatioUser(itemId);
    }
}