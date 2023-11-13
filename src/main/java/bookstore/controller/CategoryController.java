package bookstore.controller;

import bookstore.dto.book.BaseBookResponseDto;
import bookstore.dto.category.CategoryResponseDto;
import bookstore.dto.category.CreateCategoryRequestDto;
import bookstore.service.BookService;
import bookstore.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/api/categories")
@Tag(name = "Category Controller", description = "Endpoints for managing categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final BookService bookService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Create a new category")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponse(responseCode = "201", description = "Category created", content = {
            @Content(mediaType = "application/json", schema =
            @Schema(implementation = CategoryResponseDto.class),
                    examples = @ExampleObject(value = "{\"id\": 1, \"name\":"
                            + " \"Fiction\", \"description\": \"Fictional books\"}"))
    })
    public CategoryResponseDto createCategory(@Valid @RequestBody
                                                      CreateCategoryRequestDto categoryDto) {
        return categoryService.save(categoryDto);
    }

    @Operation(summary = "Get all categories")
    @GetMapping
    @ApiResponse(responseCode = "200", description = "List of categories", content = {
            @Content(mediaType = "application/json", schema =
            @Schema(implementation = CategoryResponseDto.class),
                    examples = @ExampleObject(value = "[{\"id\": 1, \"name\":"
                            + " \"Fiction\", \"description\": \"Fictional books\"}]"))
    })
    public List<CategoryResponseDto> getAllCategories(
            @Parameter(description = "Page number (zero-based)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page", example = "10")
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Sort order and fields", example = "name,desc")
            @RequestParam(defaultValue = "name,asc") String sort) {

        return categoryService.findAll(page, size, sort);
    }

    @Operation(summary = "Get a category by ID")
    @GetMapping("/{id}")
    @ApiResponse(responseCode = "200", description = "Category found", content = {
            @Content(mediaType = "application/json", schema =
            @Schema(implementation = CategoryResponseDto.class),
                    examples = @ExampleObject(value = "{\"id\": 1, \"name\": \"Fiction\","
                            + " \"description\": \"Fictional books\"}"))
    })
    @ApiResponse(responseCode = "404", description = "Category not found")
    public CategoryResponseDto getCategoryById(@PathVariable Long id) {
        return categoryService.getById(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Update a category by ID")
    @PutMapping("/{id}")
    @ApiResponse(responseCode = "200", description = "Category updated", content = {
            @Content(mediaType = "application/json", schema =
            @Schema(implementation = CategoryResponseDto.class),
                    examples = @ExampleObject(value = "{\"id\": 1, \"name\":"
                            + "\"Fiction\", \"description\": \"Updated description\"}"))
    })
    @ApiResponse(responseCode = "404", description = "Category not found")
    public CategoryResponseDto updateCategory(@PathVariable Long id,
                                              @Valid @RequestBody
                                                      CreateCategoryRequestDto categoryDto) {
        return categoryService.update(id, categoryDto);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Delete a category by ID")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponse(responseCode = "204", description = "Category deleted")
    @ApiResponse(responseCode = "404", description = "Category not found")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteById(id);
    }

    @Operation(summary = "Get all books by specific category")
    @GetMapping("/{id}/books")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(responseCode = "200", description = "Books found", content = {
            @Content(mediaType = "application/json", schema =
            @Schema(implementation = CategoryResponseDto.class),
                    examples = @ExampleObject(value = "{\"id\": 1, \"name\":"
                            + "\"Fiction\", \"description\": \"Description\"}"))
    })
    public List<BaseBookResponseDto> getBooksByCategoryId(
            @PathVariable Long id,
            @Parameter(description = "Page number (zero-based)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page", example = "10")
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Sort order and fields", example = "title,desc")
            @RequestParam(defaultValue = "title,desc") String sort) {
        return bookService.findAllByCategories_Id(id, page, size, sort);
    }
}
