package com.scottxuan.web.shiro;

import com.scottxuan.base.error.ErrorCodes;
import com.scottxuan.base.exception.ExceptionUtils;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * @author : scottxuan
 */
public class ScottxuanPermissionsFilter extends PermissionsAuthorizationFilter {
    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {
        if (!super.isAccessAllowed(request, response, mappedValue)) {
            ExceptionUtils.throwException(ErrorCodes.SYS_ERROR_403);
        }
        return true;
    }
}
