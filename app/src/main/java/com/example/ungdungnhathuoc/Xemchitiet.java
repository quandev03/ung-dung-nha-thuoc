package com.example.ungdungnhathuoc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ungdungnhathuoc.Data.SQLiteConnect;
import com.example.ungdungnhathuoc.Model.Thuoc;


public class Xemchitiet extends AppCompatActivity {
    TextView tvten,tvcongdung,tvthanhphan,tvgia;
    ImageView  imgback,imgthuoc;
    Button btndathang;
    SQLiteConnect sqLiteConnect = new SQLiteConnect(this);
    SharedPreferences sharedPre;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xemchitiet);

        sharedPre =  getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        username = sharedPre.getString("accessToken", null);


        tvten=findViewById(R.id.tvten);
        tvcongdung=findViewById(R.id.tvcongdung);
        tvthanhphan=findViewById(R.id.tvthanhphan);
        tvgia=findViewById(R.id.tvgia);
        imgback=findViewById(R.id.imgback);
        imgthuoc=findViewById(R.id.imgthuoc);
        btndathang=findViewById(R.id.btndathang);
        Intent intent= getIntent();//dùng để truyền dữ liệu giữa các Activity hoặc giữa các thành phần khác trong ứng dụng Android.
        Bundle data = intent.getExtras();
        Thuoc thuoc =(Thuoc)data.get("thuoc_value");
        Log.d("DATA THUOC", thuoc.toString());
        tvten.setText("Tên:"+thuoc.getTenthuoc());
        tvthanhphan.setText("Loại: "+thuoc.getLoai());
        tvcongdung.setText("Công dụng:"+thuoc.getCongdung());
        tvgia.setText("Đơn giá:"+thuoc.getDongia());
        //hiển thị hình thuoc
        String hinhanh= thuoc.getHinhanh();
        Uri uri=Uri.parse(hinhanh);
        imgthuoc.setImageURI(uri);

        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // kết thúc màn hình
                finish();
            }
        });
        btndathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showOrderDialog(thuoc);
            }
        });
    }
    // Hàm hiển thị Dialog mua sản phẩm
    // Hàm hiển thị Dialog mua sản phẩm
    private void showOrderDialog(Thuoc thuoc) {
        // Tạo view cho dialog
        View dialogView = getLayoutInflater().inflate(R.layout.dialog, null);

        // Lấy các view trong dialog
        TextView txtsoluongsp = dialogView.findViewById(R.id.txtsoluongsp);
        TextView btnthem = dialogView.findViewById(R.id.btnthem);
        Button btnbot = dialogView.findViewById(R.id.btnbot);
        TextView txttongtien = dialogView.findViewById(R.id.txttongtien);
        Button btnmua = dialogView.findViewById(R.id.btnmua);

        int[] quantity = {0};// sử dụng mảng để chứa số lượng

        // Cập nhật số lượng ban đầu
        txtsoluongsp.setText("Số lượng:"+quantity[0]);
        updateTotalPrice(quantity[0], thuoc, txttongtien); // Gọi hàm để cập nhật tổng tiền

        // Thiết lập sự kiện cho nút cộng
        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity[0]++; // Tăng số lượng
                txtsoluongsp.setText("Số lượng:"+quantity[0]);
                updateTotalPrice(quantity[0], thuoc, txttongtien); // Cập nhật tổng tiền
            }
        });

        // Thiết lập sự kiện cho nút trừ
        btnbot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity[0] >= 1) { // Kiểm tra số lượng
                    quantity[0]--; // Giảm số lượng
                    txtsoluongsp.setText("Số lượng:"+quantity[0]);
                    updateTotalPrice(quantity[0], thuoc, txttongtien); // Cập nhật tổng tiền
                }
            }
        });

        // Tạo Dialog và hiển thị
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView)
                .setCancelable(true);

        AlertDialog dialog = builder.create();
        dialog.show();

        // Xử lý khi nhấn "Mua ngay"
        btnmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Logic mua sản phẩm ở đây
                Log.d("Data", "Quantity" + quantity[0]);
                boolean result = sqLiteConnect.createOrder(thuoc.getId(), username, thuoc.getId());
                Log.d("result create", "result:" + result);
                Toast.makeText(Xemchitiet.this, "Đặt hàng " + thuoc.getTenthuoc() + " thành công", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }

    // Hàm cập nhật tổng tiền
    private void updateTotalPrice(int quantity, Thuoc thuoc, TextView txttongtien) {
        // Cập nhật tổng tiền
        txttongtien.setText("Tổng tiền: " + (quantity * thuoc.getDongia()) + " VND");
    }

}