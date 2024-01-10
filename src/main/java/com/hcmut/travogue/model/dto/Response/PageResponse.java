package com.hcmut.travogue.model.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse<T> {
    private List<T> data;
    private int totalItems;
    private int currentPage;
    private int totalPages;

    public PageResponse(Page<T> page) {
        this.data = page.getContent();
        this.totalItems = (int) page.getTotalElements();
        this.currentPage = page.getPageable().getPageNumber();
        this.totalPages = page.getTotalPages();
    }
}
