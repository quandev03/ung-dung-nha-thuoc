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

import com.example.ungdungnhathuoc.Data.SQLiteConnect;
import com.example.ungdungnhathuoc.Model.Account;
import com.example.ungdungnhathuoc.Request.UpdateProfileInput;
import com.example.ungdungnhathuoc.Response.ResponseData;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import com.example.ungdungnhathuoc.Model.User;


public class EditProfile extends AppCompatActivity {
    Button btnSave, btnCancel;
    EditText edtUsername, edtFullname, edtEmail, edtPhone, edtAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_profile);
        edtUsername = findViewById(R.id.edit_username);
        edtFullname = findViewById(R.id.edit_fullname);
        edtEmail = findViewById(R.id.edit_email);
        edtPhone = findViewById(R.id.edit_phone);
        edtAddress = findViewById(R.id.edit_address);
        btnSave = findViewById(R.id.btn_save);
        btnCancel = findViewById(R.id.btn_cancel);



        // get accessToken
        SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.edit_profile_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SQLiteConnect sqLiteConnect = new SQLiteConnect(this);
        User user = sqLiteConnect.userDetail(sharedPref.getString("accessToken", null));
        edtFullname.setText(user.getFullname());
        edtEmail.setText(user.getEmail());
        edtPhone.setText(user.getPhone());
        edtAddress.setText(user.getAddress());
        edtUsername.setText(user.getUsername());


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

                User userUpdate = new User(user.getUsername(), user.getPassword(), fullname, address, email, phone);

                boolean result =sqLiteConnect.updateUser(userUpdate);
                if (result) {
                    Toast.makeText(EditProfile.this, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditProfile.this, Profile.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(EditProfile.this, "Cập nhật thông tin thất bại", Toast.LENGTH_SHORT).show();
                }



            }
        });

    };

}