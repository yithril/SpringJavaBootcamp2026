package org.example;

import java.util.List;

public class BookData {
    public static List<Book> getBooks() {
        return List.of(
                new Book("The Hobbit", "J.R.R. Tolkien", "Fantasy", 1937, 4.8, 310),
                new Book("The Fellowship of the Ring", "J.R.R. Tolkien", "Fantasy", 1954, 4.9, 423),
                new Book("The Two Towers", "J.R.R. Tolkien", "Fantasy", 1954, 4.8, 352),
                new Book("The Return of the King", "J.R.R. Tolkien", "Fantasy", 1955, 4.9, 416),

                new Book("Harry Potter and the Sorcerer's Stone", "J.K. Rowling", "Fantasy", 1997, 4.7, 309),
                new Book("Harry Potter and the Chamber of Secrets", "J.K. Rowling", "Fantasy", 1998, 4.6, 341),
                new Book("Harry Potter and the Prisoner of Azkaban", "J.K. Rowling", "Fantasy", 1999, 4.8, 435),

                new Book("The Martian", "Andy Weir", "Science Fiction", 2011, 4.7, 369),
                new Book("Project Hail Mary", "Andy Weir", "Science Fiction", 2021, 4.8, 496),

                new Book("Dune", "Frank Herbert", "Science Fiction", 1965, 4.6, 412),
                new Book("Dune Messiah", "Frank Herbert", "Science Fiction", 1969, 4.1, 256),
                new Book("Children of Dune", "Frank Herbert", "Science Fiction", 1976, 4.3, 444),

                new Book("To Kill a Mockingbird", "Harper Lee", "Classic", 1960, 4.8, 281),
                new Book("Go Set a Watchman", "Harper Lee", "Classic", 2015, 3.5, 278),

                new Book("Pride and Prejudice", "Jane Austen", "Classic", 1813, 4.5, 279),
                new Book("Emma", "Jane Austen", "Classic", 1815, 4.2, 474),

                new Book("The Lightning Thief", "Rick Riordan", "Fantasy", 2005, 4.4, 377),
                new Book("The Sea of Monsters", "Rick Riordan", "Fantasy", 2006, 4.3, 279),
                new Book("The Titan's Curse", "Rick Riordan", "Fantasy", 2007, 4.5, 312),

                new Book("Clean Code", "Robert C. Martin", "Programming", 2008, 4.4, 464),
                new Book("Clean Architecture", "Robert C. Martin", "Programming", 2017, 4.3, 432),

                new Book("The Pragmatic Programmer", "Andrew Hunt", "Programming", 1999, 4.7, 352),
                new Book("Head First Java", "Kathy Sierra", "Programming", 2003, 4.5, 720),

                new Book("1984", "George Orwell", "Dystopian", 1949, 4.7, 328),
                new Book("Animal Farm", "George Orwell", "Dystopian", 1945, 4.6, 112)
        );
    }
}
