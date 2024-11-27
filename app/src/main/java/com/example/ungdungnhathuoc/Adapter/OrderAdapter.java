package com.example.ungdungnhathuoc.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ungdungnhathuoc.Activity.ThongKeDonHangActivity;
import com.example.ungdungnhathuoc.Activity.ThongTinDonHangNBActivity;
import com.example.ungdungnhathuoc.Model.Order;
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

        // Hiển thị thông tin tóm tắt
        holder.tvOrderId.setText("Mã đơn: " + order.getOrderId());
        holder.tvStatus.setText("Trạng thái: " + order.getStatus());
        holder.tvTotalPrice.setText("Tổng tiền: " + order.getTotalPrice());
        holder.tvOrderDate.setText("Ngày mua: " + order.getOrderDate());
        holder.tvItems.setText("Sản phẩm: " + order.getItems());

        // Hiển thị ảnh sản phẩm (nếu bạn sử dụng ID tài nguyên)
//        holder.imgSanPham.setImageResource(order.getImgSanPham());
// Fixed this line

        // Ẩn các thông tin không cần thiết trong trang thống kê
        holder.tvCustomerName.setVisibility(View.GONE);
        holder.tvContactInfo.setVisibility(View.GONE);
        holder.tvAddress.setVisibility(View.GONE);

        // Tạo listener cho việc chuyển qua trang chi tiết đơn hàng
        View.OnClickListener orderDetailClickListener = v -> {
            Intent intent = new Intent(context, ThongTinDonHangNBActivity.class);
            intent.putExtra("order_id", order.getOrderId());
            intent.putExtra("order_status", order.getStatus());
            intent.putExtra("order_price", order.getTotalPrice());
            intent.putExtra("order_date", order.getOrderDate());
            intent.putExtra("order_customer", order.getCustomerName());
            intent.putExtra("order_phone", order.getCustomerPhone());
            intent.putExtra("order_address", order.getAddress());
            intent.putExtra("order_items", order.getItems());
//            intent.putExtra("order_ImgSanPham", order.getImgSanPham(),);  // Fixed this line

            context.startActivity(intent);
        };

        // Áp dụng listener cho item view và nút "Xem chi tiết"
        holder.itemView.setOnClickListener(orderDetailClickListener);
        holder.btnViewDetails.setOnClickListener(orderDetailClickListener);

        // Sự kiện nhấn nút xác nhận đơn hàng
        holder.btnConfirm.setOnClickListener(v -> {
            order.setStatus("Đã xác nhận");
            notifyItemChanged(position); // Cập nhật lại trạng thái của item
            if (context instanceof ThongKeDonHangActivity) {
                ((ThongKeDonHangActivity) context).updateStatistics();
            }
        });

        // Sự kiện nhấn nút hủy đơn hàng
        holder.btnCancel.setOnClickListener(v -> {
            order.setStatus("Đã hủy");
            notifyItemChanged(position); // Cập nhật lại trạng thái của item
            if (context instanceof ThongKeDonHangActivity) {
                ((ThongKeDonHangActivity) context).updateStatistics();
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderId, tvStatus, tvTotalPrice, tvOrderDate, tvCustomerName, tvContactInfo, tvAddress, tvItems;
        Button btnConfirm, btnCancel, btnViewDetails;
        ImageView imgSanPham;

        public OrderViewHolder(@NonNull View itemView) {
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

            // Nút xác nhận, hủy và xem chi tiết
            btnConfirm = itemView.findViewById(R.id.btnConfirm);
            btnCancel = itemView.findViewById(R.id.btnCancel);
            btnViewDetails = itemView.findViewById(R.id.btnViewDetails);
        }
    }
}
