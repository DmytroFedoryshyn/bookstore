package bookstore.service;

import bookstore.dto.category.CategoryResponseDto;
import bookstore.dto.category.CreateCategoryRequestDto;
import bookstore.mapper.CategoryMapper;
import bookstore.model.Category;
import bookstore.repository.category.CategoryRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    @Override
    public List<CategoryResponseDto> findAll(Pageable pageable) {
        return repository.findAll(pageable)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponseDto getById(Long id) {
        return mapper.toDto(repository.getReferenceById(id));
    }

    @Override
    public CategoryResponseDto save(CreateCategoryRequestDto categoryDto) {
        return mapper.toDto(repository.save(mapper.toEntity(categoryDto)));
    }

    @Override
    public CategoryResponseDto update(Long id, CreateCategoryRequestDto categoryDto) {
        Category category = mapper.toEntity(categoryDto);
        category.setId(id);
        return mapper.toDto(repository.save(category));
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
