package org.example;

public class Main {
    public static void main(String[] args) {
        //while loops
        boolean dormammuHasHadEnough = false;
        int counter = 0;

        while(dormammuHasHadEnough == false){
            System.out.println("Dormammu I've come to bargain.");
            System.out.println("Dormammu destroys Dr. Strange in new and fun ways.");
            counter++;

            if(counter == 10){
                System.out.println("Dormammu and Dr. Strange make a deal to save earth");
                dormammuHasHadEnough = true;
            }
        }

        //for loops when you care about how many times
        for(int x = 0; x < 10; x++){
            System.out.println(x);
        }

        //we can even go backwards
        for(int y = 20; y > 0; y--){
            System.out.println(y);
        }
    }
}