package com.skypro.sockswarehouse.loger;

/**
 * Логгер
 */
public class FormLogInfo {

    private FormLogInfo() {
    }

    public static String getInfo() {
        StackTraceElement stackTraceElement = new Throwable().getStackTrace()[1];
        String methodName = stackTraceElement.getMethodName();
        String className = stackTraceElement.getClassName();
        return "Старт метода " +
                "\"" +
                methodName +
                "\"" +
                " из класса " +
                "\"" +
                className +
                "\"";
    }
}
