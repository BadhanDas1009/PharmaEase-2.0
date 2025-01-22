package com.example.pharmaease20;
public class Medicine {
    private int id;
    private String name;
    private String price;
    private String description;
    private String type;
    private int quantity;
    private String imagePath;

    public Medicine(int id, String name, String price, String description, String type, int quantity, String imagePath) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.type = type;
        this.quantity = quantity;
        this.imagePath = imagePath;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getPrice() { return price; }
    public String getDescription() { return description; }
    public String getType() { return type; }
    public int getQuantity() { return quantity; }
    public String getImagePath() { return imagePath; }
}
