package com.example.ungdungnhathuoc.Model;

public class Order {
    private String orderId;
    private String status;
    private double totalPrice;  // Change to double for numeric operations
    private String orderDate;
    private String customerName;
    private String contactInfo;
    private String address;
    private String customerPhone;
    private String items;


    // Constructor


    public Order(String orderId, String status, double totalPrice, String orderDate, String customerName, String contactInfo, String address, String customerPhone, String items) {
        this.orderId = orderId;
        this.status = status;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.customerName = customerName;
        this.contactInfo = contactInfo;
        this.address = address;
        this.customerPhone = customerPhone;
        this.items = items;
    }

    // Getters
    public String getOrderId() {
        return orderId;
    }

    public String getStatus() {
        return status;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public String getAddress() {
        return address;
    }

    public String getItems() {
        return items;
    }

    // Setters
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        customerPhone = customerPhone;
    }

    public void setItems(String items) {
        this.items = items;
    }

    // Override toString() for easier debugging

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", status='" + status + '\'' +
                ", totalPrice=" + totalPrice +
                ", orderDate='" + orderDate + '\'' +
                ", customerName='" + customerName + '\'' +
                ", contactInfo='" + contactInfo + '\'' +
                ", address='" + address + '\'' +
                ", CustomerPhone='" + customerPhone + '\'' +
                ", items='" + items + '\'' +
                '}';
    }
}
