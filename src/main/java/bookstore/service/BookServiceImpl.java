package bookstore.service;

import bookstore.dto.book.BaseBookResponseDto;
import bookstore.dto.book.BookResponseDto;
import bookstore.dto.book.BookSearchParametersDto;
import bookstore.dto.book.CreateBookRequestDto;
import bookstore.exception.EntityNotFoundException;
import bookstore.mapper.BookMapper;
import bookstore.model.Book;
import bookstore.repository.book.BookRepository;
import bookstore.repository.book.BookSpecificationBuilder;
import bookstore.util.SortParametersParsingUtil;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookSpecificationBuilder bookSpecificationBuilder;
    private final BookMapper bookMapper;
    private final SortParametersParsingUtil sortParametersParsingUtil;

    @Override
    public BookResponseDto save(CreateBookRequestDto product) {
        return bookMapper
                .toBookDto(bookRepository
                        .save(bookMapper.toBook(product)));
    }

    @Override
    public List<BookResponseDto> findAll(int page, int size, String sort) {
        Sort.Order sortOrder = sortParametersParsingUtil.parseSortOrder(sort);

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortOrder));
        return bookRepository.findAll(pageable).stream()
                .map(bookMapper::toBookDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookResponseDto get(Long id) {
        Optional<Book> optional = bookRepository.findById(id);
        if (optional.isEmpty()) {
            throw new EntityNotFoundException("Could not retrieve book by ID");
        }

        return bookMapper.toBookDto(optional.get());
    }

    @Override
    public BookResponseDto update(CreateBookRequestDto product, Long id) {
        Book book = bookMapper.toBook(product);
        book.setId(id);
        return bookMapper.toBookDto(bookRepository.save(book));
    }

    @Override
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public List<BookResponseDto> search(BookSearchParametersDto searchParametersDto,
                                        int page, int size, String sort) {
        Sort.Order sortOrder = sortParametersParsingUtil.parseSortOrder(sort);

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortOrder));
        Specification<Book> bookSpecification = bookSpecificationBuilder
                .build(searchParametersDto);
        return bookRepository.findAll(bookSpecification, pageable)
                .stream()
                .map(bookMapper::toBookDto).toList();
    }

    @Override
    public List<BaseBookResponseDto> findAllByCategories_Id(
            Long id, int page, int size, String sort) {
        Sort.Order sortOrder = sortParametersParsingUtil.parseSortOrder(sort);

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortOrder));
        return bookRepository.findAllByCategoryId(id, pageable)
                .stream()
                .map(bookMapper::toDtoWithoutCategories)
                .collect(Collectors.toList());
    }
}
