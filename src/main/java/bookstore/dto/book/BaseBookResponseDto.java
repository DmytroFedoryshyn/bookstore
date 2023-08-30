package bookstore.dto.book;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class BaseBookResponseDto {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private BigDecimal price;
    private String description;
    private String coverImage;
}
