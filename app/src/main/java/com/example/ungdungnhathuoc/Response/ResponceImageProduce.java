package com.example.ungdungnhathuoc.Response;

public class ResponceImageProduce {
    private String imagepath;
    private String message;

    public ResponceImageProduce(String imagepath, String message) {
        this.imagepath = imagepath;
        this.message = message;
    }

    @Override
    public String toString() {
        return "ResponceImageProduce{" +
                "imagepath='" + imagepath + '\'' +
                ", mesenge='" + message + '\'' +
                '}';
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public String getMesenge() {
        return message;
    }

    public void setMesenge(String message) {
        this.message = message;
    }
}
