package com.example.ungdungnhathuoc.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.ungdungnhathuoc.Data.SQLiteConnect;
import com.example.ungdungnhathuoc.Model.Order;
import com.example.ungdungnhathuoc.Model.Thuoc;
import com.example.ungdungnhathuoc.Model.User;
import com.example.ungdungnhathuoc.R;

public class ThongTinDonHangNBActivity extends AppCompatActivity {

        private TextView tvOrderId, tvStatus, tvCustomerName, tvProductName, tvPhone, tvAddress, tvTotalPrice, tvOrderDate;
        private ImageView imgSanPham;
        SQLiteConnect sqLiteConnect;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_thong_tin_don_hang_nbactivity);

            // Ánh xạ các view
            tvOrderId = findViewById(R.id.tvOrderId);
            tvStatus = findViewById(R.id.tvStatus);
            tvCustomerName = findViewById(R.id.tvCustomerName);
            tvProductName = findViewById(R.id.tvProductName);
            tvPhone = findViewById(R.id.tvPhone);
            tvAddress = findViewById(R.id.tvAddress);
            tvTotalPrice = findViewById(R.id.tvTotalPrice);
            tvOrderDate = findViewById(R.id.tvOrderDate);
            imgSanPham = findViewById(R.id.imgSanPham);

            // Lấy orderId từ Intent
            Intent intent = getIntent();
            int orderId = intent.getIntExtra("order_id", -1); // Lấy orderId từ Intent, mặc định là -1 nếu không có giá trị

            if (orderId != -1) {
                // Kết nối đến cơ sở dữ liệu và lấy chi tiết đơn hàng
                sqLiteConnect = new SQLiteConnect(this); // Khởi tạo SQLiteConnect nếu chưa có
                Order order = sqLiteConnect.getOrderDetailsById(orderId);

                if (order != null) {
                    // Cập nhật các TextView với dữ liệu từ đơn hàng
                    tvOrderId.setText("Mã đơn hàng: " + order.getOrderId());
                    tvStatus.setText("Trạng thái: " + order.getStatus());
                    tvCustomerName.setText("Tên khách hàng: " + order.getUser().getUsername());
                    tvProductName.setText("Sản phẩm: " + order.getThuoc().getTenthuoc());
                    tvPhone.setText("Số điện thoại: " + order.getUser().getPhone());
                    tvAddress.setText("Địa chỉ: " + order.getUser().getAddress());
                    tvTotalPrice.setText("Tổng tiền: " + order.getTongTien() + " Đồng");
                    tvOrderDate.setText("Ngày đặt: " + order.getOrderDate());

                    // Cập nhật ảnh sản phẩm (nếu có)
                    String imageUrl = order.getThuoc().getHinhanh();
                    Glide.with(this).load(imageUrl).into(imgSanPham);
                } else {
                    Toast.makeText(this, "Không tìm thấy đơn hàng", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Mã đơn hàng không hợp lệ", Toast.LENGTH_SHORT).show();
            }
        }

}

