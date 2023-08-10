package bookstore.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookSearchParametersDto {
    private String[] authors;
    private String[] titles;
}
