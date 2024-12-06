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
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.ungdungnhathuoc.Adapter.DonHangAdapter;
import com.example.ungdungnhathuoc.Data.SQLiteConnect;
import com.example.ungdungnhathuoc.Model.DonHang;
import com.example.ungdungnhathuoc.Model.Order;
import com.example.ungdungnhathuoc.R;
import com.example.ungdungnhathuoc.Request.FilterOrder;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.util.ArrayList;

public class LichSuDonHangNguoiMuaActivity extends BaseActivity {
    ListView lvDonHang;
    ArrayList<DonHang> listDonHang;
    DonHangAdapter adapterDonHang;
    Button btnTatCa, btnChuaXacNhan, btnDaXacNhan, btnHoanThanh;
    SQLiteConnect sqLiteConnect;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lich_su_don_hang_nguoi_mua);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawer_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        lvDonHang = findViewById(R.id.lvDonHang);
        btnTatCa = findViewById(R.id.btnTatCa);
        btnChuaXacNhan = findViewById(R.id.btnChuaXacNhan);
        btnDaXacNhan = findViewById(R.id.btnDaXacNhan);
        btnHoanThanh = findViewById(R.id.btnHoanThanh);
        sqLiteConnect = new SQLiteConnect(this);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);

        // Set up Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set up DrawerToggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Xử lý sự kiện chọn item từ NavigationView
        navigationView.setNavigationItemSelectedListener(item -> {
            handleNavigation(item.getItemId());
            drawerLayout.closeDrawers();
            return true;
        });
                // Lấy userId từ SharedPreferences
        SharedPreferences preferences = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String userId = preferences.getString("accessToken", "");  // Giả sử accessToken là userId

        // Lấy tất cả đơn hàng khi vào trang
        loadOrderData(userId, -1);




        btnTatCa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadOrderData(userId, -1);
                btnTatCa.setBackgroundColor(getResources().getColor(R.color.red));
                btnChuaXacNhan.setBackgroundColor(getResources().getColor(R.color.blue2));
                btnDaXacNhan.setBackgroundColor(getResources().getColor(R.color.blue2));
                btnHoanThanh.setBackgroundColor(getResources().getColor(R.color.blue2));
            }
        });

        btnChuaXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadOrderData(userId, 0);
                btnChuaXacNhan.setBackgroundColor(getResources().getColor(R.color.red));
                btnTatCa.setBackgroundColor(getResources().getColor(R.color.blue2));
                btnDaXacNhan.setBackgroundColor(getResources().getColor(R.color.blue2));
                btnHoanThanh.setBackgroundColor(getResources().getColor(R.color.blue2));
            }
        });

        btnDaXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadOrderData(userId, 1);
                btnDaXacNhan.setBackgroundColor(getResources().getColor(R.color.red));
                btnChuaXacNhan.setBackgroundColor(getResources().getColor(R.color.blue2));
                btnTatCa.setBackgroundColor(getResources().getColor(R.color.blue2));
                btnHoanThanh.setBackgroundColor(getResources().getColor(R.color.blue2));
            }
        });

        btnHoanThanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadOrderData(userId, 3);
                btnHoanThanh.setBackgroundColor(getResources().getColor(R.color.red));
                btnChuaXacNhan.setBackgroundColor(getResources().getColor(R.color.blue2));
                btnDaXacNhan.setBackgroundColor(getResources().getColor(R.color.blue2));
                btnTatCa.setBackgroundColor(getResources().getColor(R.color.blue2));
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
//            Bitmap image = decodeImage();

            // Tạo đối tượng `DonHang` từ dữ liệu `Order`
            DonHang donHang = new DonHang(
                    order.getThuoc().getTenthuoc(), // Tên thuốc
                    order.getTongTien(),            // Tổng tiền
                    order.getThuoc().getHinhanh(),                          // Hình ảnh thuốc (Bitmap)
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

    protected void handleNavigation(int itemId) {
        super.handleNavigatioUser(itemId);
    }
}




