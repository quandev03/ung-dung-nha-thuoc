package com.example.ungdungnhathuoc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import com.squareup.picasso.Picasso;


public class MainActivity extends AppCompatActivity {
    TextView homenb, settingsnb, thong_tin_don_hang_nb, thong_ke_don_hang_nb;
    private DrawerLayout drawerLayout;
    Button btnProfile, addProduce;
    EditText tvAccessToken;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the button after setting the content view
        btnProfile = findViewById(R.id.openProfile);
        tvAccessToken = findViewById(R.id.accessToken);
        addProduce = findViewById(R.id.addProduce);


        // Cach hien thi anh
//        imageView = findViewById(R.id.imageView);
//        Picasso.get()
//                .load("https://drive.google.com/uc?id=1WK6C8cnWcOuG-R53X2Pbm-B1U-O7spUk")
//                .placeholder(R.drawable.placeholder)
//                .error(R.drawable.error_image)
//                .into(imageView);


        // Enable edge-to-edge display

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


        btnProfile.setOnClickListener(view -> {
            String accessTokenString = tvAccessToken.getText().toString().trim();
            SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("accessToken", accessTokenString);
            editor.apply();
            Intent intent = new Intent(MainActivity.this, Profile.class);
            startActivity(intent);
        });

        addProduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String accessTokenString = tvAccessToken.getText().toString().trim();
                SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("accessToken", accessTokenString);
                editor.apply();
                Intent intent = new Intent(MainActivity.this, AddProduce.class);
                startActivity(intent);
            }
        });

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

//         Set up button click listener to open Profile activity    }
}}
