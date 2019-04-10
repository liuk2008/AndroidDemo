# AndroidDemo #

## Android演示项目 ##

**app**

    * 1、增加混淆使用Demo
    * 2、设置系统状态栏与标题主题色一致
         * 1、通过代码动态设置状态栏与主题色一致
         * 2、设置系统状态栏透明，通过设置标题栏Padding高度=状态栏高度，保持颜色一致
         * 3、设置系统状态栏透明，通过设置自定义布局，保持颜色一致
              * 1、设置布局View，高度为0dp，背景色与主题色一致
              * 2、获取状态栏高度，设置布局View高度=状态栏高度
    * 3、增加网络框架使用Demo
    * 4、当TARGET_SDK_VERSION>=24时，设置代理无法抓取https包
         解决办法：targetSdkVersion降到23以下
    * 5、当TARGET_SDK_VERSION>=28且手机系统>=9.0时，当前应用被禁止进行http请求
         解决办法：APP改用https请求或者targetSdkVersion降到27以下
    * 6、同时解决4和5问题：在res下新增一个xml目录，创建xml文件，配置内容
         <!-- 需在Application配置android:networkSecurityConfig="@xml/network_security_config"-->
         <network-security-config>
             <!-- 处理7.0系统无法抓取https请求-->
             <!--使用 debug-overrides 指定仅在 android:debuggable 为 true 时才信任的仅调试 CA-->
             <debug-overrides>
                 <trust-anchors>
                     <certificates src="user" />
                 </trust-anchors>
             </debug-overrides>
             <!--处理9.0系统无法使用http请求-->
             <base-config cleartextTrafficPermitted="true" />
         </network-security-config>

**重点注意**

    * 1、项目框架：
         * 1、主页框架的搭建
         * 2、路由框架，网络框架
         * 3、组件化，模块化
         * 4、异常处理机制
         * 5、MVC、MVP、MVVM模型
    * 2、性能问题：
         * 1、项目架构中的内存泄露问题
         * 2、UI线程做下面的操作影响性能
              * |--1、载入图片
              * |--2、网络请求
              * |--3、解析JSON
              * |--4、读取数据库
         * 3、执行GC操作，以及频繁创建对象，会影响性能
         * 4、使用网络框架时，当发送多个请求后页面销毁时，如何处理回调方法
         * 5、启动速度：热启动、冷启动、暖启动
         * 6、编译速度，功耗
         * 7、安装包大小，布局优化
    * 3、安全问题：
         * 1、代码混淆
         * 2、签名校验
         * 3、http&https请求加密
         * 4、禁止使用代理
    * 4、其他问题
         * 1、动态申请权限需注意多种机型问题
         * 2、注意Application与BaseActivity的冗余程度
         * 3、考虑统一检测网络连接
         * 4、抽取工具类
         * 5、Fragment与Activity嵌套使用





