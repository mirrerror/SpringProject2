package md.mirrerror.services;

import md.mirrerror.models.Book;
import md.mirrerror.repositories.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class BooksService {

    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> findAll() {
        return booksRepository.findAll();
    }

    public Book findOne(int id) {
        return booksRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Book book) {
        book.setTakenOn(LocalDate.now());
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book bookToUpdate) {
        bookToUpdate.setBookId(id);
        booksRepository.save(bookToUpdate);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    public List<Book> findAllPaginated(int page, int itemsPerPage, boolean sortByYear) {
        return sortByYear
                ? booksRepository.findAll(PageRequest.of(page, itemsPerPage, Sort.by("year"))).getContent()
                : booksRepository.findAll(PageRequest.of(page, itemsPerPage)).getContent();
    }

    public List<Book> findAllSortedByYear() {
        return booksRepository.findAll(Sort.by("year"));
    }

}
