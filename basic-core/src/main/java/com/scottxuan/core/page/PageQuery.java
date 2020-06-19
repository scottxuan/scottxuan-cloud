package com.scottxuan.core.page;

import com.github.pagehelper.PageHelper;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import com.scottxuan.base.page.PageParam;

/**
 * @author : zhaoxuan
 */
@Getter
public class PageQuery{

    public static void start(Integer pageIndex, Integer pageSize) {
        PageHelper.startPage(pageIndex, pageSize);
    }

    public static void start(Integer pageIndex, Integer pageSize, String sort) {
        if (pageIndex < PageParam.MIN_PAGE_INDEX) {
            pageIndex = PageParam.MIN_PAGE_INDEX;
        }
        if (pageSize > PageParam.MAX_PAGE_SIZE) {
            pageSize = PageParam.MIN_PAGE_INDEX;
        }
        if (StringUtils.isNotEmpty(sort)) {
            PageHelper.startPage(pageIndex, pageSize, sort + " asc");
        }else{
            PageHelper.startPage(pageIndex, pageSize);
        }
    }

    public static void start(Integer pageIndex, Integer pageSize, String sort, Boolean asc) {
        if (pageIndex < PageParam.MIN_PAGE_INDEX) {
            pageIndex = PageParam.MIN_PAGE_INDEX;
        }
        if (pageSize > PageParam.MAX_PAGE_SIZE) {
            pageSize = PageParam.MIN_PAGE_INDEX;
        }
        if (StringUtils.isNotEmpty(sort)) {
            PageHelper.startPage(pageIndex, pageSize, sort + (asc ? " asc" : " desc"));
        }else{
            PageHelper.startPage(pageIndex, pageSize);
        }
    }

    public static void start(PageParam pageParam) {
        if (StringUtils.isEmpty(pageParam.getSort())) {
            PageHelper.startPage(pageParam.getPageIndex(), pageParam.getPageSize());
        }else{
            PageHelper.startPage(pageParam.getPageIndex(), pageParam.getPageSize(),pageParam.getSort() + (pageParam.getAsc() ? " asc" : " desc"));
        }
    }
}
