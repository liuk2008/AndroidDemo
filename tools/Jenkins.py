import os
import shutil
import time

# 多渠道标识
is_need_channel = False
channels = []
# 文件目录
src_dir = tag_dir = ""

build_type = ''


# 判断是否存在Jenkins环境
def isJenkins():
    path = os.getenv("path")
    if ((path != None) and (path.__contains__("jenkins"))):
        return True
    else:
        return False


# 获取Jenkins环境参数
def getJenkins():
    job_name = os.getenv("JOB_NAME")
    git_branch = os.getenv("GIT_BRANCH")
    # git_branch= subprocess.check_output(['git', 'branch']).strip()
    build_number = os.getenv("BUILD_NUMBER")
    global src_dir, is_need_channel, build_type
    build_type = os.getenv("BUILD_TYPE")
    src_dir = os.path.join(os.path.abspath('..'), "app\\build\\outputs\\apk\\" + build_type)
    is_need_channel = os.getenv("IS_NEED_CHANNEL")
    if 'false' == is_need_channel:
        is_need_channel = False
    else:
        is_need_channel = True


#  执行jenkins配置的渠道标识
def getChannel():
    jenkins_channels = os.getenv("Channels")
    if jenkins_channels != None:
        jenkins_channels = jenkins_channels.strip()
        global channels
        channels = jenkins_channels.split(",")


def copyApk():
    # print(os.getcwd())  # 当前文件的路径
    # grader_father = os.path.dirname(os.path.dirname(os.path.dirname(os.path.abspath(".."))))
    path = os.path.abspath("..")  # 当前文件的父路径
    for i in range(0, 3):
        path = os.path.abspath(path + os.path.sep + "..")
    global tag_dir
    tag_dir = os.path.join(path, 'apk\\' + time.strftime('%Y-%m-%d', time.localtime(
        time.time())) + '\\' + build_type + '\\')

    # 复制文件夹
    if os.path.exists(tag_dir):
        shutil.rmtree(tag_dir)
    shutil.copytree(src_dir, tag_dir)

# if __name__ == "__main__":
#     getJenkins()
#     if is_need_channel:
#         getChannel()
#         ChannelApk.createChannelApk(channels, CopyApk.tag_dir)
#     else:
#         CopyApk.getApkName()
#         CopyApk.copyApk()
