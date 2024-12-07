package com.example.ungdungnhathuoc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ungdungnhathuoc.Activity.BaseActivity;
import com.example.ungdungnhathuoc.Activity.HomeActivity;
import com.example.ungdungnhathuoc.Data.SQLiteConnect;
import com.example.ungdungnhathuoc.Model.Account;
import com.example.ungdungnhathuoc.Model.User;
import com.example.ungdungnhathuoc.Response.ResponseData;
import com.google.gson.Gson;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Profile extends BaseActivity {
    private Button btnEditProfile, btnBackHome;
    private TextView tvFullname;
    private TextView tvEmail;
    private TextView tvPhone;
    private TextView tvAddress;
    private TextView tvUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
//        this.isLogin();
        SQLiteConnect sqLiteConnect = new SQLiteConnect(this);
        User user = sqLiteConnect.userDetail(sharedPref.getString("accessToken", null));
        Log.d("User", "User: "+ user.getFullname());
        btnEditProfile = findViewById(R.id.btnEditProfile);
        tvFullname = findViewById(R.id.fullname);
        tvEmail = findViewById(R.id.email);
        tvPhone = findViewById(R.id.sdt);
        tvAddress = findViewById(R.id.diaChi);
        tvUsername = findViewById(R.id.username);
        btnBackHome = findViewById(R.id.btnback);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tvFullname.setText(user.getFullname());
        tvEmail.setText(user.getEmail());
        tvPhone.setText(user.getPhone());
        tvAddress.setText(user.getAddress());
        tvUsername.setText(user.getUsername());






        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });




        btnEditProfile.setOnClickListener(view -> {
            Intent intent = new Intent(Profile.this, EditProfile.class);
            startActivity(intent);
        });
        btnBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, HomeActivity.class);
                startActivity(intent);
            }
        });

    }
}