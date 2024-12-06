package com.example.ungdungnhathuoc.Activity;

import static android.opengl.ETC1.decodeImage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ungdungnhathuoc.Adapter.DonHangAdapter;
import com.example.ungdungnhathuoc.Data.SQLiteConnect;
import com.example.ungdungnhathuoc.Model.DonHang;
import com.example.ungdungnhathuoc.Model.Order;
import com.example.ungdungnhathuoc.R;
import com.example.ungdungnhathuoc.Request.FilterOrder;

import java.io.File;
import java.util.ArrayList;

public class LichSuDonHangNguoiMuaActivity extends AppCompatActivity {
    ListView lvDonHang;
    ArrayList<DonHang> listDonHang;
    DonHangAdapter adapterDonHang;
    Button btnTatCa, btnChuaXacNhan, btnDaXacNhan;
    SQLiteConnect sqLiteConnect;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lich_su_don_hang_nguoi_mua);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        lvDonHang = findViewById(R.id.lvDonHang);
        btnTatCa = findViewById(R.id.btnTatCa);
        btnChuaXacNhan = findViewById(R.id.btnChuaXacNhan);
        btnDaXacNhan = findViewById(R.id.btnDaXacNhan);
        sqLiteConnect = new SQLiteConnect(this);
                // Lấy userId từ SharedPreferences
        SharedPreferences preferences = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String userId = preferences.getString("accessToken", "");  // Giả sử accessToken là userId

        // Lấy tất cả đơn hàng khi vào trang
        loadOrderData(userId, -1);




        btnTatCa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadOrderData(userId, -1);
            }
        });

        btnChuaXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadOrderData(userId, 0);
            }
        });

        btnDaXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadOrderData(userId, 1);
            }
        });
    }
    private void loadOrderData(String username, int status) {
        ArrayList<Order> ordersFromDb;
        if (status == -1) {
            ordersFromDb = sqLiteConnect.getOrderUser(username); // Lấy tất cả đơn hàng
        } else {
            ordersFromDb = sqLiteConnect.getOrderUser(username, status); // Lấy đơn hàng theo trạng thái
        }

        listDonHang = new ArrayList<>();
        for (Order order : ordersFromDb) {
            // Chuyển chuỗi hình ảnh thành Bitmap
            Bitmap image = decodeImage(order.getThuoc().getHinhanh());

            // Tạo đối tượng `DonHang` từ dữ liệu `Order`
            DonHang donHang = new DonHang(
                    order.getThuoc().getTenthuoc(), // Tên thuốc
                    order.getTongTien(),            // Tổng tiền
                    image,                          // Hình ảnh thuốc (Bitmap)
                    String.valueOf(order.getOrderId()) // Mã đơn hàng
            );
            listDonHang.add(donHang);
        }

        if (listDonHang.isEmpty()) {
            Toast.makeText(this, "Không có đơn hàng nào!", Toast.LENGTH_SHORT).show();
        } else {
            adapterDonHang = new DonHangAdapter(this, R.layout.lv_donhang, listDonHang);
            lvDonHang.setAdapter(adapterDonHang);
        }
    }

    private Bitmap decodeImage(String base64Image) {
        try {
            byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        } catch (Exception e) {
            e.printStackTrace();
            return BitmapFactory.decodeResource(getResources(), R.drawable.giaikhat); // Ảnh mặc định nếu lỗi
        }
    }




}