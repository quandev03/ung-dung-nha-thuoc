package com.example.ungdungnhathuoc.Model;

public class Order {
    private int orderId;  // Mã đơn hàng
    private String status;  // Trạng thái đơn hàng (chẳng hạn: Đã giao, Đang xử lý)
    private double tongTien;
    private String orderDate;  // Ngày đặt hàng
    private User user;  // Người mua (liên kết với đối tượng User)
    private Thuoc thuoc;  // Sản phẩm (liên kết với đối tượng Thuoc)

    public Order() {

    }

    public Order(int orderId, String statusString, double totalAmount, String orderDate, User user, Thuoc thuoc) {
        this.orderId = orderId;
        this.tongTien = tongTien;
        this.status = status;
        this.orderDate = orderDate;
        this.user = user;
        this.thuoc = thuoc;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Thuoc getThuoc() {
        return thuoc;
    }

    public void setThuoc(Thuoc thuoc) {
        this.thuoc = thuoc;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", status='" + status + '\'' +
                ", tongTien=" + tongTien +
                ", orderDate='" + orderDate + '\'' +
                ", user=" + user +
                ", thuoc=" + thuoc +
                '}';
    }
}


    // Constructor

