package com.scottxuan.core.page;

import com.scottxuan.base.page.PageParam;
import com.scottxuan.base.page.QueryParam;
import com.scottxuan.base.utils.ObjectUtils;
import com.scottxuan.core.page.PageQuery;
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
        Object[] args = point.getArgs();
        for (Object obj : args) {
            if (obj instanceof PageParam) {
                PageParam param = (PageParam) obj;
                if (ObjectUtils.isNotEmpty(param)) {
                    PageQuery.start(param);
                    break;
                }
            }
            if (obj instanceof QueryParam){
                QueryParam queryParam = (QueryParam) obj;
                if (ObjectUtils.isNotEmpty(queryParam)) {
                    PageParam param = queryParam.getPageParam();
                    if (ObjectUtils.isNotEmpty(param)) {
                        PageQuery.start(param);
                        break;
                    }
                }
            }
        }
        return point.proceed(args);
    }
}
