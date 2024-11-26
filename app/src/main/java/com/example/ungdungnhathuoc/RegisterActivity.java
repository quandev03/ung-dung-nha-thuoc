package com.example.ungdungnhathuoc;

import android.content.Intent;
import android.content.SharedPreferences;
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

import com.example.ungdungnhathuoc.Model.SQLiteConnect;
import com.example.ungdungnhathuoc.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class RegisterActivity extends AppCompatActivity {
    EditText emailRegister, usernameRegister, passwordRegister,repasswordRegister, fullnameRegister, addressRegister, phoneRegister;
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
                if (username.isEmpty() || password.isEmpty() || repassword.isEmpty() || fullname.isEmpty()
                        || address.isEmpty() || phone.isEmpty() || email.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(repassword)) {
                    Toast.makeText(RegisterActivity.this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!isUsernameValid(username) || !isPasswordValid(password) || !isPhoneValid(phone) || !isEmailValid(email)) {
                    Toast.makeText(RegisterActivity.this, "Thông tin nhập vào không hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Tạo đối tượng JSON để gửi
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("username", username);
                    jsonObject.put("password", password);
                    jsonObject.put("fullname", fullname);
                    jsonObject.put("address", address);
                    jsonObject.put("phone", phone);
                    jsonObject.put("email", email);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(RegisterActivity.this, "Lỗi xử lý dữ liệu", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Gửi yêu cầu POST đến API
                OkHttpClient client = new OkHttpClient();
                RequestBody body = RequestBody.create(
                        MediaType.parse("application/json; charset=utf-8"),
                        jsonObject.toString()
                );

                Request request = new Request.Builder()
                        .url("https://api.quandev03.id.vn/auth/register")
                        .post(body)
                        .build();

                client.newCall(request).enqueue(new okhttp3.Callback() {
                    @Override
                    public void onFailure(okhttp3.Call call, IOException e) {
                        e.printStackTrace();
                        runOnUiThread(() -> Toast.makeText(RegisterActivity.this, "Lỗi kết nối mạng: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                    }

                    @Override
                    public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                        String responseBody = response.body().string(); // Lưu phản hồi
                        if (response.isSuccessful()) {
                            runOnUiThread(() -> {
                                Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();

                                // Lưu thông tin vào SharedPreferences
                                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("username", usernameRegister.getText().toString().trim());
                                editor.putString("fullname", fullnameRegister.getText().toString().trim());
                                editor.putString("address", addressRegister.getText().toString().trim());
                                editor.putString("phone", phoneRegister.getText().toString().trim());
                                editor.putString("email", emailRegister.getText().toString().trim());
                                editor.putBoolean("isLoggedIn", true);  // Đánh dấu người dùng đã đăng nhập
                                editor.apply();  // Lưu thay đổi

                                // Chuyển đến màn hình đăng nhập
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                            });
                        } else {
                            runOnUiThread(() -> {
                                Toast.makeText(RegisterActivity.this, "Đăng ký thất bại: " + responseBody, Toast.LENGTH_SHORT).show();
                            });
                        }
                    }

                });
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

    //Hàm mã hóa mật khẩu
    public String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

}