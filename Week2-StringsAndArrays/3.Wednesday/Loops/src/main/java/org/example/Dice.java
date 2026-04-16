package org.example;

public class Dice {
    public int roll(){
        int result =  (int)(Math.random() * 10) + 1;
        return result;
    }
}
