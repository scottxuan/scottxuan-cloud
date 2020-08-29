package com.scottxuan.base.page;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * @author : zhaoxuan
 */
@Getter
public class PageParam {
    public static final Integer MIN_PAGE_INDEX = 1;
    public static final Integer MIN_PAGE_SIZE = 1;
    public static final Integer MAX_PAGE_SIZE = 1000;

    private Integer pageIndex;
    private Integer pageSize;
    private String sort;
    private Boolean asc = Boolean.TRUE;

    public PageParam(Integer pageIndex, Integer pageSize) {
        if (pageIndex != null && pageSize != null) {
            if (pageIndex < MIN_PAGE_INDEX) {
                pageIndex = MIN_PAGE_INDEX;
            }
            if (pageSize < MIN_PAGE_SIZE) {
                pageSize = MIN_PAGE_INDEX;
            }
            if (pageSize > MAX_PAGE_SIZE) {
                pageSize = MAX_PAGE_SIZE;
            }
            this.pageIndex = pageIndex;
            this.pageSize = pageSize;
        }
    }

    public PageParam(Integer pageIndex, Integer pageSize, String sort) {
        this(pageIndex, pageSize);
        if (this.pageIndex!=null
                && this.pageSize!=null
                && StringUtils.isNotBlank(sort)) {
            this.sort = sort;
        }
    }

    public PageParam(Integer pageIndex, Integer pageSize, String sort, Boolean asc) {
        this(pageIndex, pageSize,sort);
        if (this.pageIndex!=null
                && this.pageSize!=null
                && StringUtils.isNotBlank(sort)) {
            this.asc = asc;
        }
    }
}
