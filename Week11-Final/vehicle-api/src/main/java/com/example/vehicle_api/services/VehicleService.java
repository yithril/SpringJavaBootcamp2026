package com.example.vehicle_api.services;

import com.example.vehicle_api.models.Vehicle;
import com.example.vehicle_api.repositories.VehicleRepository;
import com.example.vehicle_api.search.VehicleSearchParams;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public List<Vehicle> findAll() {
        return vehicleRepository.findAll();
    }

    public Vehicle findByVin(int vin) {
        return vehicleRepository.findById(vin).orElse(null);
    }

    public Vehicle save(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    public Vehicle update(int vin, Vehicle vehicle) {
        vehicle.setVin(vin);
        return vehicleRepository.save(vehicle);
    }

    public void delete(int vin) {
        vehicleRepository.deleteById(vin);
    }

    public List<Vehicle> search(VehicleSearchParams params) {
        if (params.hasPrice()) {
            double min = params.getMinPrice() != null ? params.getMinPrice() : 0;
            double max = params.getMaxPrice() != null ? params.getMaxPrice() : Double.MAX_VALUE;
            return vehicleRepository.findByPriceBetween(min, max);
        } else if (params.hasMakeModel()) {
            return vehicleRepository.findByMakeAndModel(params.getMake(), params.getModel());
        } else if (params.hasYear()) {
            int min = params.getMinYear() != null ? params.getMinYear() : 0;
            int max = params.getMaxYear() != null ? params.getMaxYear() : Integer.MAX_VALUE;
            return vehicleRepository.findByYearBetween(min, max);
        } else if (params.hasColor()) {
            return vehicleRepository.findByColor(params.getColor());
        } else if (params.hasMileage()) {
            int min = params.getMinMileage() != null ? params.getMinMileage() : 0;
            int max = params.getMaxMileage() != null ? params.getMaxMileage() : Integer.MAX_VALUE;
            return vehicleRepository.findByOdometerBetween(min, max);
        } else if (params.hasType()) {
            return vehicleRepository.findByVehicleType(params.getType());
        } else {
            return vehicleRepository.findAll();
        }
    }
}
