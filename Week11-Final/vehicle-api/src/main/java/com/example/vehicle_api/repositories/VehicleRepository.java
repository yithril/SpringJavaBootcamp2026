package com.example.vehicle_api.repositories;

import com.example.vehicle_api.models.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

    List<Vehicle> findByPriceBetween(double min, double max);

    List<Vehicle> findByMakeAndModel(String make, String model);

    List<Vehicle> findByYearBetween(int min, int max);

    List<Vehicle> findByColor(String color);

    List<Vehicle> findByOdometerBetween(int min, int max);

    List<Vehicle> findByVehicleType(String vehicleType);
}
