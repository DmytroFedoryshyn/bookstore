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
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookSpecificationBuilder bookSpecificationBuilder;
    private final BookMapper bookMapper;

    public BookServiceImpl(BookRepository bookRepository,
                           BookSpecificationBuilder bookSpecificationBuilder,
                           BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookSpecificationBuilder = bookSpecificationBuilder;
        this.bookMapper = bookMapper;
    }

    @Override
    public BookDto save(CreateBookRequestDto product) {
        return bookMapper
                .toBookDto(bookRepository
                        .save(bookMapper.toBook(product)));
    }

    @Override
    public List<BookDto> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable).stream()
                .map(bookMapper::toBookDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookDto get(Long id) {
        Optional<Book> optional = bookRepository.findById(id);
        if (optional.isEmpty()) {
            throw new EntityNotFoundException("Could not retrieve book by ID");
        }

        return bookMapper.toBookDto(optional.get());
    }

    @Override
    public BookDto update(CreateBookRequestDto product, Long id) {
        Book book = bookMapper.toBook(product);
        book.setId(id);
        return bookMapper.toBookDto(bookRepository.save(book));
    }

    @Override
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public List<BookDto> search(BookSearchParametersDto searchParametersDto, Pageable pageable) {
        Specification<Book> bookSpecification = bookSpecificationBuilder.build(searchParametersDto);
        return bookRepository.findAll(bookSpecification, pageable)
                .stream()
                .map(bookMapper::toBookDto).toList();
    }
}
