package com.example.ungdungnhathuoc.Adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ungdungnhathuoc.Model.Thuoc;
import com.example.ungdungnhathuoc.R;

import java.util.List;

public class ThuocAdapter extends RecyclerView.Adapter<ThuocAdapter.ThuocViewHolder> {

    private final List<Thuoc> thuocList;
    private final OnThuocEditListener editListener;
    public ThuocAdapter(List<Thuoc> thuocList, OnThuocEditListener editListener) {
        this.thuocList = thuocList;
        this.editListener = editListener;
    }

    @NonNull
    @Override
    public ThuocViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thuoc, parent, false);
        return new ThuocViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThuocViewHolder holder, int position) {
        Thuoc thuoc = thuocList.get(position);

        holder.textTenthuoc.setText(thuoc.getTenthuoc());
        holder.textCongdung.setText(thuoc.getCongdung());
        holder.textSoluong.setText("Số lượng hiện tại: " + thuoc.getSlhientai() + " | Số lượng đã bán: " + thuoc.getSldb());
        holder.textDongia.setText("Đơn giá: " + thuoc.getDongia() + " VND");

        // Load image (placeholder if no image URL)
        Glide.with(holder.itemView.getContext())
                .load(thuoc.getHinhanh())
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.imageThuoc);

        // Edit button click
        holder.buttonEdit.setOnClickListener(v -> editListener.onEdit(thuoc));
        holder.buttonDelete.setOnClickListener(v -> editListener.onDelete(thuoc));
    }

    @Override
    public int getItemCount() {
        return thuocList.size();
    }

    public static class ThuocViewHolder extends RecyclerView.ViewHolder {
        ImageView imageThuoc;
        TextView textTenthuoc, textCongdung, textSoluong, textDongia;
        ImageView buttonEdit, buttonDelete;

        public ThuocViewHolder(@NonNull View itemView) {
            super(itemView);
            imageThuoc = itemView.findViewById(R.id.imageThuoc);
            textTenthuoc = itemView.findViewById(R.id.textTenthuoc);
            textCongdung = itemView.findViewById(R.id.textCongdung);
            textSoluong = itemView.findViewById(R.id.textSoluong);
            textDongia = itemView.findViewById(R.id.textDongia);
            buttonEdit = itemView.findViewById(R.id.buttonEdit);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }
    }

    public interface OnThuocEditListener {
        void onEdit(Thuoc thuoc);
        void onDelete(Thuoc thuoc);
    }
}

