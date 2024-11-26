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
import com.example.ungdungnhathuoc.Model.SQLiteConnect;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity {
    EditText usernameLogin, passwordLogin;
    Button loginButton;
    TextView registerTextView;
    SQLiteConnect dbHelper;
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
        dbHelper = new SQLiteConnect(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameLogin.getText().toString().trim();
                String password = passwordLogin.getText().toString().trim();
                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập tên đăng nhập và mật khẩu", Toast.LENGTH_SHORT).show();
                    return;
                }
//                String hashedPassword = hashPassword(password);
//                if (hashedPassword == null) {
//                    Toast.makeText(LoginActivity.this, "Đã có lỗi trong quá trình mã hóa mật khẩu", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                boolean isLoggedId = dbHelper.checkUser(username, password);
                String fullname = dbHelper.getFullnameByUsernameAndPassword(username, password);
                if (isLoggedId) {
                    // Lưu accessToken vào SharedPreferences
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("accessToken", username);
                    editor.apply();
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công!\nChào mừng " + fullname, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, HomeAdminActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                }
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
            return null;
        }
    }
}