package com.intuit.profilevalidationsystem.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

import static com.intuit.profilevalidationsystem.constants.Properties.REQUEST_ID;

@Slf4j
@Component
public class ControllerInterceptor extends HandlerInterceptorAdapter
{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
    {
        if(request.getRequestURL().indexOf("/profile") != -1) {
            UUID trackingId = UUID.randomUUID();
            MDC.put(REQUEST_ID, trackingId.toString());
            log.info("Requested URL : {} ; Added tracking id in MDC : {}", request.getRequestURL(), trackingId);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView)
    {
        MDC.clear(); //clearing to prevent retaining info between requests
    }
}
