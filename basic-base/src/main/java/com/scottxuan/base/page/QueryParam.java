package com.scottxuan.base.page;

import com.google.common.collect.Maps;
import lombok.Getter;
import com.scottxuan.base.utils.ObjectUtils;

import java.util.Map;

/**
 * @author : scottxuan
 */
@Getter
public class QueryParam {
    private PageParam pageParam;
    private Map<String,Object> params = Maps.newHashMap();

    public QueryParam() {
    }

    public QueryParam(PageParam pageParam) {
        this.pageParam = pageParam;
    }

    public QueryParam(Integer pageIndex, Integer pageSize) {
        this.pageParam = new PageParam(pageIndex,pageSize);
    }

    public QueryParam(Integer pageIndex, Integer pageSize, String sort) {
        this.pageParam = new PageParam(pageIndex,pageSize,sort);
    }

    public QueryParam(Integer pageIndex, Integer pageSize, String sort, Boolean asc) {
        this.pageParam = new PageParam(pageIndex,pageSize,sort,asc);
    }

    public QueryParam put(String key, Object value){
        params.put(key,value);
        return this;
    }

    public QueryParam putIfNotEmpty(String key, Object value){
        if (ObjectUtils.isNotEmpty(value)) {
            params.put(key,value);
        }
        return this;
    }

    public Boolean isEmpty(){
        return params.size() == 0;
    }

    public Boolean isNotEmpty(){
        return !isEmpty();
    }
}
