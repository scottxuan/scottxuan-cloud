package com.scottxuan.web.result;


import lombok.Getter;
import com.scottxuan.base.page.Page;
import com.scottxuan.base.result.IPageResult;

/**
 * @author : scottxuan
 */
@Getter
public class PageResultDto extends ResultDto {
    private static final long serialVersionUID = 4756985726137202197L;
    private Page page;

    public PageResultDto(IPageResult<?> iPageResult) {
        if (iPageResult.isSuccess()) {
            this.page = iPageResult.getPage();
            super.setData(iPageResult.getValue());
        }else{
            super.setError(iPageResult.getError(),iPageResult.getArgs());
        }
    }
}
