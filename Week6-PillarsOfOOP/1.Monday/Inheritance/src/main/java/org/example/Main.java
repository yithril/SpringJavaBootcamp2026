package org.example;

public class Main {
    public static void main(String[] args) {
        Dog dog = new Dog();
        dog.setName("Fido");

        Cat cat = new Cat();
        cat.setName("Fluffy");

        dog.bark();
        cat.meow();
    }
}