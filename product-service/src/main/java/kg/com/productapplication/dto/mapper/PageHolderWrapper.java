package kg.com.productapplication.dto.mapper;


import kg.com.productapplication.dto.response.PageHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PageHolderWrapper {

    public <T> PageHolder<T> wrapPageHolder(Page<T> content) {
        return PageHolder.<T>builder()
                .content(content.getContent())
                .page(content.getNumber())
                .size(content.getSize())
                .totalPages(content.getTotalPages())
                .totalElements(content.getTotalElements())
                .hasNextPage(content.hasNext())
                .hasPreviousPage(content.hasPrevious())
                .build();
    }
}
