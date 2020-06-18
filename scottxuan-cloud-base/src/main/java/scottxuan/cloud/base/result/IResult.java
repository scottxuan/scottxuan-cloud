package scottxuan.cloud.base.result;

import scottxuan.cloud.base.error.IError;
/**
 * @author : zhaoxuan
 */
public interface IResult<T> {
    T getValue();

    IError getError();

    Object[] getArgs();

    boolean isSuccess();
}
