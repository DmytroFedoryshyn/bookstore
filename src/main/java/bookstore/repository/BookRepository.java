package bookstore.repository;

import java.util.List;
import bookstore.model.Book;

public interface BookRepository {
    Book save(Book product);
    List<Book> findAll();
}
