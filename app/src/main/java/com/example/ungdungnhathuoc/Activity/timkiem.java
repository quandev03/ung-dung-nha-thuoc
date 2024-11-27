package com.example.ungdungnhathuoc.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ungdungnhathuoc.Adapter.AdapterTimKiem;
import com.example.ungdungnhathuoc.Model.Thuoc;
import com.example.ungdungnhathuoc.R;

import java.util.ArrayList;

public class timkiem extends AppCompatActivity {
    ImageView app2;
    ListView lvtk;
    Button search_button;
    EditText search_bar;
    TextView tvkq;
    AdapterTimKiem adaptertimkiem;
    ArrayList<Thuoc> listthuoc1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timkiem);
        app2=findViewById(R.id.back);
        lvtk=findViewById(R.id.lvtk);
        search_button=findViewById(R.id.search_button);
        search_bar=findViewById(R.id.search_bar);
        tvkq=findViewById(R.id.tvkq);
        listthuoc1= new ArrayList<>();
        String duongdananh="android.resource://"+R.class.getPackage().getName()+"/";
        //chạy thử dữ liệu
        listthuoc1.add(new Thuoc("Vitamin E","Thuốc bổ",duongdananh+R.drawable.thuoc2,30,20,250000));
        listthuoc1.add(new Thuoc("Vitamin tổng hợp","Thuốc bổ",duongdananh+R.drawable.thuoc1,50,45,500000));
        listthuoc1.add(new Thuoc("Vitamin C","Thuốc bổ",duongdananh+R.drawable.thuoc3,50,45,250000));
        adaptertimkiem= new AdapterTimKiem(timkiem.this, R.layout.thuoc,listthuoc1);
        lvtk.setAdapter(adaptertimkiem);
        // thết lập suwj kiện tiìm kiếm
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query = search_bar.getText().toString().trim(); // Lấy từ khóa tìm kiếm
                adaptertimkiem.getFilter().filter(query);

                // Kiểm tra xem có kết quả phù hợp không
                boolean hasResults = false;
                for (int i = 0; i < adaptertimkiem.getCount(); i++) {
                    hasResults = true;
                    break;
                }

                if (query.isEmpty()) {
                    tvkq.setVisibility(View.GONE); // Ẩn thông báo khi không nhập từ khóa
                    Toast.makeText(timkiem.this, "Từ khóa tìm kiếm chưa được nhập...", Toast.LENGTH_SHORT).show();
                } else if (hasResults) {  // Nếu không có kết quả
                    tvkq.setVisibility(View.GONE); // Ẩn thông báo khi có kết quả
                } else {
                    tvkq.setVisibility(View.VISIBLE); // Hiển thị thông báo không có kết quả
                    tvkq.setText("Không có kết quả phù hợp");
                    //tvkq.setVisibility(View.VISIBLE); // Hiển thị thông báo không có kết quả
                }

            }
        });
        // thết laapj sự kiện khi nhấn vào app2 sẽ uqay trở về trang chủ
        app2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(timkiem.this, HomeActivity.class);
                startActivity(intent);
                // Hiệu ứng chuyển đổi khi quay lại
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

                // Kết thúc Activity hiện tại
                finish();
            }
        });
    }
}

