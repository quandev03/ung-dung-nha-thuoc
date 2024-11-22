package com.example.ungdungnhathuoc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
//        btnProfile = findViewById(R.id.openProfile);
        tvAccessToken = findViewById(R.id.accessToken);
//        addProduce = findViewById(R.id.addProduce);
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

        // Set up Navigation Drawer item clicks (reuse handleNavigation method from BaseActivity)
        navigationView.setNavigationItemSelectedListener(item -> {
            handleNavigation(item.getItemId()); // Call navigation handler from BaseActivity
            drawerLayout.closeDrawers(); // Close Drawer after item click
            return true; // Return true to indicate the item click is handled
        });

        // Check if the user is logged in by checking access token
        SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String accessToken = sharedPref.getString("accessToken", null);

        if (accessToken == null) {
            // If no access token is found, redirect to LoginActivity
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // Ensure MainActivity is not accessible after redirect
        } else {
            // If access token exists, proceed to MainActivity features
            // You can perform any setup related to logged-in user here
        }

        // Handle Profile Button click
//        btnProfile.setOnClickListener(view -> {
//            String accessTokenString = tvAccessToken.getText().toString().trim();
//            if (!accessTokenString.isEmpty()) {
//                SharedPreferences.Editor editor = sharedPref.edit();
//                editor.putString("accessToken", accessTokenString);
//                editor.apply();
//                Intent intent = new Intent(MainActivity.this, Profile.class);
//                startActivity(intent);
//            } else {
//                showToast("Vui lòng nhập access token");
//            }
//        });

        // Handle Add Produce Button click
//        addProduce.setOnClickListener(view -> {
//            String accessTokenString = tvAccessToken.getText().toString().trim();
//            if (!accessTokenString.isEmpty()) {
//                SharedPreferences.Editor editor = sharedPref.edit();
//                editor.putString("accessToken", accessTokenString);
//                editor.apply();
//                Intent intent = new Intent(MainActivity.this, AddProduce.class);
//                startActivity(intent);
//            } else {
//                showToast("Vui lòng nhập access token");
//            }
//        });
    }

    // Show a simple Toast message
    private void showToast(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    // Override this method from BaseActivity to add additional behavior if needed
    @Override
    protected void handleNavigation(int itemId) {
        super.handleNavigation(itemId); // Call navigation handler from BaseActivity
    }
}
