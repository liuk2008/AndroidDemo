# AndroidDemo

Android演示项目

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

**lib**

    注意：尽量避免各个lib包之间相互引用

	* 1、lib-common
	
         * base包
         * 1、增加BaseFragment基础类
              1、可以捕捉返回键，增加自定义功能
              2、使用单例模式创建Fragment实例
              注意：Fragment中EditText文字的记忆性
         * 2、增加BaseActivity基础类
              1、添加、回退Fragment
              2、特殊处理Fragment中的返回键
              3、配置沉浸式系统状态栏
              4、封装常见View的系统方法
              5、增加网络检测功能，网络未连接时显示异常信息
         * 3、增加ToolbarUtil标题栏工具类
         * 4、增加PermissionActivity，此Activity为Dialog形式，用于Android 6.0 动态申请权限
              1、可单独使用工具类请求权限，获取未授权的权限信息
              2、申请单个、多个权限时，弹出对话框，进入到应用系统信息页面
         * 5、增加FragmentHostActivity，用于创建Fragment。注意：一般一个FragmentHostActivity嵌套一个Fragment
  
         * net包
         * 1、封装网络框架：
	          1、Retrofit2 + CallBack：封装Retrofit网络框架
	             增加Cookie管理机制、设置请求头
	          2、Retrofit2 + RxJava2：封装Retrofit+RxJava2网络框架
	             增加Retrofit缓存机制、Cookie管理机制、设置请求头、日志机制、重新连接机制
	          3、Http + Callback：封装原生网路框架
	          4、每个网络框架可取消单个请求，也可取消全部请求
	          5、网络层统一检测网络连接状态，包含：网络未连接，网络已连接但无法正常访问
	          6、release版本设置NO_PROXY，禁止通过代理抓取http、https请求
         * 2、ErrorHandler：处理网络请求异常
         * 3、Null：当网络请求正常但无返回数据时，可使用Null对象解析
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
         * 注意：
               1、页面销毁后取消网络回调
               2、使用过程中注意内存泄漏问题

         * h5包 
         * 1、封装自定义WebView，设置WebSettings
         * 2、封装WebViewUtils工具类，提供获取Cookie，Header等方法
         * 3、封装WebChromeClient，提供showPicWindow回调方法，处理文件上传
         * 4、封装WebViewClient，提供相关回调方法
              1、onLoadUrl：处理特定URL
              2、executorJs：与js脚本交互
              3、处理网络错误，显示自定义错误界面
         * 5、封装WebViewHelper，加载自定义WebView，监听对应事件，提供方法
              1、getFromAssets：从assets中加载文件
              2、injectJs、excJsMethod、evaluateJs、addJavascriptInterface：与js相关的方法
              3、showPicWindow、setWebViewPic：调用本地拍照、图库等功能，向WebView传递文件
              4、增加js文件，注入到h5页面，关联本地方法与js方法，实现点击图片放大、缩小、长按等功能
         * 注意：使用WebView时存在内存泄漏问题

         * refreshview包
         * 1、增加自定义RefreshView，实现下拉刷新、上拉加载功能
              1、布局文件使用SwipeRefreshLayout与RecyclerView嵌套，初始化view
              2、通过监听SwipeRefreshLayout刷新事件，实现下拉刷新功能，默认开启
              3、通过监听RecyclerView滑动事件，实现上拉加载功能，默认开启
              4、可单独设置刷新、加载功能是否开启，进度条是否显示，提供回调接口监听各状态事件
              5、设置Adapter，底层通过MyCommonAdapter填充数据
         * 2、增加自定义MyCommonAdapter，继承于RecyclerView.Adapter
              1、设置Adapter，提供item点击、添加数据功能
              2、包装原始Adapter，统一添加底部布局，根据数据量动态展示
              3、封装RecyclerView.Adapter公共方法，通过MyViewHolder封装itemView
              4、提供convert方法填充item数据

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

    * 1、viewinject注解管理器机制
         * 1、view-annotation：注解包，统一管理注解。包括：MyBindView、MyOnClick
         * 2、view-compiler：注解管理器包，在编译时扫描和处理注解，生成对应的Java文件，统一处理注解作用的元素
         * 3、view-bindview：MyViewInjector通过反射调用注解管理器生成的Java源文件
         * 注意：
               1、使用此lib包时，会增加APK的方法数量以及APK的大小
               2、使用注解时注意内存泄漏

**重点注意**

    * 1、项目框架：
         * 1、主页框架的搭建，Fragment与Activity嵌套使用等
         * 2、路由框架，网络框架
         * 3、组件化，模块化
         * 4、异常处理机制
         * 5、MVC、MVP、MVVM 模型
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





