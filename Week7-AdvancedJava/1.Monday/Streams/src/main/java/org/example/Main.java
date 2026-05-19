package org.example;


import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //Streams are a declarative way of working with collections
        //Start with a question of what kind of data you're looking for
        var books = BookData.getBooks();

        //I want a list of books written by Jane Austen
        var janeAustenBooks = books.stream()
                .filter(book -> book.getAuthor().equalsIgnoreCase("Jane Austen"))
                .toList();

        janeAustenBooks.forEach(book -> System.out.println(book.getTitle()));

        //I want a list of books written by J.R.R. Tolkien over 400 pages
        books.stream()
                .filter(x -> x.getPages() > 400 && x.getAuthor().equalsIgnoreCase("J.R.R. Tolkien"))
                .forEach(x -> System.out.println(x.getTitle()));

        //I want non-Fantasy books
        //!= not equals
        books.stream()
                .filter(x -> !x.getGenre().equalsIgnoreCase("Fantasy"))
                .forEach(x -> System.out.println(x.getTitle()));

        //MAP function
        //Show me a list of all book titles and nothing else
        books.stream()
                .map(y -> y.getTitle())
                .forEach(y -> System.out.println(y));

        //This is the same
        //The :: are called method reference
        books.stream()
                .map(Book::getTitle)
                .forEach(System.out::println);

        //What is the average page number of our books?
        var averagePage = books.stream()
                            .mapToInt(Book::getPages)
                            .average()
                            .orElse(0);

        System.out.println("Average page count of books: " + averagePage);

        //Average page size of Classic books
        var averageClassic = books.stream()
                .filter(x -> x.getGenre().equalsIgnoreCase("Classic"))
                .mapToInt(Book::getPages)
                .average()
                .orElse(0);
        //min max count sum
    }

}