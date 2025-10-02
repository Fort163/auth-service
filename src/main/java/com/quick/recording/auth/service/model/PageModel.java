package com.quick.recording.auth.service.model;

import lombok.Data;

@Data
public abstract class PageModel {

    private Integer currentPage;
    private Integer nextPage;
    private Integer backPage;
    private String counterPage;
    private Integer pageSize;

    public PageModel() {
        this.pageSize = 10;
        this.currentPage = 1;
    }
}
