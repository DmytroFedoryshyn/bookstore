package bookstore.mapper.impl;

import bookstore.dto.category.CategoryResponseDto;
import bookstore.dto.category.CreateCategoryRequestDto;
import bookstore.mapper.CategoryMapper;
import bookstore.model.Category;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-17T21:01:47+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 20.0.1 (Oracle Corporation)"
)
@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public CategoryResponseDto toDto(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryResponseDto categoryResponseDto = new CategoryResponseDto();

        if ( category.getId() != null ) {
            categoryResponseDto.setId( category.getId() );
        }
        if ( category.getName() != null ) {
            categoryResponseDto.setName( category.getName() );
        }
        if ( category.getDescription() != null ) {
            categoryResponseDto.setDescription( category.getDescription() );
        }

        return categoryResponseDto;
    }

    @Override
    public Category toEntity(CategoryResponseDto categoryDto) {
        if ( categoryDto == null ) {
            return null;
        }

        Category category = new Category();

        if ( categoryDto.getId() != null ) {
            category.setId( categoryDto.getId() );
        }
        if ( categoryDto.getName() != null ) {
            category.setName( categoryDto.getName() );
        }
        if ( categoryDto.getDescription() != null ) {
            category.setDescription( categoryDto.getDescription() );
        }

        return category;
    }

    @Override
    public Category toEntity(CreateCategoryRequestDto requestDto) {
        if ( requestDto == null ) {
            return null;
        }

        Category category = new Category();

        if ( requestDto.getName() != null ) {
            category.setName( requestDto.getName() );
        }
        if ( requestDto.getDescription() != null ) {
            category.setDescription( requestDto.getDescription() );
        }

        return category;
    }
}
