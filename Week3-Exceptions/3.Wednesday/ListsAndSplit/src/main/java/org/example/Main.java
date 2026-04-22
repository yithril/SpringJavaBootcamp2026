package org.example;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //Arrays are too restrictive. We need something more flexible.
        //Lists are like arrays BUT they can grow and shrink
        List<String> groceryList = new ArrayList<>();

        groceryList.add("Bananas");
        groceryList.add("Peanut Butter");
        groceryList.add("Orange Juice");

        groceryList.remove("Bananas");

        for(String item : groceryList){
            System.out.println(item);
        }

        System.out.println(groceryList.size());
    }
}