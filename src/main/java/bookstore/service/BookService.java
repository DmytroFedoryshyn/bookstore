package bookstore.service;

import java.util.List;
import bookstore.model.Book;

public interface BookService {
    Book save(Book product);
    List<Book> findAll();
}
