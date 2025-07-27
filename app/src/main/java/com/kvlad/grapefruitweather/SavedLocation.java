package com.kvlad.grapefruitweather;

public class SavedLocation {
    private final int id;
    private final String name;
    private final String coordinates;
    private int orderNumber;

    public SavedLocation(int id, String name, String coordinates, int orderNumber) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.orderNumber = orderNumber;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }
}
