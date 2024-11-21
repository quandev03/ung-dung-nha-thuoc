package com.example.ungdungnhathuoc.Activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ungdungnhathuoc.Adapter.OrderAdapter;
import com.example.ungdungnhathuoc.Models.Order;
import com.example.ungdungnhathuoc.R;
import com.google.android.material.navigation.NavigationView;
import androidx.appcompat.app.ActionBarDrawerToggle;
import java.util.ArrayList;
import java.util.List;

public class ThongKeDonHangActivity extends AppCompatActivity {

    private RecyclerView recyclerViewOrders;
    private TextView tvTotalOrders, tvDeliveredOrders, tvCancelledOrders, tvUnconfirmedOrders, tvTongTienDaGiao;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke_don_hang_nbactivity); // Ensure the layout file name is correct

        // Set up Toolbar and DrawerLayout
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        // ActionBarDrawerToggle for the drawer
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Enable back button on the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerViewOrders = findViewById(R.id.recyclerViewOrders);
        tvTotalOrders = findViewById(R.id.tvTotalOrders);
        tvDeliveredOrders = findViewById(R.id.tvDeliveredOrders);
        tvCancelledOrders = findViewById(R.id.tvCancelledOrders);
        tvUnconfirmedOrders = findViewById(R.id.tvUnconfirmedOrders);
        tvTongTienDaGiao = findViewById(R.id.tvTongTienDaGiao);

        // Set up RecyclerView
        recyclerViewOrders.setLayoutManager(new LinearLayoutManager(this));
        List<Order> orders = getSampleOrders();

        // Check if orders are empty
        if (orders != null && !orders.isEmpty()) {
            OrderAdapter adapter = new OrderAdapter(orders, this); // Pass context correctly to adapter
            recyclerViewOrders.setAdapter(adapter);
            // Update statistics
            updateStatistics(orders);
        } else {
            // Display message if no orders
            tvTotalOrders.setText("Không có đơn hàng");
            tvDeliveredOrders.setText("Không có đơn hàng");
            tvCancelledOrders.setText("Không có đơn hàng");
            tvUnconfirmedOrders.setText("Không có đơn hàng");
            tvTongTienDaGiao.setText("0 Đồng");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(navigationView);  // Open the navigation drawer
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private List<Order> getSampleOrders() {
        // Sample data for testing
        List<Order> orders = new ArrayList<>();
        orders.add(new Order("002", "Đã giao", 500.0, "2024-11-07", "Trần Thị B", "Hiệu thuốc Long Châu", "Hồ Chí Minh", "0987654321", "Sản phẩm C"));
        orders.add(new Order("003", "Chưa xác nhận", 750.0, "2024-11-06", "Lê Văn C", "Hiệu thuốc Long Châu", "Đà Nẵng", "0912345678", "Sản phẩm D"));
        orders.add(new Order("003", "Chưa xác nhận", 750.0, "2024-11-06", "Lê Văn C", "Hiệu thuốc Long Châu", "Đà Nẵng", "0912345678", "Sản phẩm D"));

        return orders;
    }

    private void updateStatistics(List<Order> orders) {
        int totalOrders = orders.size();
        int deliveredOrders = 0;
        int cancelledOrders = 0;
        int unconfirmedOrders = 0;
        double totalMoneyOrders = 0.0;

        // Calculate order statistics
        for (Order order : orders) {
            if ("Đã giao".equals(order.getStatus())) {
                deliveredOrders++;
                totalMoneyOrders += order.getTotalPrice();
            }
            if ("Đã hủy".equals(order.getStatus())) cancelledOrders++;
            if ("Chưa xác nhận".equals(order.getStatus())) unconfirmedOrders++;
        }

        // Update statistics on the screen
        tvTotalOrders.setText("Tổng số đơn: " + totalOrders);
        tvDeliveredOrders.setText("Đơn đã giao: " + deliveredOrders);
        tvCancelledOrders.setText("Đơn đã hủy: " + cancelledOrders);
        tvUnconfirmedOrders.setText("Đơn chưa xác nhận: " + unconfirmedOrders);
        tvTongTienDaGiao.setText("Tổng tiền đơn hàng đã giao thành công: " + totalMoneyOrders + " Đồng");
    }
}
