package bookstore.mapper;

import bookstore.config.MapperConfig;
import bookstore.dto.category.CategoryResponseDto;
import bookstore.dto.category.CreateCategoryRequestDto;
import bookstore.model.Category;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface CategoryMapper {
    CategoryResponseDto toDto(Category category);

    Category toEntity(CategoryResponseDto categoryDto);

    Category toEntity(CreateCategoryRequestDto requestDto);
}
