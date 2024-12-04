package com.example.ungdungnhathuoc.Request;

public class FilterOrder {

    private String username;
    private int status = -1;

    public FilterOrder(
            String username,
            int status
    ){
        this.username = username;
        this.status = status;
    }
    public FilterOrder(String username) {
        this.username = username;
        this.status = -1;
    }
    public FilterOrder(int status) {
        this.status = status;
    }
    public String buildCondition(){
        String condition = "";
        if(username != null){
            condition = "username = '" + username + "'";
        }
        if(status != -1) {
            condition += " AND";
            condition += " status = " + status;
        };
        return condition;
    }
}
