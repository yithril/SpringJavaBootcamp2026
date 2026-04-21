package org.example;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MultipleExceptions {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] numbers = {10, 20, 30};

        try{
            System.out.print("Enter your age: ");
            int age = scanner.nextInt(); //Maybe a problem?

            if(age < 1){
                System.out.println("Please type in a positive number.");
                System.exit(0);
            }

            System.out.print("Enter an index (0-2): ");
            int index = scanner.nextInt(); //Maybe a problem?

            int value = numbers[index]; //Possibly a problem

            int result = 100 / (value - age); //Possibly a problem

            System.out.println("Result: " + result);

            scanner.close();
        }
        catch(InputMismatchException ex){
            System.out.println("Please input an integer.");
        }
        catch(ArithmeticException ex){
            System.out.println("We can't divide by zero.");
        }
        catch(ArrayIndexOutOfBoundsException ex){
            System.out.println("You can select 0, 1, or 2");
        }
        catch(Exception ex){
            System.out.println("Something went wrong try again.");
        }
    }
}
