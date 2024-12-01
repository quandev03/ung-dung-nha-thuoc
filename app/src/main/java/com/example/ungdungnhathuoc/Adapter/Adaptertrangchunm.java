package com.example.ungdungnhathuoc.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.ungdungnhathuoc.Model.Thuoc;
import com.example.ungdungnhathuoc.R;

import java.util.ArrayList;

public class Adaptertrangchunm extends ArrayAdapter {
    Context context;
    int resource;
    static ArrayList<Thuoc> listhuoc;
    public Adaptertrangchunm(@NonNull Context context, int resource, ArrayList<Thuoc> listhuoc ) {
        super(context, resource);
        this.context=context;
        this.resource=resource;
        this.listhuoc=listhuoc;
    }

    @Override
    public int getCount() {
        return this.listhuoc.size();
    }

    public ArrayList<Thuoc> getListhuoc() {
        return listhuoc;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View customView= layoutInflater.inflate(resource,null);
        // ánh xạ
       ImageView imgthuoc=customView.findViewById(R.id.imgthuoc);
       TextView tvtenthuoc=customView.findViewById(R.id.tvtenthuoc);
       TextView tvcongdung=customView.findViewById(R.id.tvcongdung);
       TextView tvslht=customView.findViewById(R.id.tvslht);
       TextView tvsldb=customView.findViewById(R.id.tvsldb);
       TextView tvdongia=customView.findViewById(R.id.tvdongia);
       TextView tvloai=customView.findViewById(R.id.tvloai);
        // thiết lập
        Thuoc thuoc=listhuoc.get(position);
        tvtenthuoc.setText("Tên: "+thuoc.getTenthuoc());
        tvloai.setText("Loại: "+thuoc.getLoai());
        tvcongdung.setText("Công dụng: "+thuoc.getCongdung());
        tvslht.setText("Số lượng: "+thuoc.getSlhientai());
        tvsldb.setText("Đã bán "+thuoc.getSldb());
        tvdongia.setText("Giá: "+thuoc.getDongia());
        //hiển thị hình thuoc
        String hinhanh= thuoc.getHinhanh();
        Uri uri=Uri.parse(hinhanh);
        imgthuoc.setImageURI(uri);
        return customView;
    }
}
