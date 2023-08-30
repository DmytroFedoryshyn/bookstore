package bookstore.service;

import bookstore.dto.book.BookResponseDto;
import bookstore.dto.book.BaseBookResponseDto;
import bookstore.dto.book.BookSearchParametersDto;
import bookstore.dto.book.CreateBookRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookResponseDto save(CreateBookRequestDto product);

    List<BookResponseDto> findAll(Pageable pageable);

    BookResponseDto get(Long id);

    BookResponseDto update(CreateBookRequestDto product, Long id);

    void delete(Long id);

    List<BookResponseDto> search(BookSearchParametersDto searchParametersDto, Pageable pageable);

    List<BaseBookResponseDto> findAllByCategories_Id(Long id, Pageable pageable);
}
