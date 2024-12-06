package com.example.ungdungnhathuoc.Activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import com.example.ungdungnhathuoc.Data.SQLiteConnect;
import com.example.ungdungnhathuoc.Model.Order;
import com.example.ungdungnhathuoc.R;

import java.util.Objects;

public class ThongTinDonHangNMActivity extends AppCompatActivity {
    TextView tvOrderId,tvProductName,tvTenNguoiBan,tvOrderDate,tvTotalPrice,tvStatus,tvDiaChiNB;
    Button btnHuy, backToList;
//    ImageView imgSanPham;
    SQLiteConnect sqLiteConnect;
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_thong_tin_don_hang_nmactivity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        tvOrderId = findViewById(R.id.tvOrderId);
        tvProductName = findViewById(R.id.tvProductName);
        tvTenNguoiBan = findViewById(R.id.tvTenNguoiBan);
        tvOrderDate = findViewById(R.id.tvOrderDate);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        tvStatus = findViewById(R.id.tvStatus);
        tvDiaChiNB = findViewById(R.id.tvDiaChiNB);
        btnHuy = findViewById(R.id.btnHuy);
        backToList = findViewById(R.id.backToList);

        // Lấy orderId từ Intent
        Intent intent = getIntent();
         // Lấy orderId từ Intent, mặc định là -1 nếu không có giá trị
        String orderIdS = intent.getStringExtra("order_id"); // Lấy orderId từ Intent, mặc định là -1 nếu không có giá trị
        int orderId = -1;
        if (orderIdS != null) {
            orderId = Integer.parseInt(orderIdS);
        };
        Log.d("ID", "Id là: " + orderId);
        if (orderId != -1) {
            // Kết nối đến cơ sở dữ liệu và lấy chi tiết đơn hàng
            // Khởi tạo SQLiteConnect nếu chưa có
            sqLiteConnect = new SQLiteConnect(this);
            Order order = sqLiteConnect.getOrderDetailsById(orderId);
            if (order != null) {
                // Cập nhật các TextView với dữ liệu từ đơn hàng
                tvOrderId.setText("Mã đơn hàng: " + order.getOrderId());
                tvStatus.setText("Trạng thái: " + order.getStatus());
                tvTenNguoiBan.setText("Tên người bán: " + order.getUser().getUsername());
                tvProductName.setText("Sản phẩm: " + order.getThuoc().getTenthuoc());
                tvDiaChiNB.setText("Địa chỉ: " + order.getUser().getAddress());
                tvTotalPrice.setText("Tổng tiền: " + order.getTongTien() + " Đồng");
                tvOrderDate.setText("Ngày đặt: " + order.getOrderDate());
                if(Objects.equals(order.getStatus(), "Đã xác nhận")) {
                    btnHuy.setText("Đã nhận được đơn hàng");
                    String messenger = "Bạn có chắc chắn đã nhận được đơn hàng này không?";
                    btnHuy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showConfirmationDialog(messenger, "complete", order);
                        }
                    });


                }else if (Objects.equals(order.getStatus(), "Đang xử lý")) {
                    btnHuy.setText("Huỷ đơn hàng");
                    btnHuy.setBackgroundColor(getResources().getColor(R.color.red));
                    String messenger = "Bạn có chắc chắn muốn huỷ đơn hàng này không?";
                    btnHuy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showConfirmationDialog(messenger, "cancel", order);
                        }
                    });
                }else if (Objects.equals(order.getStatus(), "Đã hủy")) {
                    btnHuy.setText("Đã huỷ đơn hàng");
                    btnHuy.setBackgroundColor(getResources().getColor(R.color.red));
                    btnHuy.setEnabled(false);
                } else {
                    btnHuy.setText("Đơn hàng đã hoàn thành");
                    btnHuy.setEnabled(false);
                }
                btnHuy.setTextColor(getResources().getColor(R.color.white));


                // Cập nhật ảnh sản phẩm (nếu có)
                Uri uri = Uri.parse(order.getThuoc().getHinhanh());
                Log.d("uri", "uri:" + uri);
//                imgSanPham.setImageURI(uri);
            } else {
                Intent intent1 = new Intent(ThongTinDonHangNMActivity.this, LichSuDonHangNguoiMuaActivity.class);
                startActivity(intent1);
                Toast.makeText(this, "Không tìm thấy đơn hàng", Toast.LENGTH_SHORT).show();
            }
        } else {
            Intent intent1 = new Intent(ThongTinDonHangNMActivity.this, LichSuDonHangNguoiMuaActivity.class);
            startActivity(intent1);
            Toast.makeText(this, "Mã đơn hàng không hợp lệ", Toast.LENGTH_SHORT).show();

        }

        backToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(ThongTinDonHangNMActivity.this, LichSuDonHangNguoiMuaActivity.class);
                startActivity(intent1);
            }
        });

    }
    private void showConfirmationDialog(String message, String option, Order order) {
        // Create the confirmation dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setCancelable(false) // Prevent closing dialog by touching outside
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // Handle the "Yes" button click
                        // You can put your action here (e.g., completing the order)
                        switch (option) {
                            case "cancel":
                                handleCancel(order);
                                break;
                            case "complete":
                                handleComplete(order);
                                break;
                            default:
                                Toast.makeText(ThongTinDonHangNMActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // Handle the "No" button click
                        // For example, dismissing the dialog
                        dialog.dismiss();
                    }
                });

        // Create and show the dialog
        AlertDialog alert = builder.create();
        alert.show();
    }
    private void handleCancel (Order order){
        sqLiteConnect.cancelOrder(order.getOrderId());
        Toast.makeText(ThongTinDonHangNMActivity.this, "Đơn hàng đã bị huỷ", Toast.LENGTH_SHORT).show();
        Intent intent = getIntent();
        finish(); // Kết thúc Activity hiện tại
        startActivity(intent);
    }
    private void handleComplete(Order order){
        sqLiteConnect.complateOrder(order.getOrderId());
        Toast.makeText(ThongTinDonHangNMActivity.this, "Bạn đã nhận được đơn hàng", Toast.LENGTH_SHORT).show();
        Intent intent = getIntent();
        finish(); // Kết thúc Activity hiện tại
        startActivity(intent);
    }

}