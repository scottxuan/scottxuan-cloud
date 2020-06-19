package com.scottxuan.core.base;

import com.scottxuan.base.page.PageParam;
import com.scottxuan.base.result.ResultBo;
import com.scottxuan.core.page.PageResult;

import java.util.List;

/**
 * @author : scottxuan
 */
public interface BaseService<T> {
    /**
     * 全量添加
     *
     * @param t
     * @return
     */
    Integer insert(T t);

    /**
     * 对非null值添加
     *
     * @param t
     * @return
     */
    Integer insertSelective(T t);

    /**
     * 全量添加  返回主键id   主键必须为自增的
     *
     * @param t
     * @return
     */
    Integer insertUseGeneratedKeys(T t);

    /**
     * 批量添加  全量
     *
     * @param list
     * @return
     */
    Integer insertBatch(List<? extends T> list);

    /**
     * 全量更新
     *
     * @param t
     * @return
     */
    Integer update(T t);

    /**
     * 增量更新
     *
     * @param t
     * @return
     */
    Integer updateSelective(T t);

    /**
     * 查询所有
     *
     * @return
     */
    List<T> selectAll();

    /**
     * 根据entity参数查询  关系为=
     *
     * @param c
     * @return
     */
    List<T> select(T c);

    /**
     * 多id查询
     *
     * @param ids
     * @return
     */
    List<T> selectByIds(List<?> ids);

    /**
     * 主键查询
     *
     * @param o
     * @return
     */
    ResultBo<T> selectById(Object o);

    /**
     * 条件数量查询
     *
     * @param t
     * @return
     */
    Integer selectCount(T t);

    /**
     * 条件单个查询
     *
     * @param t
     * @return
     */
    ResultBo<T> selectOne(T t);

    /**
     * 条件分页查询
     *
     * @param t
     * @param pageParam
     * @return
     */
    PageResult<T> selectPage(T t, PageParam pageParam);

    /**
     * 全量数据分页查询
     *
     * @param pageParam
     * @return
     */
    PageResult<T> selectPage(PageParam pageParam);

    /**
     * 根据id删除
     *
     * @param o
     * @return
     */
    Integer deleteById(Object o);
}
