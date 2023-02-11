package dev.checku.checkuserver.global.aop;

import com.google.common.base.Joiner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.stream.Collectors;

import static net.logstash.logback.argument.StructuredArguments.value;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class LogAspect {

//    @Around("within(dev.checku.checkuserver.domain..api..*) && !@annotation(dev.checku.checkuserver.global.advice.NoLogging)")
    @Around("within(dev.checku.checkuserver.domain..*) && !@annotation(dev.checku.checkuserver.global.advice.NoLogging)")
    public Object logging(ProceedingJoinPoint pjp) throws Throwable {
        String params = getRequestParams();
        long startAt = System.currentTimeMillis();
        Object result = pjp.proceed();
        long endAt = System.currentTimeMillis();

        log.info("{} took {} ms. (params: '{}')",
                value("method", pjp.getSignature().getName()), endAt - startAt, params);

        return result;
    }



    private String getRequestParams() {
        String params = "";
        RequestAttributes requestAttribute = RequestContextHolder.getRequestAttributes();

        if (requestAttribute != null) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

            Map<String, String[]> paramMap = request.getParameterMap();
            if (!paramMap.isEmpty()) {
                params = paramMapToString(paramMap);
            }
        }

        return params;
    }

    private String paramMapToString(Map<String, String[]> paramMap) {
        return paramMap.entrySet().stream()
                .map(entry -> String.format("%s -> (%s)",
                        entry.getKey(), Joiner.on(",").join(entry.getValue())))
                .collect(Collectors.joining(", "));
    }

}
