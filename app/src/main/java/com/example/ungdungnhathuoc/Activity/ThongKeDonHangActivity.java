package com.example.ungdungnhathuoc.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ungdungnhathuoc.Adapter.OrderAdapter;
import com.example.ungdungnhathuoc.Model.Order;
import com.example.ungdungnhathuoc.R;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class ThongKeDonHangActivity extends BaseActivity {

    private RecyclerView recyclerViewOrders;
    private TextView tvTotalOrders, tvDeliveredOrders, tvCancelledOrders, tvUnconfirmedOrders, tvTongTienDaGiao,tvconfirmedOrders;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private List<Order> orders;  // Thêm biến lưu danh sách đơn hàng

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke_don_hang_nbactivity);
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
        tvconfirmedOrders = findViewById(R.id.tvconfirmedOrders);

        // Set up RecyclerView
        recyclerViewOrders.setLayoutManager(new LinearLayoutManager(this));

        // Lấy danh sách đơn hàng mẫu
        orders = getSampleOrders();

        // Kiểm tra nếu không có đơn hàng
        if (orders != null && !orders.isEmpty()) {
            OrderAdapter adapter = new OrderAdapter(orders, this);
            recyclerViewOrders.setAdapter(adapter);
            updateStatistics();  // Cập nhật thống kê
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
        orders.add(new Order(1, "Đã giao", 500.0, "2024-11-07", "Trần Thị B", "Hiệu thuốc Long Châu", "Hồ Chí Minh", "0987654321", "Sản phẩm C"));
        orders.add(new Order(2, "Chưa xác nhận", 750.0, "2024-11-06", "Lê Văn C", "Hiệu thuốc Long Châu", "Đà Nẵng", "0912345678", "Sản phẩm D"));
        orders.add(new Order(3, "Chưa xác nhận", 750.0, "2024-11-06", "Lê Văn C", "Hiệu thuốc Long Châu", "Đà Nẵng", "0912345678", "Sản phẩm D"));
        return orders;
    }

    public void updateStatistics() {
        // Cập nhật thống kê sau khi thay đổi trạng thái đơn hàng
        int totalOrders = 0;
        int deliveredOrders = 0;
        int cancelledOrders = 0;
        int unconfirmedOrders = 0;
        int    confirmedOrders = 0;
        double totalMoneyOrders = 0.0;

        // Lặp qua danh sách đơn hàng để tính lại thống kê
        for (Order order : orders) {
            totalOrders++;
            if ("Đã giao".equals(order.getStatus())) {
                deliveredOrders++;
                totalMoneyOrders += order.getTotalPrice();
            }
            if ("Đã xác nhận".equals(order.getStatus())) {
                confirmedOrders++;
            }
            if ("Đã hủy".equals(order.getStatus())) cancelledOrders++;
            if ("Chưa xác nhận".equals(order.getStatus())) unconfirmedOrders++;
        }

        // Cập nhật thông tin thống kê lên giao diện
        tvTotalOrders.setText("Tổng số đơn: " + totalOrders);
        tvDeliveredOrders.setText("Đơn đã giao: " + deliveredOrders);
        tvCancelledOrders.setText("Đơn đã hủy: " + cancelledOrders);
        tvUnconfirmedOrders.setText("Đơn chưa xác nhận: " + unconfirmedOrders);
        tvconfirmedOrders.setText("Đơn đã xác nhận: " + confirmedOrders);
        tvTongTienDaGiao.setText("Tổng tiền đơn hàng đã giao thành công: " + totalMoneyOrders + " Đồng");
    }

    // Thêm phương thức getOrders()
    public List<Order> getOrders() {
        return orders;
    }

    @Override
    protected void handleNavigation(int itemId) {
        super.handleNavigation(itemId);
    }
}
