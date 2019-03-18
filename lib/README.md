# AndroidDemo

Android演示项目

**lib**

    注意：尽量避免各个lib包之间相互引用

	* 1、lib-common：网络框架，WebView框架，封装Activity与Fragment，自定义刷新View等
	
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

