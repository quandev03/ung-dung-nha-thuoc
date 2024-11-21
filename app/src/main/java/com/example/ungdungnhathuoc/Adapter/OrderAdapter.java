// OrderAdapter.java
package com.example.ungdungnhathuoc.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ungdungnhathuoc.Activity.ThongKeDonHangActivity;
import com.example.ungdungnhathuoc.Activity.ThongTinDonHangNBActivity;
import com.example.ungdungnhathuoc.Models.Order;
import com.example.ungdungnhathuoc.R;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private List<Order> orderList;
    private Context context;

    // Constructor nhận danh sách đơn hàng và context
    public OrderAdapter(List<Order> orderList, Context context) {
        this.orderList = orderList;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);

        // Gán giá trị vào các TextView của ViewHolder
        holder.tvOrderId.setText("Mã đơn: " + order.getOrderId());
        holder.tvStatus.setText("Trạng thái: " + order.getStatus());
        holder.tvTotalPrice.setText("Tổng tiền: " + order.getTotalPrice());
        holder.tvOrderDate.setText("Ngày mua: " + order.getOrderDate());
        holder.tvCustomerName.setText("Người mua: " + order.getCustomerName());
        holder.tvContactInfo.setText("Số điện thoại: " + order.getCustomerPhone());
        holder.tvAddress.setText("Địa chỉ: " + order.getAddress());
        holder.tvItems.setText("Sản phẩm: " + order.getItems());

        // Thiết lập sự kiện click cho item
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ThongTinDonHangNBActivity.class); // Open ThongTinDonHangNBActivity
            intent.putExtra("order_id", order.getOrderId());
            intent.putExtra("order_status", order.getStatus());
            intent.putExtra("order_price", order.getTotalPrice());
            intent.putExtra("order_date", order.getOrderDate());
            intent.putExtra("order_customer", order.getCustomerName());
            intent.putExtra("order_phone", order.getCustomerPhone());
            intent.putExtra("order_address", order.getAddress());
            intent.putExtra("order_items", order.getItems());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderId, tvStatus, tvTotalPrice, tvOrderDate, tvCustomerName, tvContactInfo, tvAddress, tvItems;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderId = itemView.findViewById(R.id.orderId);
            tvStatus = itemView.findViewById(R.id.status);
            tvTotalPrice = itemView.findViewById(R.id.totalPrice);
            tvOrderDate = itemView.findViewById(R.id.orderDate);
            tvCustomerName = itemView.findViewById(R.id.customerName);
            tvContactInfo = itemView.findViewById(R.id.contactInfo);
            tvAddress = itemView.findViewById(R.id.address);
            tvItems = itemView.findViewById(R.id.items);
        }
    }

}
