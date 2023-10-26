package md.mirrerror.dao;

import md.mirrerror.models.Book;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Book(
                rs.getInt("book_id"),
                rs.getString("name"),
                rs.getString("author"),
                rs.getInt("year"),
                rs.getInt("person_id")
        );
    }
}