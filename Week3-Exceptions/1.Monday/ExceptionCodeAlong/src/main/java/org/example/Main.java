package org.example;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("How old are you?");

        try{
            int age = scanner.nextInt();
            System.out.println("You are " + age + " years old.");
        }
        catch(InputMismatchException ex){
            System.out.println("Please enter an integer");
        }
    }
}