package com.scottxuan.core.base;

import com.scottxuan.base.page.PageParam;
import com.scottxuan.base.result.ResultBo;
import com.scottxuan.core.page.PageResult;

import java.util.List;

/**
 * @author : scottxuan
 */
public interface BaseService<T> {
    Integer insert(T t);

    Integer insertSelective(T t);

    Integer insertUseGeneratedKeys(T t);

    Integer insertBatch(List<? extends T> list);

    Integer update(T t);

    Integer updateSelective(T t);

    List<T> selectAll();

    List<T> select(T c);

    List<T> selectByIds(List<?> ids);

    ResultBo<T> selectById(Object o);

    Integer selectCount(T t);

    ResultBo<T> selectOne(T t);

    PageResult<T> selectPage(T t, PageParam pageParam);

    PageResult<T> selectPage(PageParam pageParam);

    Integer deleteById(Object o);
}
