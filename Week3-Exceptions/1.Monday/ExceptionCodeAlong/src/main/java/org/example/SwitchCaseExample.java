package org.example;

import java.util.Scanner;

public class SwitchCaseExample {
    public static void main(String[] args) {
        //switch case is VERY similar to if statements
        //BEST used when you have a set menu of options
        Scanner scanner = new Scanner(System.in);

        System.out.println("What's your sign?");
        String sign = scanner.nextLine();

        sign = sign.toLowerCase();

        //switch case
        switch(sign){
            case "pisces":
                System.out.println("You enjoy the water");
                break;
            case "cancer":
                System.out.println("The crab sign.");
                break;
            case "taurus":
                System.out.println("You are stubborn.");
                break;
            default:
                System.out.println("That is not a sign bro.");
                break;
        }

    }
}
