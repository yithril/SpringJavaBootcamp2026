package org.example;

public class Book {
    private String title;
    private String author;
    private String genre;
    private int yearPublished;
    private double rating;
    private int pages;

    public Book(String title, String author, String genre, int yearPublished, double rating, int pages) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.yearPublished = yearPublished;
        this.rating = rating;
        this.pages = pages;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getYearPublished() {
        return yearPublished;
    }

    public void setYearPublished(int yearPublished) {
        this.yearPublished = yearPublished;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }
}
