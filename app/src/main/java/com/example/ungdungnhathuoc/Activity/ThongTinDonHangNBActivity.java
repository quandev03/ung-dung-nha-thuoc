package com.example.ungdungnhathuoc.Activity;

import static com.example.ungdungnhathuoc.R.id.imgSanPham;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;
import com.example.ungdungnhathuoc.R;
import com.google.android.material.navigation.NavigationView;

public class ThongTinDonHangNBActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    private TextView tvOrderId, tvStatus, tvCustomerName, tvProductName, tvPhone, tvAddress, tvTotalPrice, tvOrderDate;
    private ImageView imgSanPham;

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
//        imgSanPham = findViewById(R.id.imgSanPham); // Initialize ImageView for product image

        // Set up the toolbar and navigation drawer
        setSupportActionBar(toolbar);

        // Fetch data from Intent
        if (getIntent() != null) {
            String orderId = getIntent().getStringExtra("order_id");
            String status = getIntent().getStringExtra("order_status");
            String customerName = getIntent().getStringExtra("order_customer");
            String productName = getIntent().getStringExtra("order_items");
            String phone = getIntent().getStringExtra("order_phone");
            String address = getIntent().getStringExtra("order_address");
            double totalPrice = getIntent().getDoubleExtra("order_price", 0.0);
            String orderDate = getIntent().getStringExtra("order_date");

            // Get the product image resource ID from the Intent
//            int order_ImgSanPham = getIntent().getIntExtra("order_ImgSanPham", -1);  // Retrieve the image resource ID

            // Set the image resource, using a default image if no image is passed
//            if (order_ImgSanPham != -1) {
//                imgSanPham.setImageResource(order_ImgSanPham);
//            } else {
//                imgSanPham.setImageResource(R.drawable.anh_nha_thuov); // Default image if no image is passed
//            }

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
