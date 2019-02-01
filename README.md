# AndroidDemo

Android演示项目

**app**

    * 1、增加混淆使用Demo

**lib**

	* 1、lib-common
		* 1、增加BaseFragment基础类
		     1、可以捕捉返回键
		* 2、增加BaseActivity基础类
		     1、添加、回退Fragment
		     2、特殊处理Fragment中的返回键
		     3、配置沉浸式系统状态栏
		     4、封装常见View的系统方法
		* 3、增加网络检测功能，网络未连接时显示异常信息
		* 4、增加ToolbarUtil标题栏工具类
		* 5、增加PermissionActivity，此Activity为Dialog形式，用于Android 6.0 动态申请权限
		     1、可单独使用工具类请求权限，获取未授权的权限信息
		     2、申请单个、多个权限时，弹出对话框，进入到应用系统信息页面
		* 6、增加FragmentHostActivity，用于创建Fragment。注意：一般一个FragmentHostActivity嵌套一个Fragment
	* 2、viewinject注解管理器机制
		* 1、view-annotation：注解包，统一管理注解。包括：MyBindView、MyOnClick
		* 2、view-compiler：注解管理器包，在编译时扫描和处理注解，生成对应的Java文件，统一处理注解作用的元素
		* 3、view-bindview：MyViewInjector通过反射调用注解管理器生成的Java源文件
		* 注意：
		      1、使用此lib包时，会增加APK的方法数量以及APK的大小
		      2、使用注解时注意内存泄漏
    * 3、lib-scan：扫码功能
        * 1、继承CaptureActivity，复写dealData方法可以获取到数据和码的格式
        * 2、可以自定义布局页面，也可以使用默认扫码页面
    * 4、lib-utils：工具类
        * 1、common：一般功能工具类
        * 2、exception：异常工具类
        * 3、safety：安全校验类
        * 4、system：系统工具类