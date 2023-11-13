package bookstore.service;

import bookstore.dto.category.CategoryResponseDto;
import bookstore.dto.category.CreateCategoryRequestDto;
import bookstore.exception.EntityNotFoundException;
import bookstore.mapper.CategoryMapper;
import bookstore.model.Category;
import bookstore.repository.category.CategoryRepository;
import bookstore.service.CategoryServiceImpl;
import bookstore.util.SortParametersParsingUtil;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper mapper;

    @Mock
    private SortParametersParsingUtil sortParametersParsingUtil;

    @Test
    @DisplayName("Find all returns non empty list")
    public void findAll_ReturnsNonEmptyList() {
        int page = 0;
        int size = 10;
        String sort = "name";
        Sort.Order sortOrder = new Sort.Order(Sort.Direction.ASC, "name");

        when(sortParametersParsingUtil.parseSortOrder(sort)).thenReturn(sortOrder);

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortOrder));

        Category category = new Category();
        category.setId(1L);
        category.setName("Category 1");

        List<Category> categories = List.of(category);
        when(categoryRepository.findAll(pageable)).thenReturn(new PageImpl<>(categories));

        CategoryResponseDto responseDto = new CategoryResponseDto();
        responseDto.setId(1L);
        responseDto.setName("Category 1");

        when(mapper.toDto(any())).thenReturn(responseDto);

        List<CategoryResponseDto> result = categoryService.findAll(page, size, sort);

        assertEquals(categories.size(), result.size());
    }

    @Test
    @DisplayName("Save successfully")
    public void save_Success() {
        CreateCategoryRequestDto requestDto = new CreateCategoryRequestDto();
        requestDto.setName("New Category");

        CategoryResponseDto responseDto = new CategoryResponseDto();
        responseDto.setId(1L);
        responseDto.setName("New Category");

        when(categoryRepository.save(any())).thenReturn(new Category());
        when(mapper.toDto(any())).thenReturn(responseDto);

        CategoryResponseDto result = categoryService.save(requestDto);

        assertNotNull(result);
        assertEquals(responseDto.getName(), result.getName());
    }


    @Test
    @DisplayName("Update successfully")
    public void update_Success() {
        Long categoryId = 1L;
        CreateCategoryRequestDto requestDto = new CreateCategoryRequestDto();
        requestDto.setName("Updated Category");

        Category existingCategory = new Category();
        existingCategory.setId(categoryId);
        existingCategory.setName("Category");

        CategoryResponseDto responseDto = new CategoryResponseDto();
        responseDto.setId(categoryId);
        responseDto.setName("Updated Category");


        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.save(existingCategory)).thenReturn(existingCategory);
        when(mapper.toDto(existingCategory)).thenReturn(responseDto);
        when(mapper.toEntity(requestDto)).thenReturn(existingCategory);

        CategoryResponseDto result = categoryService.update(categoryId, requestDto);

        assertNotNull(result);
        assertEquals(responseDto.getName(), result.getName());
    }

    @Test
    @DisplayName("Delete successfully")
    public void deleteById_Success() {
        categoryService.deleteById(1L);

        Mockito.verify(categoryRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Get by existent id returns category")
    public void getByExistentId_ReturnsCategory() {
        Long categoryId = 1L;

        Category existingCategory = new Category();
        existingCategory.setId(categoryId);
        existingCategory.setName("Category");

        CategoryResponseDto responseDto = new CategoryResponseDto();
        responseDto.setId(categoryId);
        responseDto.setName("Category");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existingCategory));
        when(mapper.toDto(existingCategory)).thenReturn(responseDto);

        CategoryResponseDto result = categoryService.getById(categoryId);

        assertNotNull(result);
        assertEquals(responseDto.getName(), result.getName());
    }

    @Test
    @DisplayName("Get by non existent id throws exception")
    public void getByNonExistentId_ThrowsException() {
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            categoryService.getById(3L);
        });
    }
}
