package com.example.ungdungnhathuoc;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Gravity;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;

import com.example.ungdungnhathuoc.Activity.ThongKeDonHangActivity;
import com.example.ungdungnhathuoc.Activity.ThongTinDonHangNBActivity;
import com.example.ungdungnhathuoc.Activity.homeActivity;

public class MainActivity extends AppCompatActivity {
    TextView homenb, settingsnb, thong_tin_don_hang_nb, thong_ke_don_hang_nb;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set up the DrawerLayout and ActionBarDrawerToggle
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Lấy các TextView từ layout Drawer
        homenb = findViewById(R.id.homenb);
        settingsnb = findViewById(R.id.settingsnb);
        thong_tin_don_hang_nb = findViewById(R.id.thong_tin_don_hang_nb);
        thong_ke_don_hang_nb = findViewById(R.id.thong_ke_don_hang_nb);

        // Gán sự kiện click cho các TextView
        homenb.setOnClickListener(v -> navigateToHome());
//        settingsnb.setOnClickListener(v -> navigateToSettings());
        thong_tin_don_hang_nb.setOnClickListener(v -> navigateToOrderInfo());
        thong_ke_don_hang_nb.setOnClickListener(v -> navigateToOrderStatistics());
    }

    // Hàm điều hướng đến Home Activity
    private void navigateToHome() {
        // Chuyển đến HomeActivity
        Intent intent = new Intent(MainActivity.this, homeActivity.class);
        startActivity(intent);
        drawerLayout.closeDrawer(GravityCompat.START); // Đóng Drawer khi chuyển trang
    }

    // Hàm điều hướng đến Settings Activity
//    private void navigateToSettings() {
//        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
//        startActivity(intent);
//        drawerLayout.closeDrawer(Gravity.START);
//    }

    // Hàm điều hướng đến Order Info Activity
    private void navigateToOrderInfo() {
        Intent intent = new Intent(MainActivity.this, ThongTinDonHangNBActivity.class);
        startActivity(intent);
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    // Hàm điều hướng đến Order Statistics Activity
    private void navigateToOrderStatistics() {
        Intent intent = new Intent(MainActivity.this, ThongKeDonHangActivity.class);
        startActivity(intent);
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu resource
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            // Handle the search item click
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
