package com.example.ungdungnhathuoc.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ungdungnhathuoc.Model.DonHang;
import com.example.ungdungnhathuoc.R;

import java.util.ArrayList;

public class DonHangAdapter extends ArrayAdapter<DonHang> {
    private Activity context;
    private int resource;
    private ArrayList<DonHang> listDonHang, listDonHangBackup, listDonHangFilter;

    // ViewHolder để tái sử dụng các View trong danh sách
    static class ViewHolder {
        ImageView imgLogo;
        TextView tvID, tvName, tvPrice;
        Button btnView;
    }

    public DonHangAdapter(@NonNull  Activity context, int resource, ArrayList<DonHang> listDH) {
        super(context, resource, listDH);
        this.context = context;
        this.resource = resource;
        this.listDonHang = listDH;
        this.listDonHangBackup = new ArrayList<>(listDH);
    }

    @Override
    public int getCount() {
        return listDonHang.size();
    }

    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        ViewHolder holder;
//
//        // Kiểm tra convertView, nếu null thì tạo mới
//        if (convertView == null) {
//            LayoutInflater inflater = context.getLayoutInflater();
//            convertView = inflater.inflate(resource, parent, false);
//
//            // Gán các View vào ViewHolder
//            holder = new ViewHolder();
//
//
//            convertView.setTag(holder); // Lưu ViewHolder vào convertView
//        } else {
//            holder = (ViewHolder) convertView.getTag(); // Lấy lại ViewHolder
//        }
//
//        DonHang dh = listDonHang.get(position);
//
//        // Thiết lập dữ liệu cho các View
//        holder.imgLogo.setImageResource(dh.getLogoDH());
//        holder.tvID.setText(dh.getMaDH());
//        holder.tvName.setText(dh.getTenDH());
//        holder.tvPrice.setText("Đơn giá: " + dh.getGiaDH() + " VNĐ");
//
//        // Sự kiện khi người dùng bấm vào btnView
//        holder.btnView.setOnClickListener(view -> {
//            String kq = "Mã sản phẩm: " + dh.getMaDH() + "\n"
//                    + "Tên sản phẩm: " + dh.getTenDH() + "\n"
//                    + "Giá: " + dh.getGiaDH();
//            Toast.makeText(context, kq, Toast.LENGTH_SHORT).show();
//        });
//
//        return convertView;
//    }
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;

        // Kiểm tra convertView, nếu null thì tạo mới
        if (convertView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(resource, parent, false);

            // Gán các View vào ViewHolder
            holder = new ViewHolder();
            holder.imgLogo = convertView.findViewById(R.id.imgLogo); // ImageView để hiển thị hình ảnh thuốc
            holder.tvID = convertView.findViewById(R.id.tvID);
            holder.tvName = convertView.findViewById(R.id.tvName);
            holder.tvPrice = convertView.findViewById(R.id.tvPrice);
            holder.btnView = convertView.findViewById(R.id.btnView);

            convertView.setTag(holder); // Lưu ViewHolder vào convertView
        } else {
            holder = (ViewHolder) convertView.getTag(); // Lấy lại ViewHolder
        }

        DonHang dh = listDonHang.get(position);

        // Thiết lập dữ liệu cho các View
        if (dh.getLogoDH() != null) {
            holder.imgLogo.setImageBitmap(dh.getLogoDH()); // Sử dụng Bitmap để hiển thị hình ảnh
        } else {
            holder.imgLogo.setImageResource(R.drawable.giaikhat); // Nếu không có hình ảnh, hiển thị ảnh mặc định
        }
        holder.tvID.setText(dh.getMaDH());
        holder.tvName.setText(dh.getTenDH());
        holder.tvPrice.setText("Đơn giá: " + dh.getGiaDH() + " VNĐ");

        // Sự kiện khi người dùng bấm vào btnView
        holder.btnView.setOnClickListener(view -> {
            String kq = "Mã sản phẩm: " + dh.getMaDH() + "\n"
                    + "Tên sản phẩm: " + dh.getTenDH() + "\n"
                    + "Giá: " + dh.getGiaDH();
            Toast.makeText(context, kq, Toast.LENGTH_SHORT).show();
        });

        return convertView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String query = charSequence.toString().toLowerCase().trim();
                if (query.isEmpty()) {
                    listDonHangFilter = listDonHangBackup;
                } else {
                    listDonHangFilter = new ArrayList<>();
                    for (DonHang dh : listDonHangBackup) {
                        if (dh.getMaDH().toLowerCase().contains(query) ||
                                dh.getTenDH().toLowerCase().contains(query)) {
                            listDonHangFilter.add(dh);
                        }
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = listDonHangFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listDonHang = (ArrayList<DonHang>) filterResults.values;
                notifyDataSetChanged(); // Cập nhật lại giao diện khi có kết quả lọc
            }
        };
    }
}
