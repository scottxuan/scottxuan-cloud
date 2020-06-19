package com.scottxuan.base.page;

import lombok.Getter;

/**
 * @author : zhaoxuan
 */
@Getter
public class PageParam {
    public static final Integer MIN_PAGE_INDEX = 1;
    public static final Integer MAX_PAGE_SIZE = 10000;

    private Integer pageIndex;
    private Integer pageSize;
    private String sort;
    private Boolean asc = Boolean.TRUE;

    public PageParam(Integer pageIndex, Integer pageSize) {
        if (pageIndex < MIN_PAGE_INDEX) {
            this.pageIndex = MIN_PAGE_INDEX;
        } else if (pageSize > MAX_PAGE_SIZE) {
            this.pageSize = MIN_PAGE_INDEX;
        } else {
            this.pageIndex = pageIndex;
            this.pageSize = pageSize;
        }
    }

    public PageParam(Integer pageIndex, Integer pageSize, String sort) {
        if (pageIndex < MIN_PAGE_INDEX) {
            this.pageIndex = MIN_PAGE_INDEX;
        } else if (pageSize > MAX_PAGE_SIZE) {
            this.pageSize = MIN_PAGE_INDEX;
        } else {
            this.pageIndex = pageIndex;
            this.pageSize = pageSize;
        }
        this.sort = sort;
    }

    public PageParam(Integer pageIndex, Integer pageSize, String sort, Boolean asc) {
        if (pageIndex < MIN_PAGE_INDEX) {
            this.pageIndex = MIN_PAGE_INDEX;
        } else if (pageSize > MAX_PAGE_SIZE) {
            this.pageSize = MIN_PAGE_INDEX;
        } else {
            this.pageIndex = pageIndex;
            this.pageSize = pageSize;
        }
        this.sort = sort;
        this.asc = asc;
    }
}
