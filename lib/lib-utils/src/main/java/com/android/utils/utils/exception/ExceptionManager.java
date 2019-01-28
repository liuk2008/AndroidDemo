package com.android.utils.utils.exception;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExceptionManager {

    private static ExecutorService singleThreadExecutorService = Executors.newSingleThreadExecutor();
    private static ThrowableHandler mThrowableHandler;

    public static void init(ThrowableHandler throwableHandler) {
        mThrowableHandler = throwableHandler;
        setDefaultUncaughtExceptionHandler();
    }

    /**
     * 用ExceptionManager.handle(e) 来代替捕捉异常后的随意 e.printStackTrace()
     */
    public static void handle(final Throwable throwable) {
        if (null == mThrowableHandler)
            throw new RuntimeException("未初始化 ExceptionManager");
        singleThreadExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    mThrowableHandler.handleThrowable(throwable);
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        });
    }

    //设置全局异常捕捉
    private static void setDefaultUncaughtExceptionHandler() {
        // 获取系统默认的异常处理器
        final Thread.UncaughtExceptionHandler originHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 设置CrashHandler处理器
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                originHandler.uncaughtException(t, e);
                // 退出程序
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
            }
        });
    }

}
