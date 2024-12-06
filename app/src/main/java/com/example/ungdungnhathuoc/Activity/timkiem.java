package com.example.ungdungnhathuoc.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ungdungnhathuoc.Adapter.Adaptertimkiem;
import com.example.ungdungnhathuoc.Data.SQLiteConnect;
import com.example.ungdungnhathuoc.Model.Thuoc;
import com.example.ungdungnhathuoc.R;

import java.util.ArrayList;

public class timkiem extends AppCompatActivity {
    ImageView app2;
    ListView lvtk;
    EditText search_bar;
    TextView tvkq;
    Adaptertimkiem adaptertimkiem;
    ArrayList<Thuoc> listthuoc1;
    SQLiteConnect sqLiteConnect;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timkiem);
        app2=findViewById(R.id.back);
        lvtk=findViewById(R.id.lvtk);
        search_bar=findViewById(R.id.search_bar);
        tvkq=findViewById(R.id.tvkq);
        sqLiteConnect=new SQLiteConnect(this);
        listthuoc1= sqLiteConnect.getAllThuoc();
        adaptertimkiem= new Adaptertimkiem(timkiem.this, R.layout.thuoc,listthuoc1);
        lvtk.setAdapter(adaptertimkiem);

        app2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(timkiem.this, HomeActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

                finish();
            }
        });
        search_bar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String query = charSequence.toString();
                Log.d("Text", "Text: " + query);
                adaptertimkiem.getFilter().filter(query);
                // Kiểm tra xem có kết quả phù hợp không
                boolean hasResults = false;
                for (int index = 0; index < adaptertimkiem.getCount(); index++) {
                    hasResults = true;
                    break;
                }

                if (query.isEmpty()) {
                    tvkq.setVisibility(View.GONE); // Ẩn thông báo khi không nhập từ khóa
                    Toast.makeText(timkiem.this, "Từ khóa tìm kiếm chưa được nhập...", Toast.LENGTH_SHORT).show();
                } else if (hasResults) {  // Nếu có kết quả
                    tvkq.setVisibility(View.GONE); // Ẩn thông báo khi có kết quả
                } else {
                    tvkq.setText("Không có kết quả phù hợp");
                    tvkq.setVisibility(View.VISIBLE); // Hiển thị thông báo không có kết quả

                    //tvkq.setVisibility(View.VISIBLE); // Hiển thị thông báo không có kết quả
                }
            }


            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}

