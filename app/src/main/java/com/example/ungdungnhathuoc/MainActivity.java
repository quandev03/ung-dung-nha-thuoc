package com.example.ungdungnhathuoc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Button btnProfile, addProduce;
    EditText tvAccessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the button after setting the content view
        btnProfile = findViewById(R.id.openProfile);
        tvAccessToken = findViewById(R.id.accessToken);
        addProduce = findViewById(R.id.addProduce);


        // Enable edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Set up button click listener to open Profile activity
        btnProfile.setOnClickListener(view -> {
            String accessTokenString = tvAccessToken.getText().toString().trim();
            SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("accessToken", accessTokenString);
            editor.apply();
            Intent intent = new Intent(MainActivity.this, Profile.class);
            startActivity(intent);
        });
        addProduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String accessTokenString = tvAccessToken.getText().toString().trim();
                SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("accessToken", accessTokenString);
                editor.apply();
                Intent intent = new Intent(MainActivity.this, AddProduce.class);
                startActivity(intent);
            }
        });
    }
}