package com.example.ungdunghieuthuoc.Adapters;



import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ActionMenuView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ungdunghieuthuoc.Models.DonHang;

import java.util.ArrayList;
import com.example.ungdunghieuthuoc.R;
import com.example.ungdunghieuthuoc.Models.DonHang;

public class DonHangAdapter extends ArrayAdapter implements Filterable {
    Activity context;
    int resource;
    ArrayList<DonHang> listDonHang,listDonHangBackup, listDonHangFilter;
    public DonHangAdapter(@NonNull Activity context, int resource, ArrayList<DonHang> listDH) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
        this.listDonHang = this.listDonHangBackup = listDH;
    }
    @Override
    public int getCount() {
        return listDonHang.size();
    }

    //getView: Phương thức này hiển thị mỗi item trong danh sách
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //inflater: Đối tượng dùng để “thổi phồng” (inflate) tài nguyên layout vào View.
        LayoutInflater inflater = this.context.getLayoutInflater();

        //customView: Giao diện của từng sản phẩm được tạo từ tài nguyên resource.
        View customView = inflater.inflate(this.resource, null);

        //Tìm và ánh xạ các phần tử trong customView
        ImageView imgLogo = customView.findViewById(R.id.imgLogo);
        TextView tvID = customView.findViewById(R.id.tvID);
        TextView tvName = customView.findViewById(R.id.tvName);
        TextView tvPrice = customView.findViewById(R.id.tvPrice);
        Button btnview = customView.findViewById(R.id.btnView);

        //sp: Lấy sản phẩm tại vị trí position trong listSanPham.
        DonHang dh = this.listDonHang.get(position);

        //setImageResource và setText: Thiết lập dữ liệu cho từng phần tử imgLogo, tvID, tvName,
        // và tvPrice trong customView từ sản phẩm sp.
        imgLogo.setImageResource(dh.getLogoDH());
        tvID.setText(dh.getMaDH());
        tvName.setText(dh.getTenDH());
        tvPrice.setText("Đơn giá: " + dh.getGiaDH() + "VNĐ");

        //setOnClickListener: Gán sự kiện bấm vào btnview.
        //kq: Chuỗi chứa thông tin sản phẩm.
        //Toast: Hiển thị thông tin sản phẩm khi bấm nút btnView.
        btnview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String kq = "Mã sản phẩm: " + dh.getMaDH() + "\n"
                        + "Tên sản phẩm: " + dh.getTenDH() + "\n"
                        + "Mã sản phẩm: " + dh.getGiaDH();
                Toast.makeText(context, kq, Toast.LENGTH_SHORT).show();
            }
        });

        //return customView: Trả về customView để hiển thị trên giao diện.
        return customView;
    }
    //getFilter: Tạo bộ lọc để tìm kiếm sản phẩm theo tên hoặc mã.
    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {

            //performFiltering: Chứa logic lọc dữ liệu.
            //query: Chuỗi tìm kiếm.
            //if(query.length() < 1): Nếu chuỗi rỗng, trả về danh sách ban đầu.
            //listSanPhamFilter: Thêm sản phẩm vào danh sách lọc nếu mã hoặc tên sản phẩm chứa query.
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String query = charSequence.toString().toLowerCase().trim();
                if(query.length() < 1){
                    listDonHangFilter = listDonHangBackup;
                }
                else{
                    listDonHangFilter = new ArrayList<>();
                    for (DonHang dh : listDonHangBackup) {
                        if(dh.getMaDH().toLowerCase().contains(query)
                                || dh.getTenDH().toLowerCase().contains(query)){
                            listDonHangFilter.add(dh);
                        }
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = listDonHangFilter;

                return filterResults;
            }

            //publishResults: Cập nhật listSanPham bằng kết quả lọc và gọi notifyDataSetChanged()
            // để cập nhật lại giao diện hiển thị sản phẩm sau khi lọc.
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listDonHang = (ArrayList<DonHang>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

}

