# AndroidDemo

Android演示项目

**app**

    * 1、增加混淆使用Demo
    * 2、通过代码动态设置状态栏与主题色一致
         * 1、设置布局View，高度为0dp
         * 2、获取状态栏高度，设置布局View高度=状态栏高度
         * 3、设置布局View背景色与主题色一致
    * 3、增加网络框架使用Demo

**lib**

    注意：尽量避免各个lib包之间相互引用

	* 1、lib-common

	    * base包
          1、增加BaseFragment基础类
             1、可以捕捉返回键，增加自定义功能
             2、使用单例模式创建Fragment实例
             注意：Fragment中EditText文字的记忆性
          2、增加BaseActivity基础类
             1、添加、回退Fragment
             2、特殊处理Fragment中的返回键
             3、配置沉浸式系统状态栏
             4、封装常见View的系统方法
             5、增加网络检测功能，网络未连接时显示异常信息
          3、增加ToolbarUtil标题栏工具类
          4、增加PermissionActivity，此Activity为Dialog形式，用于Android 6.0 动态申请权限
             1、可单独使用工具类请求权限，获取未授权的权限信息
             2、申请单个、多个权限时，弹出对话框，进入到应用系统信息页面
          5、增加FragmentHostActivity，用于创建Fragment。注意：一般一个FragmentHostActivity嵌套一个Fragment

        * net包
	      1、封装网络框架：
	         1、Retrofit2 + CallBack：封装Retrofit网络框架
	         2、Retrofit2 + RxJava2：封装Retrofit+RxJava2网络框架
	            增加Retrofit缓存机制、Cookie管理机制、设置请求头、日志机制、重新连接机制
	         3、Http + Callback：封装原生网路框架
	         4、每个网络框架可取消单个请求，也可取消全部请求
	      2、ErrorHandler：处理网络请求异常
          3、Null：当网络请求正常但无返回数据时，可使用Null对象解析
          * http请求场景
          * 1、网络层200情况下
          * |--1、业务层存在数据
          * |-----1、业务层数据格式标准
          * |--------1、业务层200，处理返回数据
          * |-----------1、存在数据，使用数据model解析
          * |-----------2、不存在数据，使用Null对象解析
          * |--------2、业务层非200，抛出 ErrorException，通过ErrorHandler处理
          * |-----2、业务层数据非标准格式，使用数据model解析
          * |--2、业务层不存在数据，使用Null对象解析
          * 2、网络层非200情况下
          * |--1、网络异常时，捕获异常，通过ErrorHandler处理
          * |--2、网络正常，业务层异常通过网络层抛出时，通过ErrorHandler处理
          注意：
          1、页面销毁后取消网络回调
          2、使用过程中注意内存泄漏问题

    * 2、lib-scan：扫码功能
        * 1、继承CaptureActivity，复写dealData方法可以获取到数据和码的格式
        * 2、可以自定义布局页面，也可以使用默认扫码页面

    * 3、lib-utils：工具类
        * 1、common：一般功能工具类
        * 2、exception：异常工具类
        * 3、safety：安全校验类
        * 4、system：系统工具类
        * 注意：使用ShakeInfoUtil工具类可以获取当前页面结构

    * 4、geetest：极验验证码

**viewinject**

    * viewinject注解管理器机制
        * 1、view-annotation：注解包，统一管理注解。包括：MyBindView、MyOnClick
        * 2、view-compiler：注解管理器包，在编译时扫描和处理注解，生成对应的Java文件，统一处理注解作用的元素
        * 3、view-bindview：MyViewInjector通过反射调用注解管理器生成的Java源文件
        * 注意：
          1、使用此lib包时，会增加APK的方法数量以及APK的大小
          2、使用注解时注意内存泄漏


**重点注意**

	* 1、项目架构中的内存泄露问题，需重点考虑
	* 2、JSON解析工作在UI线程中进行，影响性能，需放在工作线程进行处理
	* 3、需同时考虑http协议&https协议请求
	* 4、UI线程做下面的操作影响性能
		* |--1、载入图片
		* |--2、网络请求
		* |--3、解析JSON
		* |--4、读取数据库
	* 5、注意安全问题，加密，代码混淆
	* 6、考虑发送多个请求后，页面销毁时如何管理
	* 7、执行GC操作时，会影响性能
	* 8、考虑统一检测网络连接
	* 9、注意Application与BaseActivity的冗余程度
	* 10、动态申请权限需注意多种机型问题