package bookstore.mapper;

import bookstore.config.MapperConfig;
import bookstore.dto.book.BaseBookResponseDto;
import bookstore.dto.book.BookResponseDto;
import bookstore.dto.book.CreateBookRequestDto;
import bookstore.model.Book;
import bookstore.model.Category;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Mapper(config = MapperConfig.class)
@Component
public interface BookMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "categories",
            source = "createBookRequestDto.categories",
            qualifiedByName = "mapLongsToCategories")
    Book toBook(CreateBookRequestDto createBookRequestDto);

    @Mapping(target = "categories",
            source = "book.categories", qualifiedByName = "mapCategoriesToLongs")
    BookResponseDto toBookDto(Book book);

    BaseBookResponseDto toDtoWithoutCategories(Book book);

    @Named("mapLongsToCategories")
    default Set<Category> mapLongsToCategories(Set<Long> categoryIds) {
        return categoryIds.stream()
                .map(Category::new)
                .collect(Collectors.toSet());
    }

    @Named("mapCategoriesToLongs")
    default Set<Long> mapCategoriesToLongs(Set<Category> categories) {
        return categories.stream()
                .map(Category::getId)
                .collect(Collectors.toSet());
    }

    @AfterMapping
    default void setCategoryIds(@MappingTarget BookResponseDto bookDto, Book book) {
        if (book.getCategories() != null) {
            bookDto.setCategories(book.getCategories().stream()
                    .map(Category::getId)
                    .collect(Collectors.toSet()));
        }
    }

    @Named("bookFromId")
    default Book bookFromId(Long id) {
        Book book = new Book();
        book.setId(id);
        return book;
    }
}
