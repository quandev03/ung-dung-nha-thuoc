package com.example.ungdungnhathuoc.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ungdungnhathuoc.Adapter.OrderAdapter;
import com.example.ungdungnhathuoc.Model.Order;
import com.example.ungdungnhathuoc.R;
import com.google.android.material.navigation.NavigationView;
import androidx.appcompat.app.ActionBarDrawerToggle;

import java.util.ArrayList;
import java.util.List;
public class ThongKeDonHangActivity extends BaseActivity {

    private RecyclerView recyclerViewOrders;
    private TextView tvTotalOrders, tvDeliveredOrders, tvCancelledOrders, tvUnconfirmedOrders, tvTongTienDaGiao;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke_don_hang_nbactivity);

        // Khởi tạo DrawerLayout và NavigationView
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

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

        // Khởi tạo các View
        recyclerViewOrders = findViewById(R.id.recyclerViewOrders);
        tvTotalOrders = findViewById(R.id.tvTotalOrders);
        tvDeliveredOrders = findViewById(R.id.tvDeliveredOrders);
        tvCancelledOrders = findViewById(R.id.tvCancelledOrders);
        tvUnconfirmedOrders = findViewById(R.id.tvUnconfirmedOrders);
        tvTongTienDaGiao = findViewById(R.id.tvTongTienDaGiao);

        // Set up RecyclerView
        recyclerViewOrders.setLayoutManager(new LinearLayoutManager(this));

        // Lấy danh sách đơn hàng mẫu
        List<Order> orders = getSampleOrders();

        // Kiểm tra nếu không có đơn hàng
        if (orders != null && !orders.isEmpty()) {
            OrderAdapter adapter = new OrderAdapter(orders, this);
            recyclerViewOrders.setAdapter(adapter);
            updateStatistics(orders);  // Cập nhật thống kê
        } else {
            // Nếu không có đơn hàng
            tvTotalOrders.setText("Không có đơn hàng");
            tvDeliveredOrders.setText("Không có đơn hàng");
            tvCancelledOrders.setText("Không có đơn hàng");
            tvUnconfirmedOrders.setText("Không có đơn hàng");
            tvTongTienDaGiao.setText("0 Đồng");
        }
    }

    private List<Order> getSampleOrders() {
        // Dữ liệu mẫu cho việc thử nghiệm
        List<Order> orders = new ArrayList<>();
        orders.add(new Order("002", "Đã giao", 500.0, "2024-11-07", "Trần Thị B", "Hiệu thuốc Long Châu", "Hồ Chí Minh", "0987654321", "Sản phẩm C"));
        orders.add(new Order("003", "Chưa xác nhận", 750.0, "2024-11-06", "Lê Văn C", "Hiệu thuốc Long Châu", "Đà Nẵng", "0912345678", "Sản phẩm D"));
        orders.add(new Order("004", "Chưa xác nhận", 750.0, "2024-11-06", "Lê Văn C", "Hiệu thuốc Long Châu", "Đà Nẵng", "0912345678", "Sản phẩm D"));
        return orders;
    }

    private void updateStatistics(List<Order> orders) {
        int totalOrders = orders.size();
        int deliveredOrders = 0;
        int cancelledOrders = 0;
        int unconfirmedOrders = 0;
        double totalMoneyOrders = 0.0;

        // Tính toán thống kê đơn hàng
        for (Order order : orders) {
            if ("Đã giao".equals(order.getStatus())) {
                deliveredOrders++;
                totalMoneyOrders += order.getTotalPrice();
            }
            if ("Đã hủy".equals(order.getStatus())) cancelledOrders++;
            if ("Chưa xác nhận".equals(order.getStatus())) unconfirmedOrders++;
        }

        // Cập nhật thống kê trên màn hình
        tvTotalOrders.setText("Tổng số đơn: " + totalOrders);
        tvDeliveredOrders.setText("Đơn đã giao: " + deliveredOrders);
        tvCancelledOrders.setText("Đơn đã hủy: " + cancelledOrders);
        tvUnconfirmedOrders.setText("Đơn chưa xác nhận: " + unconfirmedOrders);
        tvTongTienDaGiao.setText("Tổng tiền đơn hàng đã giao thành công: " + totalMoneyOrders + " Đồng");
    }

    @Override
    protected void handleNavigation(int itemId) {
        super.handleNavigation(itemId);
    }
}
