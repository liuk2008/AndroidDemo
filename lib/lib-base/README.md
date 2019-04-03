# AndroidDemo #

## Android演示项目 ##

**lib-base**

     注意：尽量避免各个lib包之间相互引用

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
     * 3、增加FragmentHostActivity，用于创建Fragment。注意：一般一个FragmentHostActivity嵌套一个Fragment

