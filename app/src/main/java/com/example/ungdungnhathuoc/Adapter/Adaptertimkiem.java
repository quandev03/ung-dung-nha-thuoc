package com.example.ungdungnhathuoc.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ungdungnhathuoc.Model.Thuoc;
import com.example.ungdungnhathuoc.R;

import java.util.ArrayList;

public class Adaptertimkiem extends ArrayAdapter {
    Context context;
    int resource;
    ArrayList<Thuoc> listhuoc1,listthuocbackup,listthuocfilter;
    public Adaptertimkiem(@NonNull Context context, int resource, ArrayList<Thuoc> listhuoc1) {
        super(context, resource);
        this.context=context;
        this.resource=resource;
        this.listhuoc1=this.listthuocbackup=listhuoc1;
    }

    @Override
    public int getCount() {
        return this.listhuoc1.size();
    }

    public ArrayList<Thuoc> getListhuoc1() {
        return listhuoc1;
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
        Thuoc thuoc=listhuoc1.get(position);
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
    // tìm kiếm

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            //lọc các tên thuốc trên các tiêu chí tìm kiếm tên thuốc,công dụng
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults= new FilterResults();
                String query = charSequence.toString().trim().toLowerCase();
                if (query.length()<1){
                    listthuocfilter=listthuocbackup;//bằng ds ban đầu
                }else {
                    listthuocfilter=new ArrayList<>();
                    for(Thuoc thuoc:listthuocbackup){
                        if(thuoc.getTenthuoc().toLowerCase().contains(query)||thuoc.getCongdung().toLowerCase().contains(query)||thuoc.getLoai().toLowerCase().contains(query)){
                            listthuocfilter.add(thuoc);
                        }
                    }
                }
                filterResults.values=listthuocfilter;
                filterResults.count=listthuocfilter.size();
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    listhuoc1=(ArrayList<Thuoc>)filterResults.values;
                    if(filterResults.count>0){
                        notifyDataSetChanged();
                    }else {
                        notifyDataSetChanged();// Cập nhật lại giao diện nếu không có kết quả
                    }

            }
        };
    }
}
