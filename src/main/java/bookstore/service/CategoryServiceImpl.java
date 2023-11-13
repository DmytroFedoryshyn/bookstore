package bookstore.service;

import bookstore.dto.category.CategoryResponseDto;
import bookstore.dto.category.CreateCategoryRequestDto;
import bookstore.exception.EntityNotFoundException;
import bookstore.mapper.CategoryMapper;
import bookstore.model.Category;
import bookstore.repository.category.CategoryRepository;
import bookstore.util.SortParametersParsingUtil;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repository;
    private final CategoryMapper mapper;
    private final SortParametersParsingUtil sortParametersParsingUtil;

    @Override
    public List<CategoryResponseDto> findAll(int page, int size, String sort) {
        Sort.Order sortOrder = sortParametersParsingUtil.parseSortOrder(sort);

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortOrder));
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
        Category dbCategory = repository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Category not found with ID: " + id));
        Category category = mapper.toEntity(categoryDto);
        return mapper.toDto(repository.save(category));
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
