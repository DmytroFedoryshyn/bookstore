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
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    private BookMapper bookMapper;

    @Mock
    private BookSpecificationBuilder bookSpecificationBuilder;

    @Mock
    private SortParametersParsingUtil sortParametersParsingUtil;

    @Test
    @DisplayName("Save a new book successfully")
    public void saveBook_Success() {
        Book book = new Book();
        book.setTitle("Book title");
        book.setPrice(BigDecimal.TEN);
        book.setAuthor("Author");
        book.setDescription("desc");
        book.setCoverImage("image");
        book.setIsbn("12345");

        Long newId = 1L;

        Book savedBook = new Book();
        savedBook.setId(newId);
        savedBook.setTitle("Book title");
        savedBook.setPrice(BigDecimal.TEN);
        savedBook.setAuthor("Author");
        savedBook.setDescription("desc");
        savedBook.setCoverImage("image");
        savedBook.setIsbn("12345");


        CreateBookRequestDto dto = new CreateBookRequestDto();
        dto.setTitle("Book title");
        dto.setPrice(BigDecimal.TEN);
        dto.setAuthor("Author");
        dto.setDescription("desc");
        dto.setCoverImage("image");
        dto.setIsbn("12345");

        BookResponseDto responseDto = new BookResponseDto();
        responseDto.setId(1L);
        responseDto.setTitle("Book title");
        responseDto.setPrice(BigDecimal.TEN);
        responseDto.setAuthor("Author");
        responseDto.setDescription("desc");
        responseDto.setCoverImage("image");
        responseDto.setIsbn("12345");

        when(bookRepository.save(any(Book.class))).thenReturn(savedBook);
        when(bookMapper.toBook(any(CreateBookRequestDto.class))).thenReturn(book);
        when(bookMapper.toBookDto(any(Book.class))).thenReturn(responseDto);

        BookResponseDto savedDto = bookService.save(dto);

        assertEquals(newId, savedDto.getId());
    }

    @Test
    @DisplayName("Find all books returns non empty list")
    public void findAll_Returns_NonEmptyList() {
        String sortString = "title";

        when(sortParametersParsingUtil.parseSortOrder(sortString)).thenReturn(Sort.Order.asc(sortString));
        when(bookRepository.findAll(any(PageRequest.class)))
                .thenReturn(new PageImpl<>(Collections.singletonList(new Book())));
        when(bookMapper.toBookDto(any())).thenReturn(new BookResponseDto());

        List<BookResponseDto> result = bookService.findAll(1, 10, sortString);

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());
    }

    @Test
    @DisplayName("Get by existent id returns book")
    public void getBy_ExistentId_Returns_Book() {
        Long id = 1L;

        Book book = new Book();
        book.setId(id);
        book.setTitle("Book title");
        book.setPrice(BigDecimal.TEN);
        book.setAuthor("Author");
        book.setDescription("desc");
        book.setCoverImage("image");
        book.setIsbn("12345");
        when(bookRepository.findById(id)).thenReturn(Optional.of(book));

        BookResponseDto responseDto = new BookResponseDto();
        responseDto.setId(id);
        responseDto.setTitle("Book title");
        responseDto.setPrice(BigDecimal.TEN);
        responseDto.setAuthor("Author");
        responseDto.setDescription("desc");
        responseDto.setCoverImage("image");
        responseDto.setIsbn("12345");

        when(bookMapper.toBookDto(any(Book.class))).thenReturn(responseDto);

        assertEquals(responseDto, bookService.get(id));
    }

    @Test
    @DisplayName("Get by non existent id throws exception")
    public void getBy_NonExistentId_ThrowsException() {
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            bookService.get(3L);
        });
    }

    @Test
    @DisplayName("Update an existing book successfully")
    public void updateBook_Success() {
        Book existingBook = new Book();
        existingBook.setId(1L);
        existingBook.setTitle("Book title");
        existingBook.setPrice(BigDecimal.TEN);
        existingBook.setAuthor("Author");
        existingBook.setDescription("desc");
        existingBook.setCoverImage("image");
        existingBook.setIsbn("12345");

        Book updatedBook = new Book();
        updatedBook.setId(1L);
        updatedBook.setTitle("Updated Book title");
        updatedBook.setPrice(BigDecimal.valueOf(15.99));
        updatedBook.setAuthor("Updated Author");
        updatedBook.setDescription("Updated desc");
        updatedBook.setCoverImage("updated_image");
        updatedBook.setIsbn("54321");

        CreateBookRequestDto updateDto = new CreateBookRequestDto();
        updateDto.setTitle("Updated Book title");
        updateDto.setPrice(BigDecimal.valueOf(15.99));
        updateDto.setAuthor("Updated Author");
        updateDto.setDescription("Updated desc");
        updateDto.setCoverImage("updated_image");
        updateDto.setIsbn("54321");

        BookResponseDto updatedDto = new BookResponseDto();
        updatedDto.setId(1L);
        updatedDto.setTitle("Updated Book title");
        updatedDto.setPrice(BigDecimal.valueOf(15.99));
        updatedDto.setAuthor("Updated Author");
        updatedDto.setDescription("Updated desc");
        updatedDto.setCoverImage("updated_image");
        updatedDto.setIsbn("54321");

        when(bookRepository.save(any(Book.class))).thenReturn(updatedBook);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(existingBook));
        when(bookMapper.toBook(any(CreateBookRequestDto.class))).thenReturn(updatedBook);
        when(bookMapper.toBookDto(updatedBook)).thenReturn(updatedDto);

        BookResponseDto resultDto = bookService.update(updateDto, 1L);

        assertEquals(updatedDto, resultDto);

        Mockito.verify(bookRepository, Mockito.times(1)).save(any(Book.class));
        Mockito.verify(bookMapper, Mockito.times(1)).toBook(any(CreateBookRequestDto.class));
        Mockito.verify(bookMapper, Mockito.times(1)).toBookDto(updatedBook);
    }

    @Test
    @DisplayName("Delete an existing book successfully")
    public void deleteBook_Success() {
        bookService.delete(1L);

        Mockito.verify(bookRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    void search_ReturnsListOfBookResponseDto() {
        BookSearchParametersDto searchParametersDto = new BookSearchParametersDto();
        int page = 0;
        int size = 10;
        String sort = "title";
        Sort.Order sortOrder = new Sort.Order(Sort.Direction.ASC, "title");

        when(sortParametersParsingUtil.parseSortOrder(sort)).thenReturn(sortOrder);

        Specification<Book> mockSpecification = mock(Specification.class);
        when(bookSpecificationBuilder.build(searchParametersDto)).thenReturn(mockSpecification);

        Book book = new Book();
        book.setId(1L);
        book.setTitle("Book title");
        book.setPrice(BigDecimal.TEN);
        book.setAuthor("Author");
        book.setDescription("desc");
        book.setCoverImage("image");
        book.setIsbn("12345");

        List<Book> books = List.of(book);
        when(bookRepository.findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class))).thenReturn(new PageImpl<>(books));

        BookResponseDto responseDto = new BookResponseDto();
        responseDto.setId(1L);
        responseDto.setTitle("Book title");
        responseDto.setPrice(BigDecimal.TEN);
        responseDto.setAuthor("Author");
        responseDto.setDescription("desc");
        responseDto.setCoverImage("image");
        responseDto.setIsbn("12345");


        when(bookMapper.toBookDto(any())).thenReturn(responseDto);

        List<BookResponseDto> result = bookService.search(searchParametersDto, page, size, sort);

        assertEquals(books.size(), result.size());
    }


    @Test
    void findAllByCategories_Id_ReturnsListOfBaseBookResponseDto() {
        Long categoryId = 1L;
        int page = 0;
        int size = 10;
        String sort = "title";
        Sort.Order sortOrder = new Sort.Order(Sort.Direction.ASC, "title");

        when(sortParametersParsingUtil.parseSortOrder(sort)).thenReturn(sortOrder);

        Pageable pageable = PageRequest.of(0, 10, Sort.by(sortOrder));


        Book book = new Book();
        book.setId(1L);
        book.setTitle("Book title");
        book.setPrice(BigDecimal.TEN);
        book.setAuthor("Author");
        book.setDescription("desc");
        book.setCoverImage("image");
        book.setIsbn("12345");

        List<Book> books = List.of(book);
        when(bookRepository.findAllByCategoryId(categoryId, pageable)).thenReturn(books);

        BaseBookResponseDto responseDto = new BaseBookResponseDto();
        responseDto.setId(1L);
        responseDto.setTitle("Book title");
        responseDto.setPrice(BigDecimal.TEN);
        responseDto.setAuthor("Author");
        responseDto.setDescription("desc");
        responseDto.setCoverImage("image");
        responseDto.setIsbn("12345");

        List<BaseBookResponseDto> result = bookService.findAllByCategories_Id(categoryId, page, size, sort);

        assertEquals(books.size(), result.size());
    }
}