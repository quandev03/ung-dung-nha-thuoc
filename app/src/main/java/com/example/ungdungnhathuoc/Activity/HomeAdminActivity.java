package com.example.ungdungnhathuoc.Activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.ungdungnhathuoc.Authentication;
import com.example.ungdungnhathuoc.R;
import com.google.android.material.navigation.NavigationView;

public class HomeAdminActivity extends BaseActivity {

    Button btnQLDonHang, btnQLKhoHang, btnThongBaoNB, btnDangXuatNB;
    SharedPreferences sharedPre;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Enable Edge-to-Edge mode for full screen layout
        setContentView(R.layout.activity_homeadmin); // Set the layout
        sharedPre = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        this.isPermissionAdmin();

        // Adjust insets for the main view to avoid overlapping system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

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
        FrameLayout mainLayout = findViewById(R.id.main);  // Ensure the ID is correct

        // Initialize buttons
        btnQLDonHang = findViewById(R.id.btnQLDonHang);
        btnQLKhoHang = findViewById(R.id.btnQLKhoHang);
        btnThongBaoNB = findViewById(R.id.btnThongBaoNB);
//        btnDangXuatNB = findViewById(R.id.btnDangXuatNB);

        // Set onClickListener for the "Quản lý đơn hàng" button
        btnQLDonHang.setOnClickListener(view -> {
            // Chuyển sang màn hình quản lý đơn hàng
            Intent intent = new Intent(HomeAdminActivity.this, ThongKeDonHangActivity.class);
            startActivity(intent);
        });

        // Set onClickListener for the "Quản lý kho hàng" button
        btnQLKhoHang.setOnClickListener(view -> {
            // Chuyển sang màn hình quản lý kho hàng
            Intent intent = new Intent(HomeAdminActivity.this, ThongKeDonHangActivity.class);
            startActivity(intent);
        });

        // Set onClickListener for the "Thông báo" button
        btnThongBaoNB.setOnClickListener(view -> {
            // Chuyển sang màn hình thông báo
            Intent intent = new Intent(HomeAdminActivity.this, ThongKeDonHangActivity.class);
            startActivity(intent);
        });

        // Set onClickListener for the "Đăng xuất" button
//        btnDangXuatNB.setOnClickListener(view -> showExitDialog());
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        // Override back press to show logout dialog
        showExitDialog();
    }

    private void showExitDialog() {
        // Create an AlertDialog for logout confirmation
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Đăng xuất khỏi ứng dụng");
        builder.setMessage("Bạn thực sự muốn đăng xuất khỏi ứng dụng?");
        builder.setPositiveButton("Có", (dialog, which) -> {
            finish(); // Close the activity on "Yes"
        });
        builder.setNegativeButton("Không", (dialog, which) -> dialog.cancel()); // Cancel dialog on "No"
        builder.create().show();
    }

    @Override
    protected void handleNavigation(int itemId) {
        super.handleNavigation(itemId);
    }
}
