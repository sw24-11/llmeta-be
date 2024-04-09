package kr.co.datastreams.llmetabe.global.utils.paging;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class Paging {
    private long totalElements;
    private int totalPages;
    private int numberOfElements;
    private boolean first;
    private List<?> content;

    public static Paging of(Page<?> page) {

        List<?> content = page.getContent();
        long totalElements = page.getTotalElements();
        int totalPages = page.getTotalPages();
        int numberOfElements = page.getNumberOfElements();
        boolean first = page.isFirst();

        return Paging.builder()
                .content(content)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .numberOfElements(numberOfElements)
                .first(first)
                .build();
    }

    public static OrderSpecifier<?> getOrder(PathBuilder<?> expression, Sort sort) {
        List<OrderSpecifier<?>> orders = new ArrayList<>();

        sort.stream().forEach(order -> {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String prop = order.getProperty();

            orders.add(new OrderSpecifier(direction, expression.get(prop)));
        });

        return orders.isEmpty() ? null : orders.get(0);
    }

}