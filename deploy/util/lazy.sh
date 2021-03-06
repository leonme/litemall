#!/bin/bash

# 本脚本的作用是
# 1. 项目打包
# 2. 上传云主机
# 3. 远程登录云主机并执行reset脚本

# 请设置云主机的IP地址和账户
# 例如 ubuntu@122.152.206.172
REMOTE=root@xqnxtc.cn
# 请设置本地SSH私钥文件id_rsa路径
# 例如 /home/litemall/id_rsa
ID_RSA=/Users/leon/.ssh/id_rsa

if test -z "$REMOTE"
then
  echo "请设置云主机登录IP地址和账户"
  exit -1
fi

if test -z "$ID_RSA"
then
  echo "请设置云主机登录IP地址和账户"
  exit -1
fi

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null && pwd )"
cd $DIR/../..
LITEMALL_HOME=$PWD
echo "LITEMALL_HOME $LITEMALL_HOME"

# 项目打包
cd $LITEMALL_HOME
sh ./deploy/util/package.sh

# 上传云主机
cd $LITEMALL_HOME
scp -i $ID_RSA -r  ./deploy $REMOTE:/root/git/litemall

# 远程登录云主机并执行reset脚本
ssh $REMOTE -i $ID_RSA << eeooff
cd /root/git/litemall
sudo sh ./deploy/bin/reset.sh
exit
eeooff