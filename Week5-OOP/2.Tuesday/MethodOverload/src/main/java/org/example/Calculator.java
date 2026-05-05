package org.example;

import java.util.Arrays;

public class Calculator {
    public double add(double a, double b){
        return a + b;
    }

    //method overloading
    //same NAME for the method, but different parameters
    //you can change the number of parameters or types of parameters
    public double add(double a, double b, double c){
        return a + b + c;
    }

    public double add(int a, int b){
        return a + b;
    }

    //... variable arguments or varargs or short
    public double add(int... a){
        return Arrays.stream(a).sum();
    }
}
