package bookstore.service;

import bookstore.dto.book.BaseBookResponseDto;
import bookstore.dto.book.BookResponseDto;
import bookstore.dto.book.BookSearchParametersDto;
import bookstore.dto.book.CreateBookRequestDto;
import java.util.List;

public interface BookService {
    BookResponseDto save(CreateBookRequestDto product);

    List<BookResponseDto> findAll(int page, int size, String sort);

    BookResponseDto get(Long id);

    BookResponseDto update(CreateBookRequestDto product, Long id);

    void delete(Long id);

    List<BookResponseDto> search(BookSearchParametersDto searchParametersDto,
                                 int page, int size, String sort);

    List<BaseBookResponseDto> findAllByCategories_Id(Long id, int page, int size, String sort);
}
