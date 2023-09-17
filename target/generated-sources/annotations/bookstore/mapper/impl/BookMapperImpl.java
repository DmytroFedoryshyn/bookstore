package bookstore.mapper.impl;

import bookstore.dto.book.BaseBookResponseDto;
import bookstore.dto.book.BookResponseDto;
import bookstore.dto.book.CreateBookRequestDto;
import bookstore.mapper.BookMapper;
import bookstore.model.Book;
import bookstore.model.Category;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-17T22:16:13+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 20.0.1 (Oracle Corporation)"
)
@Component
public class BookMapperImpl implements BookMapper {

    @Override
    public Book toBook(CreateBookRequestDto createBookRequestDto) {
        if ( createBookRequestDto == null ) {
            return null;
        }

        Book book = new Book();

        Set<Category> set = mapLongsToCategories( createBookRequestDto.getCategories() );
        if ( set != null ) {
            book.setCategories( set );
        }
        if ( createBookRequestDto.getTitle() != null ) {
            book.setTitle( createBookRequestDto.getTitle() );
        }
        if ( createBookRequestDto.getAuthor() != null ) {
            book.setAuthor( createBookRequestDto.getAuthor() );
        }
        if ( createBookRequestDto.getIsbn() != null ) {
            book.setIsbn( createBookRequestDto.getIsbn() );
        }
        if ( createBookRequestDto.getPrice() != null ) {
            book.setPrice( createBookRequestDto.getPrice() );
        }
        if ( createBookRequestDto.getDescription() != null ) {
            book.setDescription( createBookRequestDto.getDescription() );
        }
        if ( createBookRequestDto.getCoverImage() != null ) {
            book.setCoverImage( createBookRequestDto.getCoverImage() );
        }

        return book;
    }

    @Override
    public BookResponseDto toBookDto(Book book) {
        if ( book == null ) {
            return null;
        }

        BookResponseDto bookResponseDto = new BookResponseDto();

        Set<Long> set = mapCategoriesToLongs( book.getCategories() );
        if ( set != null ) {
            bookResponseDto.setCategories( set );
        }
        if ( book.getId() != null ) {
            bookResponseDto.setId( book.getId() );
        }
        if ( book.getTitle() != null ) {
            bookResponseDto.setTitle( book.getTitle() );
        }
        if ( book.getAuthor() != null ) {
            bookResponseDto.setAuthor( book.getAuthor() );
        }
        if ( book.getIsbn() != null ) {
            bookResponseDto.setIsbn( book.getIsbn() );
        }
        if ( book.getPrice() != null ) {
            bookResponseDto.setPrice( book.getPrice() );
        }
        if ( book.getDescription() != null ) {
            bookResponseDto.setDescription( book.getDescription() );
        }
        if ( book.getCoverImage() != null ) {
            bookResponseDto.setCoverImage( book.getCoverImage() );
        }

        setCategoryIds( bookResponseDto, book );

        return bookResponseDto;
    }

    @Override
    public BaseBookResponseDto toDtoWithoutCategories(Book book) {
        if ( book == null ) {
            return null;
        }

        BaseBookResponseDto baseBookResponseDto = new BaseBookResponseDto();

        if ( book.getId() != null ) {
            baseBookResponseDto.setId( book.getId() );
        }
        if ( book.getTitle() != null ) {
            baseBookResponseDto.setTitle( book.getTitle() );
        }
        if ( book.getAuthor() != null ) {
            baseBookResponseDto.setAuthor( book.getAuthor() );
        }
        if ( book.getIsbn() != null ) {
            baseBookResponseDto.setIsbn( book.getIsbn() );
        }
        if ( book.getPrice() != null ) {
            baseBookResponseDto.setPrice( book.getPrice() );
        }
        if ( book.getDescription() != null ) {
            baseBookResponseDto.setDescription( book.getDescription() );
        }
        if ( book.getCoverImage() != null ) {
            baseBookResponseDto.setCoverImage( book.getCoverImage() );
        }

        return baseBookResponseDto;
    }
}
