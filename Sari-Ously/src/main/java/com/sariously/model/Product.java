package com.sariously.model;

import java.awt.Color;

public class Product {

    private final String name;
    private final String description;
    private final Color color;
    private double price;   // selling price per unit
    private final double cost;    // cost of restocking one unit
    private int stock;
    private int capacity;   // highest stock level reached, used for the stock bar

    public Product(String name, String description, double price, double cost, int stock, Color color) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.cost = cost;
        this.stock = stock;
        this.capacity = Math.max(stock, 10);
        this.color = color;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public Color getColor() { return color; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public double getCost() { return cost; }
    public int getStock() { return stock; }
    public int getCapacity() { return capacity; }

    public void setStock(int stock) {
        this.stock = stock;
        this.capacity = Math.max(capacity, stock);
    }

    public String getBadgeText() {
        String[] parts = name.trim().split("[\\s-]+");
        if (parts.length >= 2 && !parts[1].isEmpty()) {
            return ("" + parts[0].charAt(0) + parts[1].charAt(0)).toUpperCase();
        }
        return name.trim().substring(0, 1).toUpperCase();
    }
}
