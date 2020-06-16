package scottxuan.cloud.base.exception;


/**
 * @author : zhaoxuan
 */
public class ExceptionUtils {

    public static void throwException(Error error) {
        throw new BizException(error);
    }

    public static void throwException(Error error, Object... args) {
        if (args.length > 0) {
            throw new BizException(error, args);
        } else {
            throw new BizException(error);
        }
    }
}
