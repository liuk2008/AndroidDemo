package com.android.plugin

import javassist.ClassPool
import javassist.CtClass
import javassist.CtMethod
import org.gradle.api.Project

/**
 * Created by liuk on 2019/3/27
 */
class InjectClass {
    //初始化类池
    private final static ClassPool pool = ClassPool.getDefault()

    static void inject(String path, Project project, String injectCode) {
        LogUtil.error("filePath = " + path)
        //将当前路径加入类池,不然找不到这个类
        pool.appendClassPath(path)
        //project.android.bootClasspath 加入android.jar，不然找不到android相关的所有类
        pool.appendClassPath(project.android.bootClasspath[0].toString())

        File dir = new File(path)
        if (dir.isDirectory()) {
            //遍历文件夹
            dir.eachFileRecurse { File file ->
                if (file.getName().equals("MainActivity.class")) {
                    //获取MainActivity.class
                    CtClass ctClass = pool.getCtClass("com.android.demo.MainActivity")
                    LogUtil.error("ctClass = " + ctClass)
                    //解冻
                    if (ctClass.isFrozen()) ctClass.defrost()
                    //获取到OnCreate方法
                    CtMethod ctMethod = ctClass.getDeclaredMethod("onCreate")
                    LogUtil.error("方法名 = " + ctMethod)
                    LogUtil.error("injectCode = " + injectCode)
                    //在方法开始注入代码
                    ctMethod.insertBefore(injectCode)
                    ctClass.writeFile(path)
                    ctClass.detach()//释放
                }
            }
        }
    }
}

