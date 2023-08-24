package bookstore.util;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class SortParametersParsingUtil {
    public Sort.Order parseSortOrder(String sort) {
        String[] sortParams = sort.split(",");
        String sortField = sortParams[0];
        Sort.Order sortOrder = Sort.Order.asc(sortField);

        if (sortParams.length > 1) {
            String sortDirection = sortParams[1];
            if ("desc".equalsIgnoreCase(sortDirection)) {
                sortOrder = Sort.Order.desc(sortField);
            }
        }

        return sortOrder;
    }
}
