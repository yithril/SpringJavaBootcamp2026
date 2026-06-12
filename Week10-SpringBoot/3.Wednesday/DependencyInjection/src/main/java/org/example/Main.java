package org.example;

public class Main {
    public static void main(String[] args) {
        OilFilter oilFilter = new OilFilter();
        FuelSensor fuelSensor = new FuelSensor();
        FuelCleaner fuelCleaner = new FuelCleaner(oilFilter, fuelSensor);
        GasolineEngine engine = new GasolineEngine(fuelCleaner);
        Car car = new Car(engine);
        car.start();

        //instantiate an electric engine car

        BatteryHealthMonitor bhm = new BatteryHealthMonitor();
        BatteryChecker bc = new BatteryChecker(bhm);
        ElectricEngine electricEngine = new ElectricEngine(bc);
        Car ev = new Car(electricEngine);
        ev.start();
    }
}