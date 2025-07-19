package com.kvlad.grapefruitweather;

public class SavedLocation {
    private final int id;
    private final String name;
    private final String coordinates;
    private String last_access_time;

    public SavedLocation(int id, String name, String coordinates, String last_access_time) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.last_access_time = last_access_time;
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

    public String getLast_access_time() {
        return last_access_time;
    }

    public void setLast_access_time(String last_access_time) {
        this.last_access_time = last_access_time;
    }
}
