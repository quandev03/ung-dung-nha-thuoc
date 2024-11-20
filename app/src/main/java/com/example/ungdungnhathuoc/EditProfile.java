package com.example.ungdungnhathuoc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ungdungnhathuoc.Model.Account;
import com.example.ungdungnhathuoc.Request.UpdateProfileInput;
import com.example.ungdungnhathuoc.Response.ResponseData;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EditProfile extends AppCompatActivity {
    Button btnSave, btnCancel;
    EditText edtUsername, edtFullname, edtEmail, edtPhone, edtAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_profile);

        OkHttpClient client = new OkHttpClient();
        Moshi moshi = new Moshi.Builder().build();
        edtAddress = findViewById(R.id.edit_address);
        edtEmail = findViewById(R.id.edit_email);
        edtFullname = findViewById(R.id.edit_fullname);
        edtPhone = findViewById(R.id.edit_phone);
        edtUsername = findViewById(R.id.edit_username);
        btnCancel = findViewById(R.id.btn_cancel);
        btnSave = findViewById(R.id.btn_save);
        // get accessToken
        SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String accessToken = sharedPref.getString("accessToken", null);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.edit_profile_layout), (v, insets) -> {
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
                                edtAddress.setText(user.getAddress());
                                edtEmail.setText(user.getEmail());
                                edtFullname.setText(user.getFullname());
                                edtPhone.setText(user.getPhone());
                                edtUsername.setText(user.getUsername());



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
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditProfile.this, Profile.class);
                startActivity(intent);

            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullname = edtFullname.getText().toString().trim();
                String address = edtAddress.getText().toString().trim();
                String phone = edtPhone.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();
                String username = edtUsername.getText().toString().trim();

                // Kiểm tra dữ liệu hợp lệ (có thể thêm điều kiện)
                if (fullname.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                    Toast.makeText(EditProfile.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Tạo đối tượng dữ liệu cần gửi
                UpdateProfileInput dataUpdate = new UpdateProfileInput(fullname, email, phone, address);

                // Chuyển đối tượng thành JSON (sử dụng Moshi hoặc Gson)
                Moshi moshi = new Moshi.Builder().build();
                JsonAdapter<UpdateProfileInput> jsonAdapter = moshi.adapter(UpdateProfileInput.class);
                String jsonData = jsonAdapter.toJson(dataUpdate);

                // Đảm bảo token không rỗng

                // Tạo RequestBody từ chuỗi JSON
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                RequestBody body = RequestBody.create(JSON, jsonData);

                // Tạo yêu cầu PUT
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://10.0.2.2:3000/auth/update-user")
                        .addHeader("Authorization", "Bearer " + accessToken)
                        .put(body)
                        .build();

                // Gửi yêu cầu bất đồng bộ
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                        // Thông báo lỗi nếu có vấn đề khi kết nối
                        runOnUiThread(() -> Toast.makeText(EditProfile.this, "Network Error", Toast.LENGTH_SHORT).show());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        // Kiểm tra mã phản hồi
                        if (!response.isSuccessful()) {
                            Log.e("Error", "Unexpected response code: " + response.code());
                            return;
                        }
                        if (response.isSuccessful()) {
                            // Xử lý phản hồi thành công
                            String responseBody = response.body().string();
                            runOnUiThread(() -> {
                                // Hiển thị thông báo khi thành công
                                Toast.makeText(EditProfile.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                            });
                        } else {
                            // Xử lý nếu mã phản hồi không phải 2xx
                            runOnUiThread(() -> Toast.makeText(EditProfile.this, "Failed to update profile", Toast.LENGTH_SHORT).show());
                        }
                    }
                });
            }
        });

    };

}