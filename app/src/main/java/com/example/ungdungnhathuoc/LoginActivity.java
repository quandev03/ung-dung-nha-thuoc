package com.example.ungdungnhathuoc;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import com.example.ungdungnhathuoc.Activity.HomeAdminActivity;
import com.example.ungdungnhathuoc.Model.SQLiteConnect;

public class LoginActivity extends AppCompatActivity {
    EditText usernameLogin, passwordLogin;
    Button loginButton;
    TextView registerTextView;
    SQLiteConnect dbHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(com.example.ungdungnhathuoc.R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

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

                // Kiểm tra đăng nhập với tài khoản admin mặc định
                if (username.equals("admin") && password.equals("admin123")) {
                    Toast.makeText(LoginActivity.this, "Chào mừng quản trị viên", Toast.LENGTH_SHORT).show();
                    // Chuyển đến màn hình chính
                    Intent intent = new Intent(LoginActivity.this, HomeAdminActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Kiểm tra tài khoản người dùng trong cơ sở dữ liệu
                    boolean isLoggedId = dbHelper.checkUser(username, password);
                    String fullname = dbHelper.getFullnameByUsernameAndPassword(username, password);
                    if (isLoggedId) {
                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công!\nChào mừng " + fullname, Toast.LENGTH_SHORT).show();
                        // Chuyển đến màn hình chính
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Khi nhấn vào dòng chữ "Don't have an account? Sign up", chuyển sang RegisterActivity
        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
