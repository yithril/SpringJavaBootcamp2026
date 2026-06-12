package org.example;

import org.springframework.stereotype.Component;

@Component
public class BatteryHealthMonitor {
    void monitorBatteryHealth() {
        System.out.println("Monitoring battery health.");
    }
}
