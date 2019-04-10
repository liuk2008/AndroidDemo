# AndroidDemo #

## python打包工具 ##

**tools**

    * 1、增加Python脚本：
         1、CopyApk.py：复制Android项目release版本的APK，到指定的存放路径 output\yyyy-mm-dd
         2、Jenkins.py：通过Jenkins平台实现多渠道打包
            1、获取Jenkins环境参数，打包类型、渠道标识等
            2、复制Android项目的APK，到指定Tomcat的存放路径 apk\yyyy-mm-dd
            3、执行ChannelApk.py脚本，实现多渠道打包
         3、ChannelApk.py：通过修改APK中的META-INF方式进行多渠道打包
            1、根据环境执行CopyApk.py或者Jenkins.py脚本，复制APK
            2、创建channel/czt.txt空文件，同时读取channel/info.txt中的渠道标识，拷贝建立以渠道标识命名的新APK
            3、直接解压APK，解压后的根目录会有一个META-INF目录
            4、将channel/czt.txt添加到META-INF目录中，保存后的文件名称为META-INF/Channel_xxx，因此通过为不同渠道的应用添加不同的空文件
            5、采用这种方式可以不用重新签名应用，每打一个渠道包只需复制一个APK，在META-INF中添加一个使用渠道号命名的空文件即可
    * 2、Windows批处理命令;
         1、generator.bat：生成release版本的安装包
         2、channel.bat：生成不同渠道的安装包







