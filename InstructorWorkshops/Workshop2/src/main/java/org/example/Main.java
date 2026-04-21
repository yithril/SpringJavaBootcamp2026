package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Book[] books = new Book[] {
                new Book(1, "9780140449136", "The Odyssey", false, null),
                new Book(2, "9780061120084", "To Kill a Mockingbird", true, "Alice"),
                new Book(3, "9780451524935", "1984", false, null),
                new Book(4, "9780307474278", "The Girl with the Dragon Tattoo", true, "Brian"),
                new Book(5, "9780743273565", "The Great Gatsby", false, null),
                new Book(6, "9780439139601", "Harry Potter and the Goblet of Fire", true, "Samantha"),
                new Book(7, "9780261103573", "The Lord of the Rings", false, null),
                new Book(8, "9780553386790", "A Game of Thrones", true, "David"),
                new Book(9, "9780307949486", "The Martian", false, null),
                new Book(10, "9780062316097", "Sapiens: A Brief History of Humankind", true, "Karen"),
                new Book(11, "9781594634024", "The Kite Runner", false, null),
                new Book(12, "9780385472579", "Zen and the Art of Motorcycle Maintenance", false, null),
                new Book(13, "9780143128540", "Thinking, Fast and Slow", true, "Michael"),
                new Book(14, "9780060850524", "Brave New World", false, null),
                new Book(15, "9781501128035", "It", true, "Jessica"),
                new Book(16, "9780307277671", "The Road", false, null),
                new Book(17, "9780345803481", "Fifty Shades of Grey", true, "Emily"),
                new Book(18, "9780316769488", "The Catcher in the Rye", false, null),
                new Book(19, "9780141439600", "Pride and Prejudice", false, null),
                new Book(20, "9780385732550", "Looking for Alaska", true, "Chris")
        };

        //menu that allows people to see available books
        //check books in
        //exit the program

        while(true){
            System.out.println("Welcome to my library");
            System.out.println("----------------------------------------");
            System.out.println("1. Available Books");
            System.out.println("2. Check In Book");
            System.out.println("3. Exit Program");
            System.out.println("Make your selection:");

            int userChoice = scanner.nextInt();

            switch(userChoice){
                case 1:
                    displayBooks(books);
                    //Let them go back if they want
                    //After showing available books, check out book.
                    break;
                case 2:
                    break;
                case 3:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Please select 1-3.");
                    break;
            }
        }
    }

    public static void displayBooks(Book[] books){
        for(Book book : books){
            if(!book.isCheckedOut()){
                System.out.println(book.getId() +". " + " " + book.getTitle());
            }
        }
    }
}