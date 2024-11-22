//package com.example.ungdungnhathuoc;
//
//import android.os.Bundle;
//import android.view.Menu;
//import android.view.View;
//import android.widget.ListView;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.drawerlayout.widget.DrawerLayout;
//
//import com.example.ungdungnhathuoc.Adapter.DonHangAdapter;
//import com.example.ungdungnhathuoc.Model.DonHang;
//import com.google.android.material.navigation.NavigationView;
//import com.google.android.material.snackbar.Snackbar;
//import androidx.navigation.NavController;
//import androidx.navigation.Navigation;
//import androidx.navigation.ui.AppBarConfiguration;
//import androidx.navigation.ui.NavigationUI;
//
//import com.example.ungdungnhathuoc.databinding.ActivityOrderHistory2Binding;
//
//import java.util.ArrayList;
//
//public class OrderHistoryActivity2 extends AppCompatActivity {
//    ListView lvDonHang;
//    ArrayList<DonHang> listDonHang;
//    DonHangAdapter adapterDonHang;
////    private AppBarConfiguration mAppBarConfiguration;
////    private ActivityOrderHistory2Binding binding;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        // Thiết lập view binding
//        binding = ActivityOrderHistory2Binding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        // Thiết lập toolbar
//        setSupportActionBar(binding.appBarOrderHistory2.toolbar);
//
//        // Thêm hành động cho FAB (Floating Action Button)
//        binding.appBarOrderHistory2.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null)
//                        .setAnchorView(R.id.fab).show();
//            }
//        });
//
//        // Cấu hình Navigation Drawer
//        DrawerLayout drawer = binding.drawerLayout;
//        NavigationView navigationView = binding.navView;
//
//        // Cấu hình AppBarConfiguration cho các top-level destinations
//        mAppBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
//                .setOpenableLayout(drawer)
//                .build();
//
//        // Khởi tạo NavController và kết nối với AppBarConfiguration
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_order_history2);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);
//
//        // Thiết lập ListView với dữ liệu
//        lvDonHang = findViewById(R.id.lvDonHang);
//        listDonHang = new ArrayList<>();
//        listDonHang.add(new DonHang("Red bull", 15000, R.drawable.giaikhat, "SP001"));
//
//        adapterDonHang = new DonHangAdapter(OrderHistoryActivity2.this, R.layout.lv_donhang, listDonHang);
//        lvDonHang.se    tAdapter(adapterDonHang);
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflating menu; this adds items to the action bar if it's present
//        getMenuInflater().inflate(R.menu.order_history_activity2, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_order_history2);
//        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
//                || super.onSupportNavigateUp();
//    }
//}
