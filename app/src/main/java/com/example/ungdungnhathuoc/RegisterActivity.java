package com.example.ungdungnhathuoc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

import com.example.ungdungnhathuoc.API.HashPass;
import com.example.ungdungnhathuoc.Data.SQLiteConnect;

public class RegisterActivity extends AppCompatActivity {
    EditText emailRegister, usernameRegister, passwordRegister, repasswordRegister, fullnameRegister, addressRegister, phoneRegister;
    Button registerButton;
    TextView loginTextView;
    SQLiteConnect dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

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
                String username, password, repassword, fullname, address, phone, email;
                username = usernameRegister.getText().toString().trim();
                password = passwordRegister.getText().toString().trim();
                repassword = repasswordRegister.getText().toString().trim();
                fullname = fullnameRegister.getText().toString().trim();
                address = addressRegister.getText().toString().trim();
                phone = phoneRegister.getText().toString().trim();
                email = emailRegister.getText().toString().trim();

                // Kiểm tra tính hợp lệ
                if (!isUsernameValid(username)) {
                    Toast.makeText(RegisterActivity.this, "Tên tài khoản không hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isPasswordValid(password)) {
                    Toast.makeText(RegisterActivity.this, "Mật khẩu không hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isPhoneValid(phone)) {
                    Toast.makeText(RegisterActivity.this, "Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isEmailValid(email)) {
                    Toast.makeText(RegisterActivity.this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Kiểm tra nếu username là admin, không cho phép đăng ký
                if (username.equals("admin")) {
                    Toast.makeText(RegisterActivity.this, "Tên tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                    return;
                } else if (password.equals("admin123")) {
                    Toast.makeText(RegisterActivity.this, "Mật khẩu đã tồn tại", Toast.LENGTH_SHORT).show();
                    return;
                } else if (email.equals("admin@example.com")) {
                    Toast.makeText(RegisterActivity.this, "Email đã tồn tại", Toast.LENGTH_SHORT).show();
                    return;
                } else if (fullname.equals("Administrator")) {
                    Toast.makeText(RegisterActivity.this, "Tên người dùng đã tồn tại", Toast.LENGTH_SHORT).show();
                    return;
                } else if (phone.equals("123456789")) {
                    Toast.makeText(RegisterActivity.this, "Mật khẩu đã tồn tại", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (username.equals("") || password.equals("") || repassword.equals("") || fullname.equals("")
                        || address.equals("") || phone.equals("") || email.equals("")) {
                    Toast.makeText(RegisterActivity.this, "Vui lòng điền thông tin", Toast.LENGTH_SHORT).show();

                } else {
                    if (password.equals(repassword)) {
                        if (dbHelper.checkUsername(username)) {
                            Toast.makeText(RegisterActivity.this, "Tên tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                            return;
                        } else if (dbHelper.checkEmail(email)) {
                            Toast.makeText(RegisterActivity.this, "Email đã tồn tại", Toast.LENGTH_SHORT).show();
                            return;
                        } else if (dbHelper.checkPhone(phone)) {
                            Toast.makeText(RegisterActivity.this, "Số điện thoại đã tồn tại", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        // Lưu thông tin vào cơ sở dữ liệu
                        HashPass hashPass = new HashPass(password);
                        boolean registerSuccess = dbHelper.insertData(username, hashPass.getHashPass(), fullname, address, email, phone);
                        if (registerSuccess) {
                            Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();

                            // Chuyển sang màn hình login
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(RegisterActivity.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(RegisterActivity.this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                    }
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

    //Kiểm tra thông tin đăng ký hợp lệ không
    public boolean isUsernameValid(String username) {
        return username.matches("^[a-zA-Z0-9._]{6,20}$");
    }

    public boolean isPasswordValid(String password) {
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$");
    }

    public boolean isPhoneValid(String phone) {
        return phone.matches("^(\\+?\\d{1,3})?[0-9]{10,15}$");
    }

    public boolean isEmailValid(String email) {
        return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    }

}