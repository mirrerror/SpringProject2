package md.mirrerror.repositories;

import md.mirrerror.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
    List<Book> findByNameStartingWith(String startingWith);
}
