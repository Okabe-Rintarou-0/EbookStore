package com.catstore.interceptor;

import com.catstore.utils.sessionUtils.SessionUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class RedisSessionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (SessionUtil.checkAuthority()) {
            System.out.println("Pass session check");
            return true;
        }
        response401(response);
        return false;
    }

    private void response401(HttpServletResponse response) throws Exception {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;	charset=utf-8");
        response.getWriter().print("401");
    }
}
