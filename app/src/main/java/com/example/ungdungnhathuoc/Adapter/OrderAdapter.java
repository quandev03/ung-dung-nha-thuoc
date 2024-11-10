package com.example.ungdungnhathuoc.Apdater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ungdungnhathuoc.Models.Order;
import com.example.ungdungnhathuoc.R;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private List<Order> orderList;

    // Constructor nhận danh sách đơn hàng
    public OrderAdapter(List<Order> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout item_order.xml cho mỗi mục trong danh sách
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        // Lấy thông tin đơn hàng tại vị trí hiện tại
        Order order = orderList.get(position);

        // Gán giá trị vào các TextView của ViewHolder
        holder.tvOrderId.setText("Mã đơn: " + order.getOrderId());
        holder.tvStatus.setText("Trạng thái: " + order.getStatus());
        holder.tvTotalPrice.setText("Tổng tiền: " + order.getTotalPrice());
        holder.tvOrderDate.setText("Ngày mua: " + order.getOrderDate());
        holder.tvCustomerName.setText("Người mua: " + order.getCustomerName());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    // Lớp ViewHolder để giữ các view của item
    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderId, tvStatus, tvTotalPrice, tvOrderDate, tvCustomerName;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderId = itemView.findViewById(R.id.tvOrderId);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvTotalPrice = itemView.findViewById(R.id.tvTotalPrice);
            tvOrderDate = itemView.findViewById(R.id.tvOrderDate);
            tvCustomerName = itemView.findViewById(R.id.tvCustomerName);
        }
    }
}
