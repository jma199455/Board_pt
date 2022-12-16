package com.study.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggerInterceptor implements HandlerInterceptor{

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.debug("==================== Interceptor BEGIN ====================");
        log.debug("Request URI ===> " + request.getRequestURI());
        log.debug("===========================================================");
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
    
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.debug("==================== Interceptor END ======================");
        log.debug("modelAndView ===> " + modelAndView);
        log.debug("===========================================================");
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }
    
    
}
