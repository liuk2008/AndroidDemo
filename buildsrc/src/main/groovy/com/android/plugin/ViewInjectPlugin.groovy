package com.android.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by liuk on 2019/3/27
 */
class ViewInjectPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        System.out.println("Hello from the ViewinjectPlugin");
        System.out.println("Hello Jcenter");
        System.out.print("Hello World")
    }
}
