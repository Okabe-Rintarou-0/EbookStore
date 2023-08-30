package com.catstore.utils.logUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogUtil {
    public static void error(String s){
        log.error(s);
    }

    public static void error(String format, Object obj){
        log.error(format, obj);
    }

    public static void error(String format, Object... objs) {
        log.error(format, objs);
    }

    public static void debug(String s) {
        log.debug(s);
    }

    public static void debug(String format, Object obj) {
        log.debug(format, obj);
    }

    public static void debug(String format, Object... objs) {
        log.debug(format, objs);
    }

    public static void warn(String s) {
        log.warn(s);
    }

    public static void warn(String format, Object obj) {
        log.warn(format, obj);
    }

    public static void warn(String format, Object... objs) {
        log.warn(format, objs);
    }

    public static void info(String s) {
        log.info(s);
    }

    public static void info(String format, Object obj) {
        log.info(format, obj);
    }

    public static void info(String format, Object... objs) {
        log.info(format, objs);
    }

    public static void trace(String s) {
        log.trace(s);
    }

    public static void trace(String format, Object obj) {
        log.trace(format, obj);
    }

    public static void trace(String format, Object... objs) {
        log.trace(format, objs);
    }

    public static void main(String[] args) {
        log.info("Test logger");
    }
}
