package com.example.ungdungnhathuoc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ungdungnhathuoc.Model.Account;
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

public class Profile extends AppCompatActivity {
    private Button btnEditProfile;
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
        OkHttpClient client = new OkHttpClient();
        Moshi moshi = new Moshi.Builder().build();

        btnEditProfile = findViewById(R.id.btnEditProfile);
        tvFullname = findViewById(R.id.fullname);
        tvEmail = findViewById(R.id.email);
        tvPhone = findViewById(R.id.sdt);
        tvAddress = findViewById(R.id.diaChi);
        tvUsername = findViewById(R.id.username);
        String accessToken = sharedPref.getString("accessToken", null);



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        Request request = new Request.Builder()
                .url("http://10.0.2.2:3000/auth/user-detail") // Thay bằng IP/Domain thực tế
                .addHeader("Authorization", "Bearer " + accessToken) // Thêm Bearer token vào header
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("Error", "Network Error", e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    Log.e("Error", "Unexpected response code: " + response.code());
                    return;
                }

                String json = response.body().string();
//                final JsonAdapter<Account> jsonAdapter = moshi.adapter(Account.class);
//                final Account account = jsonAdapter.fromJson(json);
                JsonAdapter<ResponseData> jsonAdapter = moshi.adapter(ResponseData.class);
                try {
                    // Chuyển JSON thành ResponseData
                    ResponseData responseData = jsonAdapter.fromJson(json);

                    if (responseData != null) {
                        // Lấy thông tin từ data và hiển thị
                        Account user = responseData.getData();
                        if (user != null) {
                            Log.i("User Info", "Username: " + user.getUsername() +
                                    ", Fullname: " + user.getFullname() +
                                    ", Email: " + user.getEmail() +
                                    ", Phone: " + user.getPhone() +
                                    ", Address: " + user.getAddress());
                            runOnUiThread(()->{
                                tvFullname.setText("\uD83D\uDC64 Họ và tên: " +user.getFullname());
                                tvEmail.setText("\uD83D\uDCE7 Email: " +user.getEmail());
                                tvPhone.setText("\uD83D\uDCDE Số điện thoại: " +user.getPhone());
                                tvAddress.setText("\uD83D\uDDFA Địa chỉ: " +user.getAddress());
                                tvUsername.setText("\uD83D\uDC68 Tên đăng nhập: " +user.getUsername());


                            });
                        }
                    } else {
                        Log.e("Error", "Failed to parse JSON");
                    }
                } catch (IOException e) {
                    Log.e("Error", "Error parsing JSON", e);
                }

            }
        });

        btnEditProfile.setOnClickListener(view -> {
            Intent intent = new Intent(Profile.this, EditProfile.class);
            startActivity(intent);
        });

    }
}