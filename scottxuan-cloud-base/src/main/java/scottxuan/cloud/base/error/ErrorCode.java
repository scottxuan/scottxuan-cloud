package scottxuan.cloud.base.error;

import lombok.AllArgsConstructor;

import java.io.Serializable;

/**
 * @author : zhaoxuan
 */
@AllArgsConstructor
public class ErrorCode implements Error,Serializable {
    private static final long serialVersionUID = -8993917598936482649L;
    private int code;
    private String message;

    public int getCode(){
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
