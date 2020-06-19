package com.scottxuan.web.result;

import lombok.Data;
import com.scottxuan.base.error.ErrorCodes;
import com.scottxuan.base.error.IError;
import com.scottxuan.base.result.ResultBo;
import com.scottxuan.web.i18n.I18nUtils;

import java.io.Serializable;

/**
 * @author : scottxuan
 */
@Data
public class ResultDto<T> implements Serializable {
    private static final long serialVersionUID = -3728530737570138336L;
    private int code;
    private String message;
    private T data;

    public ResultDto() {
        this.code = ErrorCodes.OPERATE_SUCCESS.getCode();
        this.message = I18nUtils.getMessage(ErrorCodes.OPERATE_SUCCESS.getMessage());
    }

    public ResultDto(T data) {
        this.code = ErrorCodes.OPERATE_SUCCESS.getCode();
        this.message = I18nUtils.getMessage(ErrorCodes.OPERATE_SUCCESS.getMessage());
        this.data = data;
    }

    public ResultDto(IError error) {
        this.code = error.getCode();
        this.message = I18nUtils.getMessage(error.getMessage());
    }

    public ResultDto(IError error, Object... args) {
        this.code = error.getCode();
        this.message = I18nUtils.getMessage(error.getMessage(), args);
    }

    public ResultDto(ResultBo<T> resultBo) {
        String message = I18nUtils.getMessage(resultBo.getError().getMessage(), resultBo.getArgs());
        this.data = resultBo.getValue();
        this.code = resultBo.getError().getCode();
        this.message = message;
        this.setData(resultBo.getValue());
    }

    protected void setError(IError error, Object... args) {
        this.code = error.getCode();
        this.message = I18nUtils.getMessage(error.getMessage(), args);
    }
}
