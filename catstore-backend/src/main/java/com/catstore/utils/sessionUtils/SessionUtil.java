package com.catstore.utils.sessionUtils;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtil {

    public static void setSession(JSONObject data) {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (servletRequestAttributes != null) {
            HttpServletRequest request = servletRequestAttributes.getRequest();
            HttpSession session = request.getSession();
            for (Object thisKey : data.keySet()) {
                String key = (String) thisKey;
                Object val = data.get(key);
                session.setAttribute(key, val);
            }
        }
    }

    public static boolean removeSession() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (servletRequestAttributes != null) {
            HttpServletRequest request = servletRequestAttributes.getRequest();
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
                return true;
            }
        }
        return false;
    }

    public static Boolean checkAuthority() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();
            HttpSession session = request.getSession(false);
            if (session != null) {
                Integer userIdentity = (Integer) session.getAttribute("userIdentity");
                return userIdentity != null && userIdentity >= 0;
            }
        }
        return false;
    }

    private static HttpSession getSession() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();
            return request.getSession(false);
        }
        return null;
    }

    public static Integer getUserId() {
        HttpSession session = getSession();
        if (session != null)
            return (Integer) session.getAttribute("userId");
        return null;
    }

    public static Integer getUserIdentity() {
        HttpSession session = getSession();
        if (session != null)
            return (Integer) session.getAttribute("userIdentity");
        return null;
    }

    public static JSONObject getAuthority() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();
            HttpSession session = request.getSession(false);
            if (session != null) {
                JSONObject authorityObject = new JSONObject();
                authorityObject.put("userId", session.getAttribute("userId"));
                authorityObject.put("userAccount", session.getAttribute("userAccount"));
                authorityObject.put("userIdentity", session.getAttribute("userIdentity"));
                return authorityObject;
            }
        }
        return null;
    }
}
