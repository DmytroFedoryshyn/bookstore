package bookstore.mapper;

import bookstore.config.MapperConfig;
import bookstore.dto.BookResponseDto;
import bookstore.dto.CreateBookRequestDto;
import bookstore.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    @Mapping(target = "id", ignore = true)
    Book toBook(CreateBookRequestDto createBookRequestDto);

    BookResponseDto toBookDto(Book book);
}
