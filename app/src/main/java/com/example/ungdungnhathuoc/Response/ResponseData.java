package com.example.ungdungnhathuoc.Response;

import com.example.ungdungnhathuoc.Model.Account;

public class ResponseData {
    private Account data; // Lớp User đã định nghĩa ở trên

    public Account getData() {
        return data;
    }

    public void setData(Account data) {
        this.data = data;
    }
}
