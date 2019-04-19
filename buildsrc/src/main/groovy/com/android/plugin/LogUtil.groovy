package com.android.plugin

import org.gradle.api.Project


class LogUtil {
    static Project project

    static void error(String msg) {
        if (project == null)
            println(msg)
        else
            project.logger.error(msg)
    }

}