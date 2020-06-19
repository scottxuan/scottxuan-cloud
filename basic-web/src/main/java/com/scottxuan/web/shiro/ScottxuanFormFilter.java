package com.scottxuan.web.shiro;

import com.scottxuan.base.error.ErrorCodes;
import com.scottxuan.base.utils.JSONUtils;
import com.scottxuan.web.result.ResultDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * @author : scottxuan
 */
@Slf4j
public class ScottxuanFormFilter extends FormAuthenticationFilter {
    @Override
    protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        ResultDto resultBody =new ResultDto(ErrorCodes.SYS_ERROR_401);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSONUtils.toJsonString(resultBody));
        response.flushBuffer();
    }
}
