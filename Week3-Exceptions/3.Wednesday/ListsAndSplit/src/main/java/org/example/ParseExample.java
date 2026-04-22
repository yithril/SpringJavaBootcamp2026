package org.example;

public class ParseExample {
    public static void main(String[] args) {
        //Parse is a scary word that just means convert
        String stringAsNumber = "15";

        //convert from String to Integer
        int age = Integer.parseInt(stringAsNumber);

        double price = Double.parseDouble("16.7");

        boolean isStudent = Boolean.parseBoolean("true");
    }
}
