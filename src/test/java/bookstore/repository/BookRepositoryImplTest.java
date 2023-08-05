package bookstore.repository;

import java.math.BigDecimal;
import java.util.List;
import bookstore.AbstractTest;
import bookstore.model.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BookRepositoryImplTest extends AbstractTest {
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        bookRepository = new BookRepositoryImpl(getSessionFactory());
    }

    @Override
    protected Class<?>[] entities() {
        return new Class[]{Book.class};
    }

    @Test
    void saveBook_Ok() {
        Book book = new Book();
        book.setTitle("Kobzar");
        book.setAuthor("Taras Shevchenko");
        book.setDescription("a very good book");
        book.setIsbn("123-567-890");
        book.setPrice(BigDecimal.valueOf(300.50));
        book.setCoverImage("http://example.com");
        bookRepository.save(book);

        Assertions.assertNotNull(book.getId());
        Assertions.assertEquals(1, book.getId());
    }

    @Test
    void findAllBooks_Ok() {
        Book book1 = new Book();
        book1.setTitle("Book 1");
        book1.setAuthor("Author 1");
        book1.setDescription("Description 1");
        book1.setIsbn("123-456-789");
        book1.setPrice(BigDecimal.valueOf(20.99));
        book1.setCoverImage("http://example.com/1");

        Book book2 = new Book();
        book2.setTitle("Book 2");
        book2.setAuthor("Author 2");
        book2.setDescription("Description 2");
        book2.setIsbn("987-654-321");
        book2.setPrice(BigDecimal.valueOf(15.50));
        book2.setCoverImage("http://example.com/2");

        bookRepository.save(book1);
        bookRepository.save(book2);

        List<Book> books = bookRepository.findAll();
        Assertions.assertEquals(books.size(), 2);
    }
}
