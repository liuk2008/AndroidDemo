# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# This is a configuration file for ProGuard.
# https://www.guardsquare.com/en/products/proguard/manual/examples

#---------------------------------基础信息----------------------------------
# 指定压缩级别
-optimizationpasses 5
# 混淆时采用的算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
# 把混淆类中的方法名也混淆了
-useuniqueclassmembernames
# 优化时允许访问并修改有修饰符的类和类的成员
-allowaccessmodification
# 不跳过library中的非public的成员和方法
-dontskipnonpubliclibraryclassmembers
# 保留用于调试堆栈跟踪的行号信息
-keepattributes SourceFile,LineNumberTable
# 使用了上一行配置，还需要添加如下配置将源文件重命名为SourceFile
-renamesourcefileattribute SourceFile
# 忽略警告
-ignorewarnings
# 保留泛型参数
-keepattributes Signature
# 保留注解
-keep class * extends java.lang.annotation.Annotation { *; }
# 不混淆内部类
-keepattributes InnerClasses
#----------------------------------------------------------------------------

#---------------------------------默认保留区---------------------------------
# 不混淆四大组件
-keep public class * extends android.app.Activity
-keep public class * extends android.support.v4.app.Fragment
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference

# 保留自定义View的构造函数
-keepclasseswithmembers class * {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

# 不混淆R文件
-keep class **.R$* {*;}

# 对于带有回调函数的onXXEvent、**On*Listener的，不能被混淆
-keepclassmembers class * {
    void *(**On*Event);
    void *(**On*Listener);
}

# 保留 V4 或V7 包
-keep class android.support.** {*;}


# 保留所有实现 Serializable 接口的类成员
-keepclassmembers class * implements java.io.Serializable {*;}

# 保留 WebView 相关
-keepclassmembers class * extends android.webkit.webViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public void *(android.webkit.webView, jav.lang.String);
    public boolean *(android.webkit.WebView, java.lang.String);
}

# WebView中使用了JS调用，保留JavaScript调试属性
-keepattributes *JavascriptInterface*
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

# 删除代码中Log相关的代码
-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int i(...);
    public static int w(...);
    public static int d(...);
    public static int e(...);
}
#---------------------------------默认保留区---------------------------------

#---------------------------------第三方--------------------------------
# Gson 混淆规则
-keep class sun.misc.Unsafe { *; }
-keep class com.google.code.gson.** { *; }
-keep interface com.google.code.gson.** { *; }

# Eventbus 混淆规则
-keepclassmembers class ** {
        @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
        <init>(java.lang.Throwable);
}

# Zxing 混淆规则
-dontwarn com.google.zxing.**

# viewinject 混淆规则
-keep class com.viewinject.bindview.** { *; }
-keep class **ViewInjector{ *; }
-keepclasseswithmembernames class * {
    @MyBindView.* <fields>;
}
-keepclasseswithmembernames class * {
    @MyOnClick.* <methods>;
}
#---------------------------------第三方--------------------------------


