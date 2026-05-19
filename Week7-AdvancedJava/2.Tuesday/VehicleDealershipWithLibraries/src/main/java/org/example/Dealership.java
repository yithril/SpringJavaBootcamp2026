package org.example;

import java.util.ArrayList;
import java.util.List;

public class Dealership {
    private String name;
    private String address;
    private String phone;
    private List<Vehicle> inventory = new ArrayList<>();

    public Dealership(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Vehicle> getVehiclesByPrice(double min, double max) {
        return inventory.stream()
                .filter(vehicle -> vehicle.getPrice() >= min && vehicle.getPrice() <= max)
                .toList();
    }

    public List<Vehicle> getVehiclesByMakeModel(String make, String model) {
        return inventory.stream()
                .filter(vehicle ->
                        vehicle.getMake().equalsIgnoreCase(make)
                                && vehicle.getModel().equalsIgnoreCase(model))
                .toList();
    }

    public List<Vehicle> getVehiclesByYear(int min, int max) {
        return inventory.stream()
                .filter(vehicle -> vehicle.getYear() >= min && vehicle.getYear() <= max)
                .toList();
    }

    public List<Vehicle> getVehiclesByColor(String color) {
        return inventory.stream()
                .filter(vehicle -> vehicle.getColor().equalsIgnoreCase(color))
                .toList();
    }

    public List<Vehicle> getVehiclesByMileage(int min, int max) {
        return inventory.stream()
                .filter(vehicle -> vehicle.getOdometer() >= min && vehicle.getOdometer() <= max)
                .toList();
    }

    public List<Vehicle> getVehiclesByType(VehicleType vehicleType) {
        return inventory.stream()
                .filter(vehicle -> vehicle.getVehicleType() == vehicleType)
                .toList();
    }

    public List<Vehicle> getAllVehicles() {
        return inventory;
    }

    public Vehicle getVehicleByVin(int vin){
        return this.inventory
                .stream()
                .filter(v -> v.getVin() == vin)
                .findFirst()
                .orElse(null);
    }

    public void addVehicle(Vehicle vehicle) {
        this.inventory.add(vehicle);
    }

    public void loadVehicles(List<Vehicle> vehicles){
        this.inventory = vehicles;
    }

    public void removeVehicle(Vehicle vehicle) {
        this.inventory.remove(vehicle);
    }
}
