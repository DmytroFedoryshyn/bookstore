package bookstore.service;

import bookstore.dto.BookDto;
import bookstore.dto.CreateBookRequestDto;
import bookstore.exception.EntityNotFoundException;
import bookstore.mapper.BookMapper;
import bookstore.model.Book;
import bookstore.repository.BookRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public BookDto save(CreateBookRequestDto product) {
        return BookMapper.INSTANCE
                .toBookDto(bookRepository
                        .save(BookMapper.INSTANCE.toBook(product)));
    }

    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream()
                .map(BookMapper.INSTANCE::toBookDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookDto get(Long id) {
        Optional<Book> optional = bookRepository.get(id);
        if (optional.isEmpty()) {
            throw new EntityNotFoundException("Could not retrieve book by ID");
        }

        return BookMapper.INSTANCE.toBookDto(optional.get());
    }
}
