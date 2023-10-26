package md.mirrerror.dao;

import md.mirrerror.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM Book", new BookMapper());
    }

    public Book show(int id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE book_id=?", new Object[]{id}, new BookMapper())
                .stream().findAny().orElse(null);
    }

    public List<Book> showPersonBooks(int personId) {
        return new ArrayList<>(jdbcTemplate.query("SELECT * FROM Book WHERE person_id=?", new Object[]{personId}, new BookMapper()));
    }

    public void save(Book book) {
        if(book.getPersonId() <= 0) jdbcTemplate.update("INSERT INTO Book (name, author, year, person_id) VALUES(?, ?, ?, ?)",
                book.getName(), book.getAuthor(), book.getYear(), null);
        else jdbcTemplate.update("INSERT INTO Book (name, author, year, person_id) VALUES(?, ?, ?, ?)",
                book.getName(), book.getAuthor(), book.getYear(), book.getPersonId());
    }

    public void update(int id, Book updatedBook) {
        if(updatedBook.getPersonId() <= 0) jdbcTemplate.update("UPDATE Book SET name=?, author=?, year=?, person_id=? WHERE book_id=?",
                updatedBook.getName(), updatedBook.getAuthor(), updatedBook.getYear(), null, id);
        else jdbcTemplate.update("UPDATE Book SET name=?, author=?, year=?, person_id=? WHERE book_id=?",
                updatedBook.getName(), updatedBook.getAuthor(), updatedBook.getYear(), updatedBook.getPersonId(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Book WHERE book_id=?", id);
    }

}
