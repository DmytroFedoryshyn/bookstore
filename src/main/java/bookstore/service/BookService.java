package bookstore.service;

import bookstore.dto.BookDto;
import bookstore.dto.BookSearchParametersDto;
import bookstore.dto.CreateBookRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto product);

    List<BookDto> findAll(Pageable pageable);

    BookDto get(Long id);

    BookDto update(CreateBookRequestDto product, Long id);

    void delete(Long id);

    List<BookDto> search(BookSearchParametersDto searchParametersDto, Pageable pageable);
}
