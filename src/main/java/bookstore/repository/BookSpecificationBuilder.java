package bookstore.repository;

import bookstore.dto.BookSearchParametersDto;
import bookstore.model.Book;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class BookSpecificationBuilder implements SpecificationBuilder<Book> {
    private final SpecificationProviderManager<Book> specificationProviderManager;

    public BookSpecificationBuilder(SpecificationProviderManager<Book>
                                            specificationProviderManager) {
        this.specificationProviderManager = specificationProviderManager;
    }

    @Override
    public Specification<Book> build(BookSearchParametersDto searchParametersDto) {
        Specification<Book> specification = Specification.where(null);
        if (searchParametersDto.getAuthors() != null
                && searchParametersDto.getAuthors().length > 0) {
            specification =
                    specification.and(specificationProviderManager
                            .getSpecificationProvider("author")
                            .getSpecification(searchParametersDto.getAuthors()));
        }

        if (searchParametersDto.getTitles() != null
                && searchParametersDto.getTitles().length > 0) {
            specification =
                    specification.and(specificationProviderManager
                            .getSpecificationProvider("title")
                            .getSpecification(searchParametersDto.getTitles()));
        }

        return specification;
    }
}
