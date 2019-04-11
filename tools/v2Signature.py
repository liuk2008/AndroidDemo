# coding=gbk
import os
import json
import time

with open('v2Config.json', 'r') as f:
    config = json.load(f)

apksignerPath = config['apksignerPath']  # apksigner路径
keyStorePath = config['keyStorePath']  # keystore 路径
keystoreAlias = config['keyStoreAlias']  # 签名文件的alias
KeyStorePwd = config['KeyStorePwd']  # 签名文件的密码
aliasPwd = config['aliasPwd']  # 签名文件alias密码

def v2Sign(target_names):
    global keyStorePath
    keyStorePath = os.path.join(os.path.abspath('..'), keyStorePath)

    # print(apksignerPath)
    # print(keyStorePath)
    # print(keystoreAlias)
    # print(KeyStorePwd)
    # print(aliasPwd)

    cmd = 'java -jar ' + apksignerPath
    cmd = cmd + ' sign --ks ' + keyStorePath
    cmd = cmd + ' --ks-key-alias ' + keystoreAlias
    cmd = cmd + ' --ks-pass pass:' + KeyStorePwd
    cmd = cmd + ' --key-pass pass:' + aliasPwd
    for target_name in target_names:
        src_dir = os.path.join(os.path.abspath('..'),
                               'output\\' + time.strftime('%Y-%m-%d', time.localtime(time.time())) + '\\'+target_name)
        target_cmd = cmd + ' --out ' + src_dir + ' ' + src_dir
        # cmd = 'java -jar {} sign --ks {} --ks-key-alias {} --ks-pass pass:{} --key-pass pass:{} --out {} {}' \
        #     .format(apksignerPath, keyStorePath, keystoreAlias, KeyStorePwd, aliasPwd, src_dir, src_dir)
        print(target_cmd)
        os.system(target_cmd)

# if __name__ == "__main__":
#     v2Sign(target_names)
