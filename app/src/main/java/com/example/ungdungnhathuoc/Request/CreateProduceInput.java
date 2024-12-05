package com.example.ungdungnhathuoc.Request;

public class CreateProduceInput {
    private String name_produce;
    private int price;
    private int quantity;
    private String description;
    private String image;
    private String category;

    public CreateProduceInput(String name_produce, int price, int quantity, String description, String image, String category){
        this.name_produce = name_produce;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.image = null;
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName_produce() {
        return name_produce;
    }

    public void setName_produce(String name_produce) {
        this.name_produce = name_produce;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "CreateProduceInput{" +
                "category='" + category + '\'' +
                ", name_produce='" + name_produce + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
