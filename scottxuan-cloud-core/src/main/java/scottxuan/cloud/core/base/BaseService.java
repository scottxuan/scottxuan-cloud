package scottxuan.cloud.core.base;

import scottxuan.cloud.base.page.PageParam;
import scottxuan.cloud.base.result.ResultBo;
import scottxuan.cloud.core.page.PageResult;

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
