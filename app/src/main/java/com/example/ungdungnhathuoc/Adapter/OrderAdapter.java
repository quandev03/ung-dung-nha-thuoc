package com.example.ungdungnhathuoc.Adapter;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ungdungnhathuoc.Activity.ThongKeDonHangActivity;
import com.example.ungdungnhathuoc.Activity.ThongTinDonHangNBActivity;
import com.example.ungdungnhathuoc.Data.SQLiteConnect;
import com.example.ungdungnhathuoc.Model.Order;
import com.example.ungdungnhathuoc.R;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private Context context;
    private List<Order> orderList;
    private SQLiteConnect sqLiteConnect;

    public OrderAdapter(Context context, List<Order> orderList, SQLiteConnect sqLiteConnect) {
        this.context = context;
        this.orderList = orderList;
        this.sqLiteConnect = sqLiteConnect;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        Order order = orderList.get(position);

        // Update order details
        updateOrderDetails(holder, order);

        // Set button listeners
        holder.btnConfirm.setOnClickListener(v -> handleOrderConfirm(order, position));
        holder.btnCancel.setOnClickListener(v -> handleOrderCancel(order, position));
        holder.btnViewDetails.setOnClickListener(v -> handleViewOrderDetails(order.getOrderId()));
    }

    private void updateOrderDetails(OrderViewHolder holder, Order order) {
        // Update Order ID, Status, Total Price, Order Date
        holder.tvOrderId.setText(String.valueOf(order.getOrderId()));
        holder.tvStatus.setText(order.getStatus() != null && !order.getStatus().isEmpty() ? order.getStatus() : "Không xác định");
        holder.tvTotalPrice.setText(order.getTongTien() != 0.0 ? String.format("%,.2f", order.getTongTien()) : "Chưa có giá");
        holder.tvOrderDate.setText(order.getOrderDate() != null && !order.getOrderDate().isEmpty() ? order.getOrderDate() : "Ngày không xác định");

        // Update customer info
        if (order.getUser() != null) {
            holder.tvCustomerName.setText(order.getUser().getFullname());
            holder.tvContactInfo.setText(order.getUser().getPhone());
            holder.tvAddress.setText(order.getUser().getAddress());
        } else {
            holder.tvCustomerName.setText("Thông tin khách hàng không có");
            holder.tvContactInfo.setText("-");
            holder.tvAddress.setText("-");
        }

        // Update product inf
    }


    private void handleOrderConfirm(Order order, int position) {
        SQLiteDatabase db = sqLiteConnect.getWritableDatabase();
        boolean result = sqLiteConnect.confirmOrder(order.getOrderId());

        if (result) {
            order.setStatus("Đã xác nhận");
            Toast.makeText(context, "Đơn hàng đã được xác nhận!", Toast.LENGTH_SHORT).show();
            notifyItemChanged(position);
            reloadStatisticsIfNeeded();
        } else {
            Toast.makeText(context, "Không thể xác nhận đơn hàng!", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleOrderCancel(Order order, int position) {
        SQLiteDatabase db = sqLiteConnect.getWritableDatabase();
        boolean result = sqLiteConnect.cancelOrder(order.getOrderId());

        if (result) {
            order.setStatus("Đã hủy");
            Toast.makeText(context, "Đơn hàng đã được hủy!", Toast.LENGTH_SHORT).show();
            notifyItemChanged(position);
            reloadStatisticsIfNeeded();
        } else {
            Toast.makeText(context, "Không thể hủy đơn hàng!", Toast.LENGTH_SHORT).show();
        }
    }

    private void reloadStatisticsIfNeeded() {
        if (context instanceof ThongKeDonHangActivity) {
            ((ThongKeDonHangActivity) context).loadStatistics();
        }
    }

    private void handleViewOrderDetails(int orderId) {
        Intent intent = new Intent(context, ThongTinDonHangNBActivity.class);
        intent.putExtra("order_id", orderId);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {

        TextView tvOrderId, tvStatus, tvTotalPrice, tvOrderDate, tvCustomerName, tvContactInfo, tvAddress, tvItems;
        ImageView imgSanPham;
        Button btnCancel, btnConfirm, btnViewDetails;

        public OrderViewHolder(View itemView) {
            super(itemView);
            tvOrderId = itemView.findViewById(R.id.tvOrderId);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvTotalPrice = itemView.findViewById(R.id.tvTotalPrice);
            tvOrderDate = itemView.findViewById(R.id.tvOrderDate);
            tvCustomerName = itemView.findViewById(R.id.tvCustomerName);
            tvContactInfo = itemView.findViewById(R.id.tvContactInfo);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvItems = itemView.findViewById(R.id.tvItems);
            imgSanPham = itemView.findViewById(R.id.imgSanPham);
            btnCancel = itemView.findViewById(R.id.btnCancel);
            btnConfirm = itemView.findViewById(R.id.btnConfirm);
            btnViewDetails = itemView.findViewById(R.id.btnViewDetails);
        }
    }
}
