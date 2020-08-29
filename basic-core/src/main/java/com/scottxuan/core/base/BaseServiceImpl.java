package com.scottxuan.core.base;


import org.springframework.stereotype.Service;
import com.scottxuan.base.page.PageParam;
import com.scottxuan.base.result.ResultBo;
import com.scottxuan.core.page.PageQuery;
import com.scottxuan.core.page.PageResult;

import java.util.List;

/**
 * @author : scottxuan
 */
@Service
public abstract class BaseServiceImpl<T> implements BaseService<T>{

    /**
     * 获取dao操作的mapper
     * @return
     */
    protected abstract BaseMapper<T> getMapper();

    @Override
    public Integer insert(T t) {
        return getMapper().insert(t);
    }

    @Override
    public Integer insertSelective(T t) {
        return getMapper().insertSelective(t);
    }

    @Override
    public Integer insertUseGeneratedKeys(T t) {
        return getMapper().insertUseGeneratedKeys(t);
    }

    @Override
    public Integer insertBatch(List<? extends T> list) {
        return getMapper().insertList(list);
    }

    @Override
    public Integer deleteById(Object o) {
        return getMapper().deleteByPrimaryKey(o);
    }

    @Override
    public Integer update(T t) {
        return getMapper().updateByPrimaryKey(t);
    }

    @Override
    public Integer updateSelective(T t) {
        return getMapper().updateByPrimaryKeySelective(t);
    }

    @Override
    public List<T> selectAll() {
        return getMapper().selectAll();
    }

    @Override
    public List<T> selectByIds(List<?> ids) {
        return getMapper().selectByIds(ids);
    }

    @Override
    public ResultBo<T> selectById(Object o) {
        return ResultBo.of(getMapper().selectByPrimaryKey(o));
    }

    @Override
    public Integer selectCount(T t) {
        return getMapper().selectCount(t);
    }

    @Override
    public ResultBo<T> selectOne(T t) {
        return ResultBo.of(getMapper().selectOne(t));
    }

    @Override
    public List<T> select(T t) {
        return getMapper().select(t);
    }

    @Override
    public PageResult<T> selectPage(T t, PageParam pageParam) {
        return getMapper().selectPage(t,pageParam);
    }

    @Override
    public PageResult<T> selectPage(PageParam pageParam) {
        return getMapper().selectPage(null,pageParam);
    }

    public void toPageQuery(PageParam pageParam){
        PageQuery.start(pageParam);
    }

    public PageResult<T> toPageResult(List<T> list){
        return PageResult.of(list);
    }
}
