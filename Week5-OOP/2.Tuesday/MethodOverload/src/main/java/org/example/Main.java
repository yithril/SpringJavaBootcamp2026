package org.example;

public class Main {
    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        double sum = calculator.add(2, 3, 10, 500, 100, 30203, 10, 2);
        System.out.println("The sum is " + sum);
    }
}