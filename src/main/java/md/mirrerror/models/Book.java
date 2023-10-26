package md.mirrerror.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Book {

    private int bookId;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 60, message = "Name should be between 2 and 60 characters")
    private String name;

    @NotEmpty(message = "Author name should not be empty")
    @Size(min = 2, max = 60, message = "Author name should be between 2 and 60 characters")
    private String author;

    @Min(value = 0, message = "Year should be greater than 0")
    private int year;
    private int personId;

    public Book() {}

    public Book(int bookId, String name, String author, int year, int personId) {
        this.bookId = bookId;
        this.name = name;
        this.author = author;
        this.year = year;
        this.personId = personId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }
}
