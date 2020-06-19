package com.scottxuan.web.base;


import com.scottxuan.base.result.IPageResult;
import com.scottxuan.base.result.IResult;
import com.scottxuan.base.result.ResultBo;
import com.scottxuan.web.result.PageResultDto;
import com.scottxuan.web.result.ResultDto;
import com.scottxuan.base.error.IError;

/**
 * @author : scottxuan
 */
public abstract class BaseController {
    protected ResultDto getResultDto(){
        return new ResultDto();
    }

    protected ResultDto getResultDto(Object data){
        if (data instanceof IPageResult) {
            return new PageResultDto((IPageResult)data);
        }else if(data instanceof IResult){
            return new ResultDto((ResultBo) data);
        }else if(data instanceof ResultDto){
            return (ResultDto)data;
        }else{
            return new ResultDto(data);
        }
    }

    protected ResultDto getFailedDto(IError error){
        return new ResultDto(error);
    }

    protected ResultDto getFailedDto(IError error, Object... args){
        return new ResultDto(error,args);
    }

}
