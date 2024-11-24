package com.example.ungdungnhathuoc;

import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.Adapter.notificationAdapter;
import com.example.Model.notificationItem;

import java.util.ArrayList;

public class ThongbaonbActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ListView lvthongbao;
        notificationAdapter thongbaoAdapter;
        ArrayList<notificationItem>listtb;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            lvthongbao=findViewById(R.id.lvthongbao);
            listtb=new ArrayList<>();
            listtb.add(new notificationItem("Thông báo 1"));
            listtb.add(new notificationItem("Thông báo 2"));
            listtb.add(new notificationItem("Thông báo 3"));

            thongbaoAdapter= new notificationAdapter(MainActivity.this,R.layout.notification_item,listtb);
            lvthongbao.setAdapter(thongbaoAdapter);
        }
}