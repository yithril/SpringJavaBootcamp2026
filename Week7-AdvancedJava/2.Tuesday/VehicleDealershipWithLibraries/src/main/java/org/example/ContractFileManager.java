package org.example;

import java.io.FileWriter;
import java.io.IOException;

public class ContractFileManager {
    private static final String FILE_PATH = "src/main/resources/contracts.csv";

    public void saveContract(Contract contract) {
        try (FileWriter writer = new FileWriter(FILE_PATH, true)) {
            writer.write(formatContract(contract));
            writer.write(System.lineSeparator());
        } catch (IOException ex) {
            System.out.println("Something went wrong saving the contract.");
            ex.printStackTrace();
        }
    }

    private String formatContract(Contract contract) {
        Vehicle vehicle = contract.getVehicleSold();

        String vehicleInfo = String.format("%s|%s|%s|%d|%d|%s|%s|%s|%s|%d|%.2f",
                contract.getDate(),
                contract.getCustomerName(),
                contract.getCustomerEmail(),
                vehicle.getVin(),
                vehicle.getYear(),
                vehicle.getMake(),
                vehicle.getModel(),
                vehicle.getVehicleType(),
                vehicle.getColor(),
                vehicle.getOdometer(),
                vehicle.getPrice()
        );

        if (contract instanceof SalesContract sale) {
            return String.format("SALE|%s|%.2f|%.2f|%.2f|%.2f|%s|%.2f",
                    vehicleInfo,
                    sale.getSalesTaxAmount(),
                    sale.getRecordingFee(),
                    sale.getProcessingFee(),
                    sale.getTotalPrice(),
                    sale.isFinanced() ? "YES" : "NO",
                    sale.getMonthlyPayment()
            );
        }

        if (contract instanceof LeaseContract lease) {
            return String.format("LEASE|%s|%.2f|%.2f|%.2f|%.2f",
                    vehicleInfo,
                    lease.getExpectedEndingValue(),
                    lease.getLeaseFee(),
                    lease.getTotalPrice(),
                    lease.getMonthlyPayment()
            );
        }

        throw new IllegalArgumentException("Unknown contract type.");
    }
}
