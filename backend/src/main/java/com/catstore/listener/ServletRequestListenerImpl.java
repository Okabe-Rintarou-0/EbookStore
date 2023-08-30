package com.catstore.listener;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;

@WebListener //thanks to http://stackoverflow.com/questions/20240591/websocket-httpsession-returns-null
public class ServletRequestListenerImpl implements ServletRequestListener {

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
//        System.out.println("requestDestroyed()");
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        HttpServletRequest servletRequest = (HttpServletRequest) sre.getServletRequest();
        System.out.println("requestInitialized() Current thread " + Thread.currentThread().getName() + " with session id " + servletRequest.getSession().getId());
    }

}
