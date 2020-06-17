package scottxuan.cloud.web.base;


import scottxuan.cloud.base.result.IPageResult;
import scottxuan.cloud.base.result.IResult;
import scottxuan.cloud.base.result.ResultBo;
import scottxuan.cloud.web.result.PageResultDto;
import scottxuan.cloud.web.result.ResultDto;
import scottxuan.cloud.base.error.Error;

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

    protected ResultDto getFailedDto(Error error){
        return new ResultDto(error);
    }

    protected ResultDto getFailedDto(Error error, Object... args){
        return new ResultDto(error,args);
    }

}
