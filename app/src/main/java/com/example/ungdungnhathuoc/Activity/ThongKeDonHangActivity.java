package com.example.ungdungnhathuoc.Activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;

import com.example.ungdungnhathuoc.Adapter.OrderAdapter;
import com.example.ungdungnhathuoc.Models.Order;
import com.example.ungdungnhathuoc.R;

import java.util.ArrayList;
import java.util.List;

public class ThongKeDonHangActivity extends AppCompatActivity {
    private RecyclerView recyclerViewOrders;
    private TextView tvTotalOrders, tvDeliveredOrders, tvCancelledOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke_don_hang_nbactivity);

        recyclerViewOrders = findViewById(R.id.recyclerViewOrders);
        tvTotalOrders = findViewById(R.id.tvTotalOrders);
        tvDeliveredOrders = findViewById(R.id.tvDeliveredOrders);
        tvCancelledOrders = findViewById(R.id.tvCancelledOrders);

        // Thiết lập RecyclerView
        recyclerViewOrders.setLayoutManager(new LinearLayoutManager(this));
        List<Order> orders = getSampleOrders();
        OrderAdapter adapter = new OrderAdapter(orders);
        recyclerViewOrders.setAdapter(adapter);

        // Cập nhật thống kê
        updateStatistics(orders);
    }

    private List<Order> getSampleOrders() {
        // Tạo danh sách đơn hàng mẫu
        List<Order> orders = new ArrayList<>();
        orders.add(new Order("001", "Đã giao", "1,000,000", "2024-11-08", "Nguyễn Văn A"));
        orders.add(new Order("002", "Đã hủy", "500,000", "2024-11-07", "Trần Thị B"));
        orders.add(new Order("003", "Chưa xác nhận", "750,000", "2024-11-06", "Lê Văn C"));
        return orders;
    }

    private void updateStatistics(List<Order> orders) {
        int totalOrders = orders.size();
        int deliveredOrders = 0;
        int cancelledOrders = 0;

        for (Order order : orders) {
            if ("Đã giao".equals(order.getStatus())) deliveredOrders++;
            if ("Đã hủy".equals(order.getStatus())) cancelledOrders++;
        }

        tvTotalOrders.setText("Tổng số đơn: " + totalOrders);
        tvDeliveredOrders.setText("Đơn đã giao: " + deliveredOrders);
        tvCancelledOrders.setText("Đơn đã hủy: " + cancelledOrders);
    }
}
