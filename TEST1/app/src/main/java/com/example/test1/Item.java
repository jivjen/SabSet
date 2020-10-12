package com.example.test1;

public class Item {
    private String name;
    private double quantity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public Item(String name, double quantity) {
        this.name = name;
        this.quantity = quantity;
    }
}
