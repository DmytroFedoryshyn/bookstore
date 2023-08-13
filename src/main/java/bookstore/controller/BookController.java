package bookstore.controller;

import bookstore.dto.BookDto;
import bookstore.dto.BookSearchParametersDto;
import bookstore.dto.CreateBookRequestDto;
import bookstore.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
@Tag(name = "Book Controller", description = "Endpoints for managing books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @Operation(summary = "Get all books")
    @GetMapping
    public List<BookDto> getAll(
            @Parameter(description = "Page number (zero-based)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page", example = "10")
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Sort order and field", example = "title,asc")
            @RequestParam(defaultValue = "title,asc") String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return bookService.findAll(pageable);
    }

    @Operation(summary = "Get a book by ID")
    @GetMapping("/{id}")
    @ApiResponse(responseCode = "200", description = "Book found", content = {
            @Content(mediaType = "application/json", schema
                    = @Schema(implementation = BookDto.class),
                    examples = @ExampleObject(value = "{\"id\": 1, \"title\":"
                            + " \"Sample Book\", \"author\": \"John Doe\"}"))
    })
    @ApiResponse(responseCode = "404", description = "Book not found")
    public BookDto getById(@PathVariable Long id) {
        return bookService.get(id);
    }

    @Operation(summary = "Create a new book")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponse(responseCode = "201", description = "Book created", content = {
            @Content(mediaType = "application/json", schema =
            @Schema(implementation = BookDto.class),
                    examples = @ExampleObject(value = "{\"id\": 1, \"title\":"
                            + " \"Sample Book\", \"author\": \"John Doe\"}"))
    })
    public BookDto create(@Valid @RequestBody CreateBookRequestDto bookDto) {
        return bookService.save(bookDto);
    }

    @Operation(summary = "Update an existing book by ID")
    @PutMapping("/{id}")
    @ApiResponse(responseCode = "200", description = "Book updated", content = {
            @Content(mediaType = "application/json", schema =
            @Schema(implementation = BookDto.class),
                    examples = @ExampleObject(value = "{\"id\": 1,"
                            + " \"title\": \"Updated Book\", \"author\": \"Jane Smith\"}"))
    })
    @ApiResponse(responseCode = "404", description = "Book not found")
    public BookDto update(@Valid @RequestBody CreateBookRequestDto bookDto, @PathVariable Long id) {
        return bookService.update(bookDto, id);
    }

    @Operation(summary = "Delete a book by ID")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponse(responseCode = "204", description = "Book deleted")
    @ApiResponse(responseCode = "404", description = "Book not found")
    public void delete(@PathVariable Long id) {
        bookService.delete(id);
    }

    @Operation(summary = "Search for books by parameters")
    @GetMapping("/search")
    @ApiResponse(responseCode = "200", description = "Books found", content = {
            @Content(mediaType = "application/json", schema =
            @Schema(implementation = BookDto.class),
                    examples = @ExampleObject(value = "[{\"id\": 1,"
                            + " \"title\": \"Book 1\", \"author\": \"Author 1\"}]"))
    })
    public List<BookDto> searchBooks(
            @Parameter(description = "Search parameters for filtering books")
                    BookSearchParametersDto searchParameters,
            @Parameter(description = "Sort order and field", example = "title,asc")
            @RequestParam(defaultValue = "title,asc") String sort,
            @Parameter(description = "Page number (zero-based)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page", example = "10")
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return bookService.search(searchParameters, pageable);
    }
}
