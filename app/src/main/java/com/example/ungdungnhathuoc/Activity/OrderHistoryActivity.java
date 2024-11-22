//package com.example.ungdungnhathuoc.Activity;
//
//import android.os.Bundle;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
//import android.widget.ListView;
//import android.widget.SearchView;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//import com.example.appquanlyhieuthuoc.Adapters.DonHangAdapter;
//import com.example.appquanlyhieuthuoc.Models.DonHang;
//import com.example.appquanlyhieuthuoc.R;
//import com.example.ungdungnhathuoc.Adapter.DonHangAdapter;
//import com.example.ungdungnhathuoc.Model.DonHang;
//import com.example.ungdungnhathuoc.R;
//import com.google.android.material.bottomnavigation.BottomNavigationView;
//
//import java.util.ArrayList;
//
//public class OrderHistoryActivity extends AppCompatActivity {
//    ListView lvDonHang;
//    ArrayList<DonHang> listDonHang;
//    DonHangAdapter adapterDonHang;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(com.example.ungdungnhathuoc.R.layout.activity_order_history);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//        lvDonHang = findViewById(R.id.lvDonHang);
//        listDonHang = new ArrayList<>();
//        listDonHang.add(new DonHang( "Red bull", 15000, R.drawable.giaikhat, "SP001"));
//        adapterDonHang = new DonHangAdapter(OrderHistoryActivity.this, R.layout.lv_donhang, listDonHang);
//        lvDonHang.setAdapter(adapterDonHang);
//    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.search_order_history,menu);
//        MenuItem searchBar = menu.findItem(R.id.searchBar);
//        SearchView searchView = (SearchView) searchBar.getActionView();
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                searchView.clearFocus();
//                adapterDonHang.getFilter().filter(s);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                adapterDonHang.getFilter().filter(s);
//                return false;
//            }
//        });
//        return super.onCreateOptionsMenu(menu);
//    }
//}