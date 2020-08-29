package com.scottxuan.core.base;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import com.scottxuan.base.page.PageParam;
import com.scottxuan.core.page.PageQuery;
import com.scottxuan.core.page.PageResult;
import tk.mybatis.mapper.common.ids.SelectByIdsMapper;
import tk.mybatis.mapper.common.special.InsertListMapper;
import tk.mybatis.mapper.common.special.InsertUseGeneratedKeysMapper;

import java.util.List;

/**
 * @author : scottxuan
 */
public interface BaseMapper<T> extends tk.mybatis.mapper.common.BaseMapper<T>, InsertListMapper<T>, InsertUseGeneratedKeysMapper<T>, SelectByIdsMapper<T> {

    /**
     * 分页查询
     *
     * @param t         属性规则 =
     * @param pageParam
     * @return
     */
    default PageResult<T> selectPage(T t, PageParam pageParam) {
        PageQuery.start(pageParam);
        return PageResult.of(select(t));
    }

    /**
     * 多id查询
     *
     * @param ids
     * @return
     */
    default List<T> selectByIds(List<?> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return Lists.newArrayList();
        }
        StringBuilder join = new StringBuilder();
        if (ids.get(0) instanceof String) {
            join.append("'");
            join.append(StringUtils.join(ids, "','"));
            join.append("'");
        } else if (ids.get(0) instanceof Integer) {
            join.append(StringUtils.join(ids, ","));
        } else {
            return Lists.newArrayList();
        }
        return selectByIds(join.toString());
    }
}
