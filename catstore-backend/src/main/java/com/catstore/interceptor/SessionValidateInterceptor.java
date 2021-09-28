package com.catstore.interceptor;


import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.catstore.annotation.SkipSessionCheck;
import com.catstore.model.Message;
import com.catstore.utils.messageUtils.MessageUtil;
import com.catstore.utils.sessionUtils.SessionUtil;
import net.sf.json.JSONObject;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class SessionValidateInterceptor extends HandlerInterceptorAdapter {
    public SessionValidateInterceptor() {
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        //判断如果请求的类是swagger的控制器，直接通行。
        if (handlerMethod.getBean().getClass().getName().equals("springfox.documentation.swagger.web.ApiResourceController")) {
            return true;
        }
        Method method = handlerMethod.getMethod();
        if (method.isAnnotationPresent(SkipSessionCheck.class)) {
            return true;
        }
        boolean status = SessionUtil.checkAuthority();
        if (!status) {
            System.out.println("Session Validation Check Failed");
            Message message = MessageUtil.createMessage(MessageUtil.NOT_LOGIN_CODE, MessageUtil.NOT_LOGIN_MSG);
            this.sendJsonBack(response, message);
            return false;
        } else {
            System.out.println("Session Validation Check Succeed");
            return true;
        }
    }

    private void sendJsonBack(HttpServletResponse response, Message message) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try (PrintWriter writer = response.getWriter()) {
            writer.print(JSONObject.fromObject(message));
        } catch (IOException e) {
            System.out.println("send json back error");
        }
    }
}

