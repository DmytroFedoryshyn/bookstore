package bookstore.service;

import bookstore.dto.BookDto;
import bookstore.dto.BookSearchParametersDto;
import bookstore.dto.CreateBookRequestDto;
import bookstore.exception.EntityNotFoundException;
import bookstore.mapper.BookMapper;
import bookstore.model.Book;
import bookstore.repository.BookRepository;
import bookstore.repository.BookSpecificationBuilder;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookSpecificationBuilder bookSpecificationBuilder;

    public BookServiceImpl(BookRepository bookRepository, BookSpecificationBuilder bookSpecificationBuilder) {
        this.bookRepository = bookRepository;
        this.bookSpecificationBuilder = bookSpecificationBuilder;
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
        Optional<Book> optional = bookRepository.findById(id);
        if (optional.isEmpty()) {
            throw new EntityNotFoundException("Could not retrieve book by ID");
        }

        return BookMapper.INSTANCE.toBookDto(optional.get());
    }

    @Override
    public BookDto update(CreateBookRequestDto product, Long id) {
        Book book = BookMapper.INSTANCE.toBook(product);
        book.setId(id);
        return BookMapper.INSTANCE.toBookDto(bookRepository.save(book));
    }

    @Override
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public List<BookDto> search(BookSearchParametersDto searchParametersDto) {
        Specification<Book> bookSpecification = bookSpecificationBuilder.build(searchParametersDto);
        return bookRepository.findAll(bookSpecification)
                .stream()
                .map(BookMapper.INSTANCE::toBookDto).toList();
    }
}
