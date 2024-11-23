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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity {
    EditText usernameLogin, passwordLogin;
    Button loginButton;
    TextView registerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Khởi tạo view
        usernameLogin = findViewById(R.id.usernameLogin);
        passwordLogin = findViewById(R.id.passwordLogin);
        loginButton = findViewById(R.id.loginButton);
        registerTextView = findViewById(R.id.registerTextView);

        // Kiểm tra accessToken ngay khi mở activity
        SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String accessToken = sharedPref.getString("accessToken", null);

        if (accessToken != null) {
            // Nếu đã đăng nhập, điều hướng đến màn hình chính
            Intent intent = new Intent(LoginActivity.this, HomeAdminActivity.class);
            startActivity(intent);
            finish();
        }

        // Xử lý sự kiện khi bấm nút Đăng nhập
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameLogin.getText().toString().trim();
                String password = passwordLogin.getText().toString().trim();

                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập tên đăng nhập và mật khẩu", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Mã hóa mật khẩu
                String hashedPassword = hashPassword(password);
                if (hashedPassword == null) {
                    Toast.makeText(LoginActivity.this, "Đã có lỗi trong quá trình mã hóa mật khẩu", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Truy xuất SharedPreferences
                SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                String storedPassword = sharedPref.getString(username + "password", null);
                String fullname = sharedPref.getString(username + "fullname", "Người dùng");

                if (storedPassword == null) {
                    Toast.makeText(LoginActivity.this, "Tên đăng nhập không tồn tại", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Kiểm tra thông tin đăng nhập
                if (storedPassword.equals(hashedPassword)) {
                    // Lưu accessToken vào SharedPreferences
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("accessToken", username); // Lưu username làm token
                    editor.apply();

                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công!\nChào mừng " + fullname, Toast.LENGTH_SHORT).show();

                    // Chuyển đến màn hình chính
                    Intent intent = new Intent(LoginActivity.this, HomeAdminActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Đăng nhập thất bại
                    Toast.makeText(LoginActivity.this, "Tên đăng nhập hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Xử lý khi bấm vào "Đăng ký"
        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    // Hàm mã hóa mật khẩu
    public String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null; // Trả về null nếu có lỗi
        }
    }
}
