package scottxuan.cloud.base.result;

import scottxuan.cloud.base.page.Page;

/**
 * @author : zhaoxuan
 */
public interface IPageResult<T> extends IResult<T> {
    Page getPage();
}
