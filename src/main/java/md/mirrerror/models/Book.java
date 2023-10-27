package md.mirrerror.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "Book")
public class Book {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookId;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 60, message = "Name should be between 2 and 60 characters")
    @Column(name = "name")
    private String name;

    @NotEmpty(message = "Author name should not be empty")
    @Size(min = 2, max = 60, message = "Author name should be between 2 and 60 characters")
    @Column(name = "author")
    private String author;

    @Min(value = 0, message = "Year should be greater than 0")
    @Column(name = "year")
    private int year;

    @Column(name = "taken_on")
    @Temporal(value = TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate takenOn;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;

    public Book() {}

    public Book(int bookId, String name, String author, int year, LocalDate takenOn, Person owner) {
        this.bookId = bookId;
        this.name = name;
        this.author = author;
        this.year = year;
        this.takenOn = takenOn;
        this.owner = owner;
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

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public LocalDate getTakenOn() {
        return takenOn;
    }

    public void setTakenOn(LocalDate takenOn) {
        this.takenOn = takenOn;
    }

    public boolean isExpired() {
        return ChronoUnit.DAYS.between(takenOn, LocalDate.now()) > 10;
    }
}
