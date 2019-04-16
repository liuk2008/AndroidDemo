package com.android.plugin

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils
import org.apache.commons.codec.digest.DigestUtils
import org.gradle.api.Project

/**
 * Created by liuk on 2019/4/15
 */
class InjectClassTransform extends Transform {

    Project project
    String injectCode

    InjectClassTransform(Project project) {
        this.project = project
        println("create transform")
    }

    /**
     * Task名称：TransformClassesWith + getName() + For + buildTypes
     * 生成目录：build/intermediates/transforms/MyTransform/
     */
    @Override
    String getName() {
        return "PreDexInjectCode"
    }

    /**
     * 需要处理的数据类型：
     * CONTENT_CLASS：表示处理java的class文件，可能是 jar 包也可能是目录
     * CONTENT_RESOURCES：表示处理java的资源
     */
    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    /**
     * 指定Transform的作用范围
     *     PROJECT                       只有项目内容
     *     PROJECT_LOCAL_DEPS            只有项目的本地依赖(本地jar,aar)
     *     SUB_PROJECTS                  只有子项目
     *     SUB_PROJECTS_LOCAL_DEPS       只有子项目的本地依赖(本地jar,aar)
     *     PROVIDED_ONLY                 只提供本地或远程依赖项
     *     EXTERNAL_LIBRARIES            只有外部库
     *     TESTED_CODE                   测试代码
     */
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    /**
     * 指明当前Transform是否支持增量编译
     */
    @Override
    boolean isIncremental() {
        return false
    }

    /**
     * Transform中的核心方法
     * 若代码没有改变，此方法不会执行
     */
    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        println("transform")
        transformInvocation.inputs.each { TransformInput input ->
            // 遍历文件夹
            input.directoryInputs.each { DirectoryInput directoryInput ->
                String dirName = directoryInput.name
                InjectClass.inject(directoryInput.file.absolutePath, project, injectCode)
                def output = transformInvocation.outputProvider.getContentLocation(dirName, directoryInput.contentTypes, directoryInput.scopes, Format.DIRECTORY)
                FileUtils.copyDirectory(directoryInput.file, output)
            }

            // 遍历jar文件，但不做操作
            input.jarInputs.each { JarInput jarInput ->
                String jarName = jarInput.name
                if (jarName.endsWith(".jar")) {
                    jarName = jarName.substring(0, jarName.length() - 4)
                }
                String md5Hex = DigestUtils.md5Hex(jarInput.file.getAbsolutePath())
                def output = transformInvocation.outputProvider.getContentLocation(jarName + "-" + md5Hex, jarInput.contentTypes, jarInput.scopes, Format.JAR)
                FileUtils.copyFile(jarInput.file, output)
            }
        }
    }

}
