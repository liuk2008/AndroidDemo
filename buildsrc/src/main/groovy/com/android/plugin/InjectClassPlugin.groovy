package com.android.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by liuk on 2019/3/27
 */
class InjectClassPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        // 注册一个Transform
        def classTransform = new InjectClassTransform(project)
        project.android.registerTransform(classTransform)
        // 自定义Extension，Gradle脚本中通过Extension传递一些配置参数给自定义插件
        def extension = project.extensions.create("injectClass", InjectClassExtension)
        project.afterEvaluate {
            println(extension.injectName)
            println(extension.injectCode)
            // 通过Extension的方式传递将要被注入的自定义代码
            classTransform.injectCode = extension.injectCode
        }
    }

}

class InjectClassExtension {
    String injectCode
    String injectName
}

//private void test() {
//    if (!project.plugins.hasPlugin(LibraryPlugin))
//        return
//    def variants
//    if (project.plugins.hasPlugin(LibraryPlugin)) {
//        variants = project.android.libraryVariants
//    } else {
//        variants = project.android.applicationVariants
//    }
//    //在脚本分析完成之后执行
//    project.afterEvaluate {
//        //遍历变体
//        variants.all { BaseVariant variant ->
//            variant.outputs.each { BaseVariantOutput output ->
//                output.processResources.doLast {
//                    def javaDirector = variant.sourceSets.get(0).getJavaDirectories().getAt(0)
//                    println("R 文件路径:" + output.outputFile.getPath())
//                    File rDir = new File(sourceOutputDir,
//                            packageForR.replaceAll('\\.',
//                                    StringEscapeUtils.escapeJava(File.separator)))
//                    File file = new File(rDir, 'R.java')
//                }
//            }
//        }
//    }
//}