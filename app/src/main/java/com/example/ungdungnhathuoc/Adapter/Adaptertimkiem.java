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
import java.util.List;

public class AdapterTimKiem extends ArrayAdapter<Thuoc> {
    private Context context;
    private int resource;
    private List<Thuoc> listThuoc;
    private List<Thuoc> listThuocBackup;

    public AdapterTimKiem(@NonNull Context context, int resource, @NonNull List<Thuoc> listThuoc) {
        super(context, resource, listThuoc);
        this.context = context;
        this.resource = resource;
        this.listThuoc = new ArrayList<>(listThuoc);
        this.listThuocBackup = new ArrayList<>(listThuoc);
    }

    @Override
    public int getCount() {
        return listThuoc.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);
            holder = new ViewHolder();
            holder.imgThuoc = convertView.findViewById(R.id.imgthuoc);
            holder.tvTenThuoc = convertView.findViewById(R.id.tvtenthuoc);
            holder.tvCongDung = convertView.findViewById(R.id.tvcongdung);
            holder.tvSlHt = convertView.findViewById(R.id.tvslht);
            holder.tvSlDb = convertView.findViewById(R.id.tvsldb);
            holder.tvDonGia = convertView.findViewById(R.id.tvdongia);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Thuoc thuoc = listThuoc.get(position);
        if (thuoc != null) {
            holder.tvTenThuoc.setText("Tên: " + thuoc.getTenthuoc());
            holder.tvCongDung.setText("Công dụng: " + thuoc.getCongdung());
            holder.tvSlHt.setText("Số lượng: " + thuoc.getSlhientai());
            holder.tvSlDb.setText("Đã bán: " + thuoc.getSldb());
            holder.tvDonGia.setText("Giá: " + thuoc.getDongia());

            Uri imageUri = Uri.parse(thuoc.getHinhanh());
            holder.imgThuoc.setImageURI(imageUri);
        }

        return convertView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();
                String query = charSequence == null ? "" : charSequence.toString().trim().toLowerCase();

                if (query.isEmpty()) {
                    filterResults.values = new ArrayList<>(listThuocBackup);
                } else {
                    List<Thuoc> filteredList = new ArrayList<>();
                    for (Thuoc thuoc : listThuocBackup) {
                        if (thuoc.getTenthuoc().toLowerCase().contains(query) ||
                                thuoc.getCongdung().toLowerCase().contains(query)) {
                            filteredList.add(thuoc);
                        }
                    }
                    filterResults.values = filteredList;
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listThuoc.clear();
                if (filterResults.values != null) {
                    listThuoc.addAll((List<Thuoc>) filterResults.values);
                }
                notifyDataSetChanged();
            }
        };
    }

    private static class ViewHolder {
        ImageView imgThuoc;
        TextView tvTenThuoc;
        TextView tvCongDung;
        TextView tvSlHt;
        TextView tvSlDb;
        TextView tvDonGia;
    }
}
