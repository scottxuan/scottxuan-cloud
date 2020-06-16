package scottxuan.cloud.base.result;

import scottxuan.cloud.base.error.Error;
/**
 * @author : zhaoxuan
 */
public interface IResult<T> {
    T getValue();

    Error getError();

    Object[] getArgs();

    boolean isSuccess();
}
