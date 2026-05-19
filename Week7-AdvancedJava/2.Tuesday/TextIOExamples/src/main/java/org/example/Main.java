package org.example;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import org.beryx.textio.TerminalProperties;
import org.beryx.textio.swing.SwingTextTerminal;

import java.awt.Font;
import java.util.List;

public class Main {

    private static final TextIO textIO = TextIoFactory.getTextIO();
    private static final TextTerminal<?> terminal = textIO.getTextTerminal();

    public static void main(String[] args) {

        configureWindow();
        configureColors();

        boolean running = true;

        while (running) {

            printHeader("""
                    
                    ==================================================
                                TEXT-IO DEMO APP
                    ==================================================
                    """);

            terminal.println("""
                    1. String Input
                    2. Integer Validation
                    3. Double Validation
                    4. Boolean Prompt
                    5. Enum Selection
                    6. Default Values
                    7. Email Validation
                    0. Exit
                    """);

            int choice = textIO.newIntInputReader()
                    .withMinVal(0)
                    .withMaxVal(7)
                    .read("Choose an option");

            switch (choice) {

                case 1:
                    stringInputExample();
                    break;

                case 2:
                    integerValidationExample();
                    break;

                case 3:
                    doubleValidationExample();
                    break;

                case 4:
                    booleanPromptExample();
                    break;

                case 5:
                    enumSelectionExample();
                    break;

                case 6:
                    defaultValueExample();
                    break;

                case 7:
                    emailValidationExample();
                    break;

                case 0:
                    running = false;
                    printSuccess("Goodbye!");
                    break;
            }
        }
    }

    private static void configureWindow() {

        if (terminal instanceof SwingTextTerminal swingTerminal) {

            swingTerminal.getFrame().setSize(1400, 900);

            swingTerminal.getFrame().setFont(
                    new Font("Consolas", Font.PLAIN, 14)
            );
        }
    }

    private static void configureColors() {

        TerminalProperties<?> properties = terminal.getProperties();

        properties.setPromptColor("cyan");
        properties.setInputColor("yellow");
        properties.setInputBold(true);
    }

    // Example 1 - String Input
    private static void stringInputExample() {

        printSectionHeader("String Input");

        String name = textIO.newStringInputReader()
                .read("Enter your name");

        terminal.println("Hello " + name + "!");
    }

    // Example 2 - Integer Validation
    private static void integerValidationExample() {

        printSectionHeader("Integer Validation");

        int age = textIO.newIntInputReader()
                .withMinVal(1)
                .withMaxVal(120)
                .read("Enter your age");

        terminal.println("Age: " + age);
    }

    // Example 3 - Double Validation
    private static void doubleValidationExample() {

        printSectionHeader("Double Validation");

        double salary = textIO.newDoubleInputReader()
                .withMinVal(0.0)
                .read("Enter your salary");

        terminal.println("Salary: $" + String.format("%,.2f", salary));
    }

    // Example 4 - Boolean Prompt
    private static void booleanPromptExample() {

        printSectionHeader("Boolean Prompt");

        boolean likesJava = textIO.newBooleanInputReader()
                .read("Do you like Java?");

        if (likesJava) {
            printSuccess("Excellent choice.");
        }
        else {
            printWarning("We'll work on that.");
        }
    }

    // Example 5 - Enum Selection
    private static void enumSelectionExample() {

        printSectionHeader("Enum Selection");

        CoffeeSize size = textIO.newEnumInputReader(CoffeeSize.class)
                .read("Choose a coffee size");

        terminal.println("You selected: " + size);
    }

    // Example 6 - Default Values
    private static void defaultValueExample() {

        printSectionHeader("Default Values");

        String city = textIO.newStringInputReader()
                .withDefaultValue("Detroit")
                .read("Enter your city");

        terminal.println("City: " + city);
    }

    // Example 7 - Pattern Validation
    private static void emailValidationExample() {

        printSectionHeader("Email Validation");

        String email = textIO.newStringInputReader()
                .withValueChecker((value, itemName) -> {
                    if (value.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                        return null;
                    }

                    return List.of("Please enter a valid email address.");
                })
                .read("Enter email");

        terminal.println("Email accepted: " + email);
    }

    private static void printHeader(String message) {

        TerminalProperties<?> properties = terminal.getProperties();

        properties.setPromptColor("cyan");

        terminal.println(message);
    }

    private static void printSectionHeader(String title) {

        TerminalProperties<?> properties = terminal.getProperties();

        properties.setPromptColor("white");

        terminal.println("\n========== " + title + " ==========\n");
    }

    private static void printSuccess(String message) {

        TerminalProperties<?> properties = terminal.getProperties();

        properties.setPromptColor("green");

        terminal.println(message);
    }

    private static void printWarning(String message) {

        TerminalProperties<?> properties = terminal.getProperties();

        properties.setPromptColor("yellow");

        terminal.println(message);
    }
}

enum CoffeeSize {
    SMALL,
    MEDIUM,
    LARGE
}