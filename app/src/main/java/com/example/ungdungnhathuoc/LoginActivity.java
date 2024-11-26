package com.example.ungdungnhathuoc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ungdungnhathuoc.Activity.HomeAdminActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    EditText usernameLogin, passwordLogin;
    Button loginButton;
    TextView registerTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Kiểm tra xem người dùng đã đăng nhập chưa bằng accessToken
        SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String accessToken = sharedPref.getString("accessToken", null);
        if (accessToken != null) {
            Intent intent = new Intent(LoginActivity.this, HomeAdminActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        usernameLogin = findViewById(R.id.usernameLogin);
        passwordLogin = findViewById(R.id.passwordLogin);
        loginButton = findViewById(R.id.loginButton);
        registerTextView = findViewById(R.id.registerTextView);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameLogin.getText().toString().trim();
                String password = passwordLogin.getText().toString().trim();

                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập tên đăng nhập và mật khẩu", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Tạo đối tượng JSON để gửi lên API
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("username", username);
                    jsonObject.put("password", password); // Sử dụng mật khẩu gốc, không mã hóa
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(LoginActivity.this, "Lỗi xử lý dữ liệu", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Gửi yêu cầu đăng nhập tới API
                OkHttpClient client = new OkHttpClient();
                RequestBody body = RequestBody.create(
                        MediaType.parse("application/json; charset=utf-8"),
                        jsonObject.toString()
                );

                Request request = new Request.Builder()
                        .url("https://api.quandev03.id.vn/auth/login") // Đổi URL với endpoint API đăng nhập
                        .post(body)
                        .build();

                client.newCall(request).enqueue(new okhttp3.Callback() {
                    @Override
                    public void onFailure(okhttp3.Call call, IOException e) {
                        e.printStackTrace();
                        runOnUiThread(() -> Toast.makeText(LoginActivity.this, "Lỗi kết nối mạng: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                    }

                    @Override
                    public void onResponse(okhttp3.Call call, Response response) throws IOException {
                        String responseBody = response.body().string(); // Lưu phản hồi

                        if (response.isSuccessful()) {
                            try {
                                // Phân tích JSON phản hồi từ API
                                JSONObject responseJson = new JSONObject(responseBody);
                                String accessToken = responseJson.optString("accessToken");
                                String fullname = responseJson.optString("fullname");

                                if (accessToken != null) {
                                    // Lưu accessToken vào SharedPreferences
                                    SharedPreferences.Editor editor = sharedPref.edit();
                                    editor.putString("accessToken", accessToken);
                                    editor.apply();

                                    // Hiển thị thông báo và chuyển sang màn hình HomeAdmin
                                    runOnUiThread(() -> {
                                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công!\nChào mừng " + fullname, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(LoginActivity.this, HomeAdminActivity.class);
                                        startActivity(intent);
                                        finish();
                                    });
                                } else {
                                    runOnUiThread(() -> Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show());
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                runOnUiThread(() -> Toast.makeText(LoginActivity.this, "Lỗi xử lý phản hồi", Toast.LENGTH_SHORT).show());
                            }
                        } else {
                            runOnUiThread(() -> Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show());
                        }
                    }
                });
            }
        });

        // Khi nhấn vào dòng chữ "Don't have an account? Sign up"
        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
