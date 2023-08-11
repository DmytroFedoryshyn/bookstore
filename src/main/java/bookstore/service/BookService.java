package bookstore.service;

import bookstore.dto.BookDto;
import bookstore.dto.BookSearchParametersDto;
import bookstore.dto.CreateBookRequestDto;
import java.util.List;

public interface BookService {
    BookDto save(CreateBookRequestDto product);

    List<BookDto> findAll();

    BookDto get(Long id);

    BookDto update(CreateBookRequestDto product, Long id);

    void delete(Long id);

    List<BookDto> search(BookSearchParametersDto searchParametersDto);
}
