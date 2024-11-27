package com.example.ungdungnhathuoc.Activity;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;

import com.example.ungdungnhathuoc.R;
import com.google.android.material.navigation.NavigationView;

public class ThongTinDonHangNBActivity extends BaseActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    private TextView tvOrderId, tvStatus, tvCustomerName, tvProductName, tvPhone, tvAddress, tvTotalPrice, tvOrderDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_don_hang_nbactivity);

        // Initialize the views
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.nav_view);

        tvOrderId = findViewById(R.id.tvOrderId);
        tvStatus = findViewById(R.id.tvStatus);
        tvCustomerName = findViewById(R.id.tvCustomerName);
        tvProductName = findViewById(R.id.tvProductName);
        tvPhone = findViewById(R.id.tvPhone);
        tvAddress = findViewById(R.id.tvAddress);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        tvOrderDate = findViewById(R.id.tvOrderDate);

        // Set up the toolbar and navigation drawer
        setSupportActionBar(toolbar);

        // Fetch data from Intent
        if (getIntent() != null) {
            String orderId = getIntent().getStringExtra("orderId");
            String status = getIntent().getStringExtra("order_status");
            String customerName = getIntent().getStringExtra("order_customer");
            String productName = getIntent().getStringExtra("order_items");
            String phone = getIntent().getStringExtra("order_phone");
            String address = getIntent().getStringExtra("order_address");
            double totalPrice = getIntent().getDoubleExtra("order_price", 0.0);
            String orderDate = getIntent().getStringExtra("order_date");

            // Set values to TextViews
            tvOrderId.setText("Mã đơn hàng: " + orderId);
            tvStatus.setText("Trạng thái: " + status);
            tvCustomerName.setText("Tên khách hàng: " + customerName);
            tvProductName.setText("Sản phẩm: " + productName);
            tvPhone.setText("Số điện thoại: " + phone);
            tvAddress.setText("Địa chỉ: " + address);
            tvTotalPrice.setText("Tổng tiền: " + totalPrice + " Đồng");
            tvOrderDate.setText("Ngày đặt: " + orderDate);
        }
    }
}
