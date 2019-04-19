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
         4、v2Signature.py：重新对APK进行签名
            1、在v2Config.json文件中配置签名信息
            2、获取 AndroidSDK 提供的 apksigner.jar 工具，对 APK 重新签名，此方法可以将APK按照v1和v2共同签署应用
            注意：需配置AndroidSDK环境变量：Android_Home=xxxxx
    * 2、Windows批处理命令;
         1、generator.bat：生成release版本的安装包
         2、channel.bat：生成不同渠道的安装包
    * 注意：
         Python多渠道打包生成的APK文件在Android 7.0以上无法安装
         1、产生原因：
            1、Android Studio在默认情况下会使用 APK Signature Scheme v2 和传统签名方案来签署应用
            2、此多渠道打包机制实际上是修改APK内部文件，因此会导致采用v2方案签名失效，而7.0以上版本按照v2方案校验签名，所以生成的多渠道APK在7.0以上无法安装
         2、两种解决办法：
            1、关闭v2签名校验，在Gradle脚本中设置 v2SigningEnabled false
            2、对通过Python打包处理的APK重新进行签名




