# coding=gbk
import json
import os

apksigner_path = ''

# ��ȡJson�ļ�
with open('v2Config.json', 'r') as f:
    config = json.load(f)

keyStorePath = config['keyStorePath']  # ��Կ���·��
alias = config['alias']  # ��Կ��ʶ��
keyStorePwd = config['keyStorePwd']  # ��Կ������
aliasPwd = config['aliasPwd']  # ��Կ����


# ��ȡ AndroidSDK �ṩ�� apksigner.jar ����
def getApksignerPath(android_home):
    global apksigner_path
    build_tools = android_home + '\\build-tools'
    for parent, dirnames, filenames in os.walk(build_tools):
        for filename in filenames:
            if filename == 'apksigner.jar':
                apksigner_path = os.path.join(parent, filename)
                break


# �� APK ����ǩ��
def v2Sign(android_home, target_dir, target_names):
    getApksignerPath(android_home)

    global keyStorePath
    keyStorePath = os.path.join(os.path.abspath('..'), keyStorePath)

    cmd = 'java -jar ' + apksigner_path
    cmd = cmd + ' sign --ks ' + keyStorePath
    cmd = cmd + ' --ks-key-alias ' + alias
    cmd = cmd + ' --ks-pass pass:' + keyStorePwd
    cmd = cmd + ' --key-pass pass:' + aliasPwd

    print('apksigner.jar ·����' + apksigner_path)
    print('��Կ���·����' + keyStorePath)
    print('��Կ�����룺' + keyStorePwd)
    print('��Կ��ʶ����' + alias)
    print('��Կ���룺' + aliasPwd)

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
