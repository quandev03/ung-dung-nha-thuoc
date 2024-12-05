package com.example.ungdungnhathuoc.Activity;

import static com.example.ungdungnhathuoc.AddProduce.IS_ADD;
import static com.example.ungdungnhathuoc.AddProduce.THUOC;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ungdungnhathuoc.Adapter.ThuocAdapter;
import com.example.ungdungnhathuoc.AddProduce;
import com.example.ungdungnhathuoc.Data.SQLiteConnect;
import com.example.ungdungnhathuoc.Model.Thuoc;
import com.example.ungdungnhathuoc.R;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public class ThuocKhoHangActivity extends BaseActivity {
    public static final String TAG = "ThuocKhoHangActivity";

    private RecyclerView rcvThuoc ;
    private ThuocAdapter thuocAdapter ;
    private final List<Thuoc> thuocList = new CopyOnWriteArrayList<>();
    SQLiteConnect sqLiteConnect ;
    Thread workerThread;
    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_thuoc_kho_hang);
        // Mapping UI
        rcvThuoc = findViewById(R.id.rcv_thuoc);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.lavender));
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Connect SQL
        sqLiteConnect = new SQLiteConnect(this);



        thuocAdapter = new ThuocAdapter(thuocList, new ThuocAdapter.OnThuocEditListener() {
            @Override
            public void onEdit(Thuoc thuoc) {
                Intent intent = new Intent(ThuocKhoHangActivity.this, AddProduce.class);
                intent.putExtra(IS_ADD, false);
                Bundle bundle = new Bundle();
                bundle.putParcelable(THUOC, thuoc);
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onDelete(Thuoc thuoc) {
                AlertDialog alertDialog = new AlertDialog.Builder(ThuocKhoHangActivity.this)
                        .setTitle("Thông báo")
                        .setMessage("Bạn có muốn xóa sản phẩm "+ thuoc.getTenthuoc() +" không?")
                        .setNegativeButton("Có", (dialog, which) -> {

                            boolean result = sqLiteConnect.deleteThuocById(thuoc.getId());
                            Toast.makeText(ThuocKhoHangActivity.this, result?"Xóa thành công" : "Xóa thất bại", Toast.LENGTH_SHORT).show();
                            if (result){
                                thuocList.remove(thuoc);
                                thuocAdapter.notifyDataSetChanged();
                            }
                            dialog.dismiss();
                        })
                        .setPositiveButton("Không", ((dialog, which) -> {
                            dialog.dismiss();
                        }))
                        .create();

                alertDialog.show();
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvThuoc.setLayoutManager(linearLayoutManager);
        rcvThuoc.setAdapter(thuocAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Get information from db using thread to prevent ANR

        workerThread = new Thread(() -> {
            try {
                thuocList .clear();
                List<Thuoc> dataResult = sqLiteConnect.getAllThuoc();
                thuocList.addAll(dataResult);
                runOnUiThread(()-> thuocAdapter.notifyDataSetChanged());
            } catch (Exception e) {
                Log.d(TAG, "onResume: Exception ==> "+e.getMessage());
            }

        });
        workerThread.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}