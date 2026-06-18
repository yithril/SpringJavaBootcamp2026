package com.example.vehicle_api.search;

public class VehicleSearchParams {
    private Double minPrice;
    private Double maxPrice;
    private String make;
    private String model;
    private Integer minYear;
    private Integer maxYear;
    private String color;
    private Integer minMileage;
    private Integer maxMileage;
    private String type;

    public boolean hasPrice() {
        return minPrice != null || maxPrice != null;
    }

    public boolean hasMakeModel() {
        return make != null || model != null;
    }

    public boolean hasYear() {
        return minYear != null || maxYear != null;
    }

    public boolean hasColor() {
        return color != null;
    }

    public boolean hasMileage() {
        return minMileage != null || maxMileage != null;
    }

    public boolean hasType() {
        return type != null;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getMinYear() {
        return minYear;
    }

    public void setMinYear(Integer minYear) {
        this.minYear = minYear;
    }

    public Integer getMaxYear() {
        return maxYear;
    }

    public void setMaxYear(Integer maxYear) {
        this.maxYear = maxYear;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getMinMileage() {
        return minMileage;
    }

    public void setMinMileage(Integer minMileage) {
        this.minMileage = minMileage;
    }

    public Integer getMaxMileage() {
        return maxMileage;
    }

    public void setMaxMileage(Integer maxMileage) {
        this.maxMileage = maxMileage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
