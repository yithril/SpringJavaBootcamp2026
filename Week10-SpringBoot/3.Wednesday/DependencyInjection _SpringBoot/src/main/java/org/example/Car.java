package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Car {
    @Autowired
    private Engine engine;

    Car(Engine engine) {
        this.engine = engine;
    }

    void start() {
        engine.performMaintenance();
        engine.start();
        System.out.println("Car started with " + engine.getClass().getSimpleName());
    }
}
