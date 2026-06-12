package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class GasolineEngine implements Engine {
    @Autowired
    private FuelCleaner cleaner;

    GasolineEngine(FuelCleaner cleaner) {
        this.cleaner = cleaner; // Dependency passed in constructor
    }

    public void start() {
        System.out.println("Gasoline engine started.");
    }

    public void performMaintenance() {
        cleaner.cleanFuelSystem();
        System.out.println("Maintaining gasoline engine.");
    }
}
