package scottxuan.cloud.base.error;

import lombok.AllArgsConstructor;

import java.io.Serializable;

/**
 * @author : zhaoxuan
 */
@AllArgsConstructor
public class ErrorCode implements IError, Serializable {
    private static final long serialVersionUID = -8993917598936482649L;
    private int code;
    private String message;

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
