package com.scottxuan.core.page;

import com.scottxuan.base.page.PageParam;
import com.scottxuan.base.page.QueryParam;
import com.scottxuan.base.utils.ObjectUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author : scottxuan
 */
@Component
@Aspect
public class PageHelperAop {

    @Pointcut("@annotation(com.scottxuan.core.page.EnablePage)")
    private void pointCut() {
    }

    @Around("pointCut()")
    public Object doAround(ProceedingJoinPoint point) throws Throwable {
        PageParam param = null;
        Object[] args = point.getArgs();
        for (Object obj : args) {
            if (obj instanceof PageParam) {
                param = (PageParam) obj;
                break;
            }
            if (obj instanceof QueryParam){
                QueryParam queryParam = (QueryParam) obj;
                if (ObjectUtils.isNotEmpty(queryParam)) {
                    param = queryParam.getPageParam();
                    break;
                }
            }
        }
        if (ObjectUtils.isNotEmpty(param)) {
            PageQuery.start(param);
        }else{
            PageQuery.start(new PageParam(1,10));
        }
        return point.proceed(args);
    }
}
