package com.android.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by liuk on 2019/3/27
 */
class InjectClassPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        LogUtil.project = project
        LogUtil.error("====== 引入 " + InjectClassPlugin.name + " 插件 ======")
        // 注册一个Transform
        LogUtil.error("====== 注册 " + InjectClassTransform.name + " ======")
        def classTransform = new InjectClassTransform(project)
        project.android.registerTransform(classTransform)
        // 自定义Extension，Gradle脚本中通过Extension传递一些配置参数给自定义插件
        def extension = project.extensions.create("injectClass", InjectClassExtension)
        project.afterEvaluate { // project 配置完成后回调
            LogUtil.error("====== 获取 extension 配置参数 ======")
            LogUtil.error(extension.injectName)
            LogUtil.error(extension.injectCode)
            // 通过Extension的方式传递将要被注入的自定义代码
            classTransform.injectCode = extension.injectCode
        }
    }
}

class InjectClassExtension {
    String injectCode
    String injectName
}
