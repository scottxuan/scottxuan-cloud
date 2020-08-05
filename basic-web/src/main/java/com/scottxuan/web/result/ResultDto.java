package com.scottxuan.web.result;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.scottxuan.base.utils.ObjectUtils;
import com.scottxuan.base.error.ErrorCodes;
import com.scottxuan.base.error.IError;
import com.scottxuan.base.result.ResultBo;
import com.scottxuan.web.i18n.I18nUtils;
import lombok.Data;

import java.io.Serializable;
import java.util.function.Consumer;

/**
 * @author : scottxuan
 */
@Data
public class ResultDto<T> implements Serializable {
    private static final long serialVersionUID = -3728530737570138336L;
    private int code;
    private String message;
    private T data;
    @JsonIgnore
    private IError error;
    @JsonIgnore
    private Object[] args;

    public ResultDto() {
        this.code = ErrorCodes.OPERATE_SUCCESS.getCode();
        this.message = I18nUtils.getMessage(ErrorCodes.OPERATE_SUCCESS.getMessage());
        this.error = ErrorCodes.OPERATE_SUCCESS;
    }

    public ResultDto(T data) {
        this.code = ErrorCodes.OPERATE_SUCCESS.getCode();
        this.message = I18nUtils.getMessage(ErrorCodes.OPERATE_SUCCESS.getMessage());
        this.data = data;
        this.error = ErrorCodes.OPERATE_SUCCESS;
    }

    public ResultDto(IError error) {
        this.code = error.getCode();
        this.message = I18nUtils.getMessage(error.getMessage());
        this.error = error;
    }

    public ResultDto(IError error, Object... args) {
        this.code = error.getCode();
        this.message = I18nUtils.getMessage(error.getMessage(), args);
        this.error = error;
        this.args = args;
    }

    public ResultDto(ResultBo<T> resultBo) {
        this.code = resultBo.getError().getCode();
        this.message = I18nUtils.getMessage(resultBo.getError().getMessage(), resultBo.getArgs());
        this.data = resultBo.getValue();
        this.error = resultBo.getError();
    }

    public ResultDto(int code, String message) {
        this.code = code;
        this.message = message;
    }

    protected void setError(IError error, Object... args) {
        this.code = error.getCode();
        this.message = I18nUtils.getMessage(error.getMessage(), args);
        this.error = error;
        this.args = args;
    }

    @JsonIgnore
    public Boolean isSuccess() {
        return this.code == ErrorCodes.OPERATE_SUCCESS.getCode();
    }

    @JsonIgnore
    public Boolean isPresent() {
        return ObjectUtils.isNotEmpty(data);
    }

    public void ifPresent(Consumer<? super T> consumer) {
        if (isPresent()) {
            consumer.accept(data);
        }
    }
}
