package bookstore.repository;

import bookstore.model.Book;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class BookRepositoryImpl extends AbstractRepository<Book> implements BookRepository {
    public BookRepositoryImpl(SessionFactory factory) {
        super(factory, Book.class);
    }
}
