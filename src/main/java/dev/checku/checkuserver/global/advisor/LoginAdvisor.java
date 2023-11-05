package dev.checku.checkuserver.global.advisor;

import dev.checku.checkuserver.domain.portal.application.service.PortalLoginService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@RequiredArgsConstructor
public class LoginAdvisor {

    private final PortalLoginService portalLoginService;

    @Before("@annotation(dev.checku.checkuserver.global.advice.Login)")
    public void portalLogin(JoinPoint joinPoint) {
        String session = portalLoginService.login();
        HttpServletRequest servletRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        servletRequest.setAttribute("session", session);
    }

}
