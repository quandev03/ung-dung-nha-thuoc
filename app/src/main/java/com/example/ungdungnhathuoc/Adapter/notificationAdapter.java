package com.example.ungdungnhathuoc.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.Model.notificationItem;
import com.example.thongbaonb.R;

import java.util.ArrayList;

public class notificationAdapter extends ArrayAdapter {
    Context context;
    int resource;
    ArrayList<notificationItem> listtb;
    public notificationAdapter(@NonNull Context context, int resource, ArrayList<notificationItem> listtb) {
        super(context, resource);
        this.context=context;
        this.resource=resource;
        this.listtb=listtb;
    }

    @Override
    public int getCount() {
        return this.listtb.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View customView= layoutInflater.inflate(resource,null);
        // ánh xạ
        TextView tvtieude=customView.findViewById(R.id.tvtieude);
        ImageView imgtb=customView.findViewById(R.id.imgtb);
        //thiết lập
        notificationItem thongbao = listtb.get(position);
        tvtieude.setText(thongbao.getTieude());
        // thết lập sự kiện xoa thông báo
        imgtb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Delete"+thongbao.getTieude(), Toast.LENGTH_SHORT).show();
                AlertDialog.Builder buider = new AlertDialog.Builder(context);
                buider.setTitle("Xóa thoong báo  ");
                buider.setMessage("Bạn thực sự muốn xóa thông báo"+
                        thongbao.getTieude()+"?");
                // nếu có
                buider.setPositiveButton("có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listtb.remove(position);
                        notifyDataSetChanged();
                    }
                });
                buider.setNegativeButton("không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                buider.create().show();
            }
        });
        return customView;
    }
}
