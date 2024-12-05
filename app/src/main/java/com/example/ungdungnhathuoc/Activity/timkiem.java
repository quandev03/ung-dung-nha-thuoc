package com.example.ungdungnhathuoc.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
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
import com.example.ungdungnhathuoc.Xemchitiet;

import java.util.ArrayList;

public class timkiem extends AppCompatActivity {
    ListView lvtk;
    EditText search_bar;
    TextView tvkq;
    Adaptertimkiem adaptertimkiem;
    ArrayList<Thuoc> listthuoc1;
    String dataSearch;
    SQLiteConnect sqLiteConnect;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timkiem);
        lvtk=findViewById(R.id.lvtk);
        search_bar=findViewById(R.id.search_bar);
       sqLiteConnect = new SQLiteConnect(this);
        tvkq=findViewById(R.id.tvkq);
        listthuoc1= sqLiteConnect.getAllThuoc();

        adaptertimkiem= new Adaptertimkiem(timkiem.this, R.layout.thuoc,listthuoc1);
        lvtk.setAdapter(adaptertimkiem);
        // thết lập suwj kiện tiìm kiếm

        search_bar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String query = charSequence.toString(); // Lấy từ khóa tìm kiếm
                adaptertimkiem.getFilter().filter(query);
                dataSearch = query;

                // Kiểm tra xem có kết quả phù hợp không
                boolean hasResults = false;
                for (int index = 0; index < adaptertimkiem.getCount(); index++) {
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

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        // thết laapj sự kiện khi nhấn vào app2 sẽ uqay trở về trang chủ
//        lvtk.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent xemchitietmh = new Intent(this, Xemchitiet.class);
//                Bundle data = new Bundle();
//                Thuoc thuoc=adaptertimkiem.getFilter().filter(dataSearch).getListhuoc().get(i);
//                data.putParcelable("thuoc_value", thuoc);
//                xemchitietmh.putExtras(data);
//                startActivity(xemchitietmh);
//
//                Toast.makeText(HomeActivity.this, listthuoc.get(i).getTenthuoc(), Toast.LENGTH_SHORT).show();
//            }
//        });

    }
}

