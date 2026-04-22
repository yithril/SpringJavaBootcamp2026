package org.example;

public class SplitExample {
    public static void main(String[] args) {
        String csvRow = "10|Dana Wyatt|52.5|12.50";
        //Let's break it up

        String[] csvItems = csvRow.split("\\|");

        System.out.println("ID: " + csvItems[0]);
        System.out.println("Name: " + csvItems[1]);
        System.out.println("Hours Worked: " + csvItems[2]);
        System.out.println("Pay Rate: " + csvItems[3]);
        System.out.println("-----------------");
    }
}
