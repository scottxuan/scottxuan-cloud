package com.scottxuan.base.result;

import com.scottxuan.base.page.Page;

/**
 * @author : zhaoxuan
 */
public interface IPageResult<T> extends IResult<T> {
    Page getPage();
}
