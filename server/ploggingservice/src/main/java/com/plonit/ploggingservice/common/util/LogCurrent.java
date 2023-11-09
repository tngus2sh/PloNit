package com.plonit.ploggingservice.common.util;

public class LogCurrent {

    public static final String START = "Start";
    public static final String END = "End";

    public static String getClassName() {
        String classFullName = Thread.currentThread().getStackTrace()[2].getClassName();
        return classFullName.substring(classFullName.lastIndexOf(".") + 1);
    }

    public static String getMethodName() {
        return Thread.currentThread().getStackTrace()[2].getMethodName();
    }

    public static String logCurrent(String className, String methodName, String state) {
        return String.format("[CALL] : [CLASS] %s - [METHOD] %s - [STATE] %s", className, methodName, state);
    }
}