package com.example.ungdungnhathuoc.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ungdungnhathuoc.R;

public class ThongTinDonHangNBActivity extends AppCompatActivity {
    private TextView tvOrderId, tvOrderStatus, tvOrderPrice, tvOrderDate, tvCustomerName, tvContactInfo, tvAddress, tvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_order);

        // Khởi tạo các TextView
        tvOrderId = findViewById(R.id.orderId);
        tvOrderStatus = findViewById(R.id.status);
        tvOrderPrice = findViewById(R.id.totalPrice);
        tvOrderDate = findViewById(R.id.orderDate);
        tvCustomerName = findViewById(R.id.customerName);
        tvContactInfo = findViewById(R.id.contactInfo);
        tvAddress = findViewById(R.id.address);
        tvItems = findViewById(R.id.items);

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        String orderId = intent.getStringExtra("orderId");
        String status = intent.getStringExtra("order_status");
        double totalPrice = intent.getDoubleExtra("order_price", 0.0);
        String orderDate = intent.getStringExtra("order_date");
        String customerName = intent.getStringExtra("order_customer");
        String contactInfo = intent.getStringExtra("order_phone");
        String address = intent.getStringExtra("order_address");
        String items = intent.getStringExtra("order_items");

        // Hiển thị dữ liệu
        tvOrderId.setText("Mã đơn hàng: " + orderId);
        tvOrderStatus.setText("Trạng thái: " + status);
        tvOrderPrice.setText("Tổng tiền: " + totalPrice + " Đồng");
        tvOrderDate.setText("Ngày đặt: " + orderDate);
        tvCustomerName.setText("Tên khách hàng: " + customerName);
        tvContactInfo.setText("Liên hệ: " + contactInfo);
        tvAddress.setText("Địa chỉ: " + address);
        tvItems.setText("Sản phẩm: " + items);
    }
}
