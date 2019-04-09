import os
import shutil
import time

# Andoid 打包APK存放路径
build_type = "release"
apk_dir = 'app\\build\\outputs\\apk\\'

src_dir = os.path.join(os.path.abspath('..'), apk_dir, build_type)
tag_dir = os.path.join(os.path.abspath('..'), 'output\\',time.strftime('%Y-%m-%d',time.localtime(time.time()))+'\\')
src_apk = tag_apk = ''

'''
# 获取指定目录下文件名
def getApkName():
    for root, dirs, files in os.walk(''):
        for file in files: 
            if os.path.splitext(file)[1]=='.apk': # 其中os.path.splitext()函数将路径拆分为文件名+扩展名
                print(file)

getApkName()

'''


# 创建文件路径
def getApkName():
    # 目录不存在则创建
    if not os.path.exists(tag_dir):
        os.mkdir(tag_dir)
    if os.path.exists(src_dir):
        for file in os.listdir(src_dir):
            if os.path.splitext(file)[1] == '.apk':  # 其中os.path.splitext()函数将路径拆分为文件名+扩展名
                global src_apk, tag_apk
                src_apk = os.path.join(src_dir, file)
                tag_apk = os.path.join(tag_dir, file)


# 复制文件
def copyApk():
    # 删除已存在文件
    files = os.listdir(tag_dir)
    for file in files:
        file = os.path.join(tag_dir, file)
        os.remove(file)
    shutil.copyfile(src_apk, tag_apk)

# if __name__ == "__main__":
#     getApkName()
#     copyApk()
