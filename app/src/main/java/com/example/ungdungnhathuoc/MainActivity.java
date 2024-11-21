package com.example.ungdungnhathuoc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;

import com.example.ungdungnhathuoc.Activity.BaseActivity;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends BaseActivity {
    private DrawerLayout drawerLayout;
    private Button btnProfile, addProduce;
    private EditText tvAccessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        btnProfile = findViewById(R.id.openProfile);
        tvAccessToken = findViewById(R.id.accessToken);
        addProduce = findViewById(R.id.addProduce);
        drawerLayout = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);

        // Set up Toolbar and DrawerLayout
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Find the NavigationView by its ID
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Set up Navigation Drawer item clicks (tái sử dụng hàm handleNavigation từ BaseActivity)
        navigationView.setNavigationItemSelectedListener(item -> {
            handleNavigation(item.getItemId()); // Gọi hàm điều hướng từ BaseActivity
            drawerLayout.closeDrawers(); // Đóng Drawer sau khi chọn item
            return true; // Return true to indicate that the item click is handled
        });

        // Handle Profile Button click
        btnProfile.setOnClickListener(view -> {
            String accessTokenString = tvAccessToken.getText().toString().trim();
            SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("accessToken", accessTokenString);
            editor.apply();
            Intent intent = new Intent(MainActivity.this, Profile.class);
            startActivity(intent);
        });

        // Handle Add Produce Button click
        addProduce.setOnClickListener(view -> {
            String accessTokenString = tvAccessToken.getText().toString().trim();
            SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("accessToken", accessTokenString);
            editor.apply();
            Intent intent = new Intent(MainActivity.this, AddProduce.class);
            startActivity(intent);
        });
    }

    // Override this method from BaseActivity to add additional behavior if needed
    @Override
    protected void handleNavigation(int itemId) {
        super.handleNavigation(itemId); // Gọi hàm handleNavigation từ BaseActivity
    }
}
