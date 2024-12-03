package com.example.ungdungnhathuoc.Activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ungdungnhathuoc.Adapter.OrderAdapter;
import com.example.ungdungnhathuoc.Data.SQLiteConnect;
import com.example.ungdungnhathuoc.Model.Order;
import com.example.ungdungnhathuoc.R;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class ThongKeDonHangActivity extends BaseActivity {

    private TextView tvTotalOrders, tvDeliveredOrders, tvCancelledOrders, tvUnconfirmedOrders, tvConfirmedOrders, tvTotalAmountDelivered;
    private RecyclerView recyclerViewOrders;
    private OrderAdapter adapter;
    private List<Order> orderList;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private SQLiteConnect sqLiteConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke_don_hang_nbactivity);

        initializeUI();
        sqLiteConnect = new SQLiteConnect(this);

        // Load dữ liệu thống kê và danh sách đơn hàng
        loadStatistics();
        loadOrders();
    }

    private void initializeUI() {
        // Khởi tạo giao diện điều hướng
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(item -> {
            handleNavigation(item.getItemId());
            drawerLayout.closeDrawers();
            return true;
        });

        // Khởi tạo TextViews
        tvTotalOrders = findViewById(R.id.tvTotalOrders);
        tvDeliveredOrders = findViewById(R.id.tvDeliveredOrders);
        tvCancelledOrders = findViewById(R.id.tvCancelledOrders);
        tvUnconfirmedOrders = findViewById(R.id.tvUnconfirmedOrders);
        tvConfirmedOrders = findViewById(R.id.tvConfirmedOrders);
        tvTotalAmountDelivered = findViewById(R.id.tvTotalAmountDelivered);

        // Khởi tạo RecyclerView
        recyclerViewOrders = findViewById(R.id.recyclerViewOrders);
        recyclerViewOrders.setLayoutManager(new LinearLayoutManager(this));
    }

    public void loadStatistics() {
        SQLiteDatabase db = sqLiteConnect.getReadableDatabase();

        // Đặt dữ liệu thống kê vào TextViews
        setTextForTextView(db, "SELECT COUNT(*) FROM orderProduce", tvTotalOrders, "Tổng số đơn hàng: ");
        setTextForTextView(db, "SELECT COUNT(*) FROM orderProduce WHERE status = 3", tvDeliveredOrders, "Đơn đã giao: ");
        setTextForTextView(db, "SELECT COUNT(*) FROM orderProduce WHERE status = 1", tvConfirmedOrders, "Đơn đã xác nhận: ");
        setTextForTextView(db, "SELECT COUNT(*) FROM orderProduce WHERE status = 2", tvCancelledOrders, "Đơn đã hủy: ");
        setTextForTextView(db, "SELECT COUNT(*) FROM orderProduce WHERE status = 0", tvUnconfirmedOrders, "Đơn chờ xác nhận: ");
        setAmountForTextView(db, "SELECT SUM(total) FROM orderProduce WHERE status = 1", tvTotalAmountDelivered, "Tổng tiền đã giao: ");
    }

    private void setTextForTextView(SQLiteDatabase db, String query, TextView textView, String label) {
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                int result = cursor.getInt(0);
                textView.setText(label + result);
            } else {
                textView.setText(label + "0");
            }
        } catch (Exception e) {
            e.printStackTrace();
            textView.setText(label + "0");
        } finally {
            if (cursor != null) cursor.close();
        }
    }

    private void setAmountForTextView(SQLiteDatabase db, String query, TextView textView, String label) {
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                double result = cursor.getDouble(0);
                textView.setText(label + result);
            } else {
                textView.setText(label + "0.0");
            }
        } catch (Exception e) {
            e.printStackTrace();
            textView.setText(label + "0.0");
        } finally {
            if (cursor != null) cursor.close();
        }
    }

    private void loadOrders() {
        orderList = sqLiteConnect.getAllOrderDetails();

        if (orderList != null && !orderList.isEmpty()) {
            adapter = new OrderAdapter(this, orderList, sqLiteConnect);
            recyclerViewOrders.setAdapter(adapter);
        }
    }

    protected void handleNavigation(int itemId) {
        super.handleNavigation(itemId);
    }
}
