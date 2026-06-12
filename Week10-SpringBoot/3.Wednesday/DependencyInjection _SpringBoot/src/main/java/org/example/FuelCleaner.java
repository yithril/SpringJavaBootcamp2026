package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FuelCleaner {
    @Autowired
    private OilFilter oilFilter;
    @Autowired
    private FuelSensor fuelSensor;

    FuelCleaner(OilFilter oilFilter, FuelSensor fuelSensor) {
        this.oilFilter = oilFilter; // Dependency passed in constructor
        this.fuelSensor = fuelSensor;
    }

    void cleanFuelSystem() {
        oilFilter.filterOil();
        System.out.println("Cleaning fuel system.");
    }
}
