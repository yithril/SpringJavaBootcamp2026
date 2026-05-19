package org.example;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;

public class DealershipFileManager {
    private static final String FILE_PATH = "src/main/resources/vehicles.csv";

    public Dealership getDealership() {
        Dealership dealership = new Dealership("Hop Motors", "123 Wherever St.", "555-1234");

        try {
            List<Vehicle> vehicles = new CsvToBeanBuilder<Vehicle>(new FileReader(FILE_PATH))
                    .withType(Vehicle.class)
                    .withSeparator('|')
                    .build()
                    .parse();

            dealership.loadVehicles(vehicles);

        } catch (Exception ex) {
            System.out.println("Something went wrong loading vehicles.");
            ex.printStackTrace();
        }

        return dealership;
    }

    public void saveDealership(Dealership dealership) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            new StatefulBeanToCsvBuilder<Vehicle>(writer)
                    .withApplyQuotesToAll(false)
                    .build()
                    .write(dealership.getAllVehicles());

        } catch (Exception ex) {
            System.out.println("Something went wrong saving vehicles.");
            ex.printStackTrace();
        }
    }
}
