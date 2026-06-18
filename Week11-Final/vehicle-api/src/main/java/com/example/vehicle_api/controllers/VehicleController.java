package com.example.vehicle_api.controllers;

import com.example.vehicle_api.models.Vehicle;
import com.example.vehicle_api.search.VehicleSearchParams;
import com.example.vehicle_api.services.VehicleService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping
    public List<Vehicle> search(VehicleSearchParams params) {
        return vehicleService.search(params);
    }

    @GetMapping("/{vin}")
    public Vehicle getByVin(@PathVariable int vin) {
        return vehicleService.findByVin(vin);
    }

    @PostMapping
    public Vehicle create(@RequestBody Vehicle vehicle) {
        return vehicleService.save(vehicle);
    }

    @PutMapping("/{vin}")
    public Vehicle update(@PathVariable int vin, @RequestBody Vehicle vehicle) {
        return vehicleService.update(vin, vehicle);
    }

    @DeleteMapping("/{vin}")
    public void delete(@PathVariable int vin) {
        vehicleService.delete(vin);
    }
}
