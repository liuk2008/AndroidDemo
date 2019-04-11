# coding=gbk
import json
import os

apksigner_path = ''

# 读取Json文件
with open('v2Config.json', 'r') as f:
    config = json.load(f)

keyStorePath = config['keyStorePath']  # keystore 路径
keystoreAlias = config['keyStoreAlias']  # 签名文件的alias
KeyStorePwd = config['KeyStorePwd']  # 签名文件的密码
aliasPwd = config['aliasPwd']  # 签名文件alias密码


# 获取 AndroidSDK 提供的 apksigner.jar 工具
def getApksignerPath(android_home):
    global apksigner_path
    build_tools = android_home + '\\build-tools'
    for parent, dirnames, filenames in os.walk(build_tools):
        for filename in filenames:
            if filename == 'apksigner.jar':
                apksigner_path = os.path.join(parent, filename)
                break


# 对 APK 重新签名
def v2Sign(android_home, target_dir, target_names):
    getApksignerPath(android_home)

    global keyStorePath
    keyStorePath = os.path.join(os.path.abspath('..'), keyStorePath)

    cmd = 'java -jar ' + apksigner_path
    cmd = cmd + ' sign --ks ' + keyStorePath
    cmd = cmd + ' --ks-key-alias ' + keystoreAlias
    cmd = cmd + ' --ks-pass pass:' + KeyStorePwd
    cmd = cmd + ' --key-pass pass:' + aliasPwd

    print('apksigner.jar 路径：' + apksigner_path)
    print('keystore 路径：' + keyStorePath)
    print('签名文件的alias：' + keystoreAlias)
    print('签名文件的密码：' + KeyStorePwd)
    print('签名文件alias密码：' + aliasPwd)

    if target_names is None:
        return

    for target_name in target_names:
        src_dir = os.path.join(target_dir + target_name)
        target_cmd = cmd + ' --out ' + src_dir + ' ' + src_dir
        # cmd = 'java -jar {} sign --ks {} --ks-key-alias {} --ks-pass pass:{} --key-pass pass:{} --out {} {}' \
        #     .format(apksignerPath, keyStorePath, keystoreAlias, KeyStorePwd, aliasPwd, src_dir, src_dir)
        print(target_cmd)
        os.system(target_cmd)

# if __name__ == "__main__":
#     getApksignerPath(os.getenv("Android_home"))
#     v2Sign(None)
