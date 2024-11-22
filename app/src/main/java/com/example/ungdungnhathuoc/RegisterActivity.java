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

import com.example.ungdungnhathuoc.Model.SQLiteConnect;

public class RegisterActivity extends AppCompatActivity {

    EditText emailRegister, usernameRegister, passwordRegister, repasswordRegister, fullnameRegister, addressRegister, phoneRegister;
    Button registerButton;
    TextView loginTextView;
    SQLiteConnect dbHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Kết nối với các EditText và Button
        emailRegister = findViewById(R.id.emailRegister);
        usernameRegister = findViewById(R.id.usernameRegister);
        passwordRegister = findViewById(R.id.passwordRegister);
        repasswordRegister = findViewById(R.id.repasswordRegister);
        fullnameRegister = findViewById(R.id.fullnameRegister);
        addressRegister = findViewById(R.id.addressRegister);
        phoneRegister = findViewById(R.id.phoneRegister);
        registerButton = findViewById(R.id.registerButton);
        loginTextView = findViewById(R.id.loginTextView);
        dbHelper = new SQLiteConnect(this);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameRegister.getText().toString().trim();
                String password = passwordRegister.getText().toString().trim();
                String repassword = repasswordRegister.getText().toString().trim();
                String fullname = fullnameRegister.getText().toString().trim();
                String address = addressRegister.getText().toString().trim();
                String phone = phoneRegister.getText().toString().trim();
                String email = emailRegister.getText().toString().trim();

                // Kiểm tra các trường hợp không hợp lệ
                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(repassword) || TextUtils.isEmpty(fullname)
                        || TextUtils.isEmpty(address) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(email)) {
                    Toast.makeText(RegisterActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Kiểm tra nếu username là admin, không cho phép đăng ký
                if (username.equals("admin")) {
                    Toast.makeText(RegisterActivity.this, "Tên tài khoản không hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Kiểm tra mật khẩu có trùng khớp
                if (!password.equals(repassword)) {
                    Toast.makeText(RegisterActivity.this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Kiểm tra username, email, và các thông tin khác có tồn tại trong cơ sở dữ liệu hay không
                if (dbHelper.checkUsername(username)) {
                    Toast.makeText(RegisterActivity.this, "Tên tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (dbHelper.checkEmail(email)) {
                    Toast.makeText(RegisterActivity.this, "Email đã tồn tại", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (dbHelper.checkPhone(phone)) {
                    Toast.makeText(RegisterActivity.this, "Số điện thoại đã tồn tại", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Đăng ký thành công
                boolean registerSuccess = dbHelper.insertData(username, password, fullname, address, phone, email);
                if (registerSuccess) {
                    // Tạo accessToken sau khi đăng ký thành công
                    String accessToken = username;  // Ví dụ, sử dụng username làm accessToken

                    // Lưu accessToken vào SharedPreferences
                    SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("accessToken", accessToken);  // Lưu accessToken
                    editor.apply();

                    Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();

                    // Chuyển đến màn hình LoginActivity
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();  // Đảm bảo đăng ký hoàn tất và không quay lại màn hình này
                } else {
                    Toast.makeText(RegisterActivity.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Khi nhấn vào dòng chữ "Do you have an account? Log in"
        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
