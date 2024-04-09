package kr.co.datastreams.llmetabe.global.utils.paging;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class Page {

    @Schema(description = "페이지", example = "0")
    private Integer page;

    @Schema(description = "사이즈", example = "20")
    private Integer size;

    @Schema(example = "DESC", description = "DESC / ASC")
    private Sort.Direction order;

    @Schema(example = "createDate", description = "정렬할 파라미터 명")
    private String sort;

    public PageRequest getPageable(Page page) {
        return PageRequest.of(page.getPage(), page.getSize(), page.getOrder(), page.getSort());
    }
}