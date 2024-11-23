package com.example.ungdungnhathuoc;

import android.annotation.SuppressLint;
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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RegisterActivity extends AppCompatActivity {

    EditText emailRegister, usernameRegister, passwordRegister, fullnameRegister, phoneRegister;
    Button registerButton;
    TextView loginTextView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailRegister = findViewById(R.id.emailRegister);
        usernameRegister = findViewById(R.id.usernameRegister);
        passwordRegister = findViewById(R.id.passwordRegister);
        fullnameRegister = findViewById(R.id.fullnameRegister);
        phoneRegister = findViewById(R.id.phoneRegister);
        registerButton = findViewById(R.id.registerButton);
        loginTextView = findViewById(R.id.loginTextView);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameRegister.getText().toString().trim();
                String password = passwordRegister.getText().toString().trim();
                String fullname = fullnameRegister.getText().toString().trim();
                String phone = phoneRegister.getText().toString().trim();
                String email = emailRegister.getText().toString().trim();

                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password) ||
                        TextUtils.isEmpty(fullname) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(email)) {
                    Toast.makeText(RegisterActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Mã hóa mật khẩu trước khi lưu
                String hashedPassword = hashPassword(password);

                SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                if (sharedPref.contains(username + "username")) {
                    Toast.makeText(RegisterActivity.this, "Tên tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Lưu thông tin vào SharedPreferences
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(username + "username", username);
                editor.putString(username + "password", hashedPassword);
                editor.putString(username + "fullname", fullname);
                editor.putString(username + "phone", phone);
                editor.putString(username + "email", email);
                editor.apply();

                Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
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
