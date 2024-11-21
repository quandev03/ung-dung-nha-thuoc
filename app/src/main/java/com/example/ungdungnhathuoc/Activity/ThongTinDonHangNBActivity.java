package com.example.ungdungnhathuoc.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.example.ungdungnhathuoc.R;
import com.google.android.material.navigation.NavigationView;
import java.text.NumberFormat;
import java.util.Locale;

public class ThongTinDonHangNBActivity extends BaseActivity {

    private TextView tvOrderId, tvOrderStatus, tvOrderPrice, tvOrderDate, tvCustomerName, tvContactInfo, tvAddress, tvItems;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_don_hang_nbactivity);

        // Setup toolbar
        setupToolbar();

        // Setup navigation drawer
        setupNavigationDrawer();

        // Initialize TextViews for displaying order details
        initializeTextViews();

        // Load order data from Intent
        loadOrderDataFromIntent();

        // Setup NavigationView listener
        setupNavigationListener();
    }

    // Setup the toolbar
    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    // Setup navigation drawer with ActionBarDrawerToggle
    private void setupNavigationDrawer() {
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, findViewById(R.id.toolbar), R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    // Initialize TextViews to display order details
    private void initializeTextViews() {
        tvOrderId = findViewById(R.id.orderId);
        tvOrderStatus = findViewById(R.id.status);
        tvOrderPrice = findViewById(R.id.totalPrice);
        tvOrderDate = findViewById(R.id.orderDate);
        tvCustomerName = findViewById(R.id.customerName);
        tvContactInfo = findViewById(R.id.contactInfo);
        tvAddress = findViewById(R.id.address);
        tvItems = findViewById(R.id.items);
    }

    // Load order data from Intent and handle missing values
    private void loadOrderDataFromIntent() {
        Intent intent = getIntent();

        // Retrieve data from the Intent and use default values if any data is missing
        String orderId = intent.getStringExtra("orderId");
        String status = intent.getStringExtra("order_status");
        double totalPrice = intent.getDoubleExtra("order_price", 0.0);
        String orderDate = intent.getStringExtra("order_date");
        String customerName = intent.getStringExtra("order_customer");
        String contactInfo = intent.getStringExtra("order_phone");
        String address = intent.getStringExtra("order_address");
        String items = intent.getStringExtra("order_items");

        // Set default values for missing data
        orderId = (orderId != null) ? orderId : "Không có mã đơn hàng";
        status = (status != null) ? status : "Chưa có trạng thái";
        orderDate = (orderDate != null) ? orderDate : "Không có thông tin ngày đặt";
        customerName = (customerName != null) ? customerName : "Chưa có tên khách hàng";
        contactInfo = (contactInfo != null) ? contactInfo : "Chưa có thông tin liên hệ";
        address = (address != null) ? address : "Chưa có địa chỉ";
        items = (items != null) ? items : "Chưa có sản phẩm";

        // Format totalPrice as currency (Vietnamese)
        String formattedPrice = NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(totalPrice);

        // Set the data to respective TextViews
        tvOrderId.setText("Mã đơn hàng: " + orderId);
        tvOrderStatus.setText("Trạng thái: " + status);
        tvOrderPrice.setText("Tổng tiền: " + formattedPrice);
        tvOrderDate.setText("Ngày đặt: " + orderDate);
        tvCustomerName.setText("Tên khách hàng: " + customerName);
        tvContactInfo.setText("Liên hệ: " + contactInfo);
        tvAddress.setText("Địa chỉ: " + address);
        tvItems.setText("Sản phẩm: " + items);
    }

    // Setup listener for NavigationView items
    private void setupNavigationListener() {
        navigationView.setNavigationItemSelectedListener(item -> {
            handleNavigation(item.getItemId());
            drawerLayout.closeDrawer(GravityCompat.START); // Close drawer after item selection
            return true;
        });
    }

    // Handle navigation item clicks (can be extended if needed)
    @Override
    protected void handleNavigation(int itemId) {
        super.handleNavigation(itemId);  // Call the handleNavigation method from BaseActivity
    }
}
