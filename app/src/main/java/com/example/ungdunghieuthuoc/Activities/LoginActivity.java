package com.example.ungdunghieuthuoc.Activities;

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

import com.example.ungdunghieuthuoc.Models.SQLiteConnect;
import com.example.ungdunghieuthuoc.R;

public class LoginActivity extends AppCompatActivity {
    EditText usernameLogin, passwordLogin;
    Button loginButton;
    TextView registerTextView;
    SQLiteConnect dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
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
                if (username.equals("admin") && password.equals("admin123")) {
                    Toast.makeText(LoginActivity.this, "Chào mừng quản trị viên", Toast.LENGTH_SHORT).show();
                }
                else{
                    boolean isLoggedId = dbHelper.checkUser(usernameLogin.getText().toString().trim(),
                            passwordLogin.getText().toString().trim());
                    String fullname = dbHelper.getFullnameByUsernameAndPassword(username, password);
                    if(isLoggedId){
                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công!\n" + "Chào mừng " + fullname, Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(LoginActivity.this, trangchu);
//                    startActivity(intent);
                    }
                    else {
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