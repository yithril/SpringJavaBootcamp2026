package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BatteryChecker {
    @Autowired
    private BatteryHealthMonitor monitor;

    BatteryChecker(BatteryHealthMonitor monitor) {
        this.monitor = monitor; // Dependency passed in constructor
    }

    void checkBattery() {
        monitor.monitorBatteryHealth();
        System.out.println("Checking battery.");
    }
}
