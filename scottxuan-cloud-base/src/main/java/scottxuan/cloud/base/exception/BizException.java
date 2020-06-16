package scottxuan.cloud.base.exception;

import lombok.Getter;
import scottxuan.cloud.base.error.Error;

/**
 * @author : zhaoxuan
 */
@Getter
public class BizException extends RuntimeException{

    private int errorCode;
    private String errorMsgKey;
    private Object[] args;

    public BizException(Error error) {
        super(String.valueOf(error.getCode()));
        this.errorCode = error.getCode();
        this.errorMsgKey = error.getMessage();
    }

    public BizException(Error error,Object... args) {
        super(String.valueOf(error.getCode()));
        this.errorCode = error.getCode();
        this.errorMsgKey = error.getMessage();
        this.args = args;
    }

    @Override
    public String getMessage() {
        return this.errorMsgKey;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
