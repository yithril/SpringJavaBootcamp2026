package org.example;

import de.vandermeer.asciitable.AsciiTable;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import org.beryx.textio.TerminalProperties;
import org.beryx.textio.swing.SwingTextTerminal;

import java.awt.Font;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class UserInterface {
    private Dealership dealership;
    private final TextIO textIO = TextIoFactory.getTextIO();
    private final TextTerminal<?> terminal = textIO.getTextTerminal();

    public UserInterface() {
        configureWindow();
        configureTerminalColors();
        init();
    }

    private void configureWindow() {
        if (terminal instanceof SwingTextTerminal swingTerminal) {
            swingTerminal.getFrame().setSize(1600, 1000);
            swingTerminal.getFrame().setFont(new Font("Consolas", Font.PLAIN, 14));
        }
    }

    private void configureTerminalColors() {
        TerminalProperties<?> properties = terminal.getProperties();
        properties.setPromptColor("cyan");
        properties.setInputColor("yellow");
        properties.setInputBold(true);
    }

    private void init() {
        DealershipFileManager fileManager = new DealershipFileManager();
        this.dealership = fileManager.getDealership();

        if (this.dealership == null) {
            printError("ERROR: The dealership data file is missing or malformed.");
            printWarning("Please ensure 'vehicles.csv' exists and has the correct format.");
            printError("The application cannot continue.");
            System.exit(1);
        }
    }

    public void display() {
        Map<Integer, Runnable> menuActions = new LinkedHashMap<>();

        menuActions.put(1, this::processGetAllVehiclesRequest);
        menuActions.put(2, this::processGetByPriceRequest);
        menuActions.put(3, this::processGetByMakeModelRequest);
        menuActions.put(4, this::processGetByYearRequest);
        menuActions.put(5, this::processGetByColorRequest);
        menuActions.put(6, this::processGetByMileageRequest);
        menuActions.put(7, this::processGetByVehicleTypeRequest);
        menuActions.put(8, this::processAddVehicleRequest);
        menuActions.put(9, this::processRemoveVehicleRequest);
        menuActions.put(10, this::processSellVehicleRequest);
        menuActions.put(11, this::processLeaseVehicleRequest);
        menuActions.put(0, this::exitProgram);

        while (true) {
            displayMenu();

            int choice = textIO.newIntInputReader()
                    .withMinVal(0)
                    .withMaxVal(11)
                    .read("Choose an option");

            menuActions.get(choice).run();
        }
    }

    private void displayMenu() {
        printHeader("""
                
                ==================================================
                              WELCOME TO HOP MOTORS
                ==================================================
                """);

        terminal.printf("Dealership: %s%n", dealership.getName());
        terminal.printf("Address: %s%n%n", dealership.getAddress());

        terminal.println("""
                Select from the following options:

                0. Exit Program
                1. View all vehicles
                2. Search for vehicles by price
                3. Search for vehicles by make/model
                4. Search for vehicles by year
                5. Search for vehicles by color
                6. Search for vehicles by mileage
                7. Search for vehicles by vehicle type
                8. Add a vehicle
                9. Remove a vehicle
                10. Sell a vehicle
                11. Lease a vehicle
                """);
    }

    public void processGetByPriceRequest() {
        printSectionHeader("Search By Price");

        double minPrice = textIO.newDoubleInputReader()
                .withMinVal(0.0)
                .read("Minimum price");

        double maxPrice = textIO.newDoubleInputReader()
                .withMinVal(minPrice)
                .read("Maximum price");

        displayVehicles(dealership.getVehiclesByPrice(minPrice, maxPrice));
    }

    public void processGetByMakeModelRequest() {
        printSectionHeader("Search By Make/Model");

        String make = textIO.newStringInputReader()
                .read("Enter make");

        String model = textIO.newStringInputReader()
                .read("Enter model");

        displayVehicles(dealership.getVehiclesByMakeModel(make, model));
    }

    public void processGetByYearRequest() {
        printSectionHeader("Search By Year");

        int minYear = textIO.newIntInputReader()
                .withMinVal(1900)
                .read("Minimum year");

        int maxYear = textIO.newIntInputReader()
                .withMinVal(minYear)
                .read("Maximum year");

        displayVehicles(dealership.getVehiclesByYear(minYear, maxYear));
    }

    public void processGetByColorRequest() {
        printSectionHeader("Search By Color");

        String color = textIO.newStringInputReader()
                .read("Enter color");

        displayVehicles(dealership.getVehiclesByColor(color));
    }

    public void processGetByMileageRequest() {
        printSectionHeader("Search By Mileage");

        int minMileage = textIO.newIntInputReader()
                .withMinVal(0)
                .read("Minimum mileage");

        int maxMileage = textIO.newIntInputReader()
                .withMinVal(minMileage)
                .read("Maximum mileage");

        displayVehicles(dealership.getVehiclesByMileage(minMileage, maxMileage));
    }

    public void processGetByVehicleTypeRequest() {
        printSectionHeader("Search By Vehicle Type");

        VehicleType vehicleType = textIO.newEnumInputReader(VehicleType.class)
                .read("Choose vehicle type");

        displayVehicles(dealership.getVehiclesByType(vehicleType));
    }

    public void processGetAllVehiclesRequest() {
        printSectionHeader("All Vehicles");
        displayVehicles(dealership.getAllVehicles());
    }

    public void processAddVehicleRequest() {
        printSectionHeader("Add Vehicle");

        int vin = textIO.newIntInputReader()
                .withMinVal(1)
                .read("Enter VIN");

        int year = textIO.newIntInputReader()
                .withMinVal(1900)
                .read("Enter year");

        String make = textIO.newStringInputReader()
                .read("Enter make");

        String model = textIO.newStringInputReader()
                .read("Enter model");

        String color = textIO.newStringInputReader()
                .read("Enter color");

        VehicleType vehicleType = textIO.newEnumInputReader(VehicleType.class)
                .read("Choose vehicle type");

        int odometer = textIO.newIntInputReader()
                .withMinVal(0)
                .read("Enter odometer reading");

        double price = textIO.newDoubleInputReader()
                .withMinVal(0.0)
                .read("Enter price");

        Vehicle vehicle = new Vehicle(vin, year, make, model, color, vehicleType, odometer, price);
        dealership.addVehicle(vehicle);

        printSuccess("Vehicle added successfully!");
    }

    public void processRemoveVehicleRequest() {
        printSectionHeader("Remove Vehicle");

        Vehicle vehicleToRemove = findVehicleByVin();

        if (vehicleToRemove == null) {
            printError("Vehicle not found.");
            return;
        }

        dealership.removeVehicle(vehicleToRemove);
        printSuccess("Vehicle removed successfully!");
    }

    public void processSellVehicleRequest() {
        printSectionHeader("Sell Vehicle");

        Vehicle vehicle = findVehicleByVin();

        if (vehicle == null) {
            printError("Vehicle not found.");
            return;
        }

        displaySingleVehicle(vehicle);

        String date = textIO.newStringInputReader()
                .read("Contract date");

        String customerName = textIO.newStringInputReader()
                .read("Customer name");

        String customerEmail = textIO.newStringInputReader()
                .read("Customer email");

        boolean financed = textIO.newBooleanInputReader()
                .read("Will the customer finance this vehicle?");

        SalesContract contract = new SalesContract(
                date,
                customerName,
                customerEmail,
                vehicle,
                financed
        );

        displayContractSummary(contract);

        boolean confirm = textIO.newBooleanInputReader()
                .read("Save this sale contract?");

        if (!confirm) {
            printWarning("Sale cancelled.");
            return;
        }

        ContractFileManager contractFileManager = new ContractFileManager();
        contractFileManager.saveContract(contract);

        dealership.removeVehicle(vehicle);

        printSuccess("Sale contract saved.");
    }

    public void processLeaseVehicleRequest() {
        printSectionHeader("Lease Vehicle");

        Vehicle vehicle = findVehicleByVin();

        if (vehicle == null) {
            printError("Vehicle not found.");
            return;
        }

        displaySingleVehicle(vehicle);

        String date = textIO.newStringInputReader()
                .read("Contract date");

        String customerName = textIO.newStringInputReader()
                .read("Customer name");

        String customerEmail = textIO.newStringInputReader()
                .read("Customer email");

        LeaseContract contract = new LeaseContract(
                date,
                customerName,
                customerEmail,
                vehicle
        );

        displayContractSummary(contract);

        boolean confirm = textIO.newBooleanInputReader()
                .read("Save this lease contract?");

        if (!confirm) {
            printWarning("Lease cancelled.");
            return;
        }

        ContractFileManager contractFileManager = new ContractFileManager();
        contractFileManager.saveContract(contract);

        dealership.removeVehicle(vehicle);

        printSuccess("Lease contract saved.");
    }

    private Vehicle findVehicleByVin() {

        while (true) {

            String input = textIO.newStringInputReader()
                    .read("Enter VIN or type BACK");

            if (input.equalsIgnoreCase("BACK")) {
                return null;
            }

            try {

                int vin = Integer.parseInt(input);


                Vehicle vehicle = dealership.getVehicleByVin(vin);

                if (vehicle != null) {
                    return vehicle;
                }

                printWarning("Vehicle not found.");

            }
            catch (NumberFormatException ex) {
                printError("VIN must be a number.");
            }
        }
    }

    private void displayVehicles(List<Vehicle> vehicles) {
        if (vehicles.isEmpty()) {
            printWarning("No vehicles found.");
            return;
        }

        AsciiTable table = new AsciiTable();

        table.addRule();
        table.addRow("VIN", "Year", "Vehicle", "Type", "Miles", "Price");
        table.addRule();

        for (Vehicle vehicle : vehicles) {
            table.addRow(
                    vehicle.getVin(),
                    vehicle.getYear(),
                    vehicle.getMake() + " " + vehicle.getModel(),
                    vehicle.getVehicleType(),
                    String.format("%,d", vehicle.getOdometer()),
                    String.format("$%,.2f", vehicle.getPrice())
            );
            table.addRule();
        }

        terminal.println(table.render());
    }

    private void displaySingleVehicle(Vehicle vehicle) {
        AsciiTable table = new AsciiTable();

        table.addRule();
        table.addRow("VIN", "Year", "Vehicle", "Type", "Miles", "Price");
        table.addRule();

        table.addRow(
                vehicle.getVin(),
                vehicle.getYear(),
                vehicle.getMake() + " " + vehicle.getModel(),
                vehicle.getVehicleType(),
                String.format("%,d", vehicle.getOdometer()),
                String.format("$%,.2f", vehicle.getPrice())
        );

        table.addRule();

        terminal.println(table.render());
    }

    private void displayContractSummary(Contract contract) {
        AsciiTable table = new AsciiTable();

        table.addRule();
        table.addRow("Customer", "Email", "Vehicle", "Total", "Monthly");
        table.addRule();

        Vehicle vehicle = contract.getVehicleSold();

        table.addRow(
                contract.getCustomerName(),
                contract.getCustomerEmail(),
                vehicle.getMake() + " " + vehicle.getModel(),
                String.format("$%,.2f", contract.getTotalPrice()),
                String.format("$%,.2f", contract.getMonthlyPayment())
        );

        table.addRule();

        terminal.println(table.render());
    }

    private void exitProgram() {
        DealershipFileManager fileManager = new DealershipFileManager();
        fileManager.saveDealership(dealership);

        printSuccess("Dealership saved. Goodbye!");
        System.exit(0);
    }

    private void printHeader(String message) {
        TerminalProperties<?> properties = terminal.getProperties();
        properties.setPromptColor("cyan");
        terminal.println(message);
    }

    private void printSectionHeader(String title) {
        TerminalProperties<?> properties = terminal.getProperties();
        properties.setPromptColor("white");
        terminal.println("\n========== " + title + " ==========\n");
    }

    private void printSuccess(String message) {
        TerminalProperties<?> properties = terminal.getProperties();
        properties.setPromptColor("green");
        terminal.println(message);
    }

    private void printWarning(String message) {
        TerminalProperties<?> properties = terminal.getProperties();
        properties.setPromptColor("yellow");
        terminal.println(message);
    }

    private void printError(String message) {
        TerminalProperties<?> properties = terminal.getProperties();
        properties.setPromptColor("red");
        terminal.println(message);
    }
}