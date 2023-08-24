package bookstore.service;

import bookstore.dto.BookResponseDto;
import bookstore.dto.BookSearchParametersDto;
import bookstore.dto.CreateBookRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookResponseDto save(CreateBookRequestDto product);

    List<BookResponseDto> findAll(Pageable pageable);

    BookResponseDto get(Long id);

    BookResponseDto update(CreateBookRequestDto product, Long id);

    void delete(Long id);

    List<BookResponseDto> search(BookSearchParametersDto searchParametersDto, Pageable pageable);
}
