package scottxuan.cloud.web.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;
import scottxuan.cloud.base.error.ErrorCode;
import scottxuan.cloud.base.error.ErrorCodes;
import scottxuan.cloud.base.exception.BizException;
import scottxuan.cloud.web.i18n.I18nUtils;
import scottxuan.cloud.web.result.ResultDto;

import java.util.Iterator;

/**
 * @author : scottxuan
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理自定义业务异常
     */
    @ExceptionHandler(value = BizException.class)
    public ResultDto bizExceptionHandler(BizException bizException) {
        return new ResultDto(new ErrorCode(bizException.getErrorCode(),bizException.getErrorMsgKey()),bizException.getArgs());
    }

    /***
     * 404处理
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public Object notFountHandler(NoHandlerFoundException e){
        return new ResultDto(ErrorCodes.SYS_ERROR_404);
    }


    /***
     * SpringMvc 参数解析异常
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Object notFountHandler(MethodArgumentTypeMismatchException e){
        return new ResultDto(ErrorCodes.ARGS_FORMAT_ERROR,e.getName());
    }

    /**
     * 文件异常
     */
    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    public ResultDto bizExceptionHandler(MaxUploadSizeExceededException exception) {
        return new ResultDto(ErrorCodes.FILE_EXCEED_MAX_SIZE);
    }

    /**
     * 处理自定义业务异常
     */
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public ResultDto bizExceptionHandler(MissingServletRequestParameterException exception) {
        return new ResultDto(ErrorCodes.ARGS_NOT_LEGAL, exception.getParameterName());
    }

    /**
     * 处理数据校验异常
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResultDto bizExceptionHandler(MethodArgumentNotValidException exception) {
        Iterator var2 = exception.getBindingResult().getAllErrors().iterator();
        while (var2.hasNext()) {
            ObjectError error = (ObjectError) var2.next();
            String message = I18nUtils.getMessage(error.getDefaultMessage());
            if (message.equals(I18nUtils.MESSAGE_NOT_FOUND)) {
                DefaultMessageSourceResolvable resolvables = (DefaultMessageSourceResolvable)error.getArguments()[0];
                message = resolvables.getDefaultMessage() + error.getDefaultMessage();
            }
            return new ResultDto(ErrorCodes.ERROR_COMMON,message);
        }
        return new ResultDto(ErrorCodes.ERROR_COMMON,exception.getMessage());
    }

    /**
     * 处理其他异常
     */
    @ExceptionHandler(value = Exception.class)
    public ResultDto exceptionHandler(Exception exception) {
        log.error("系统异常:{}", exception.getMessage(), exception);
        return new ResultDto(ErrorCodes.SYS_ERROR_500);
    }
}
