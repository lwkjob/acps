#!/bin/sh

echo "kill AcpsRpcStartupMain "
jps | grep AcpsRpcStartupMain | grep -v grep | grep -v tail | awk '{print $1}'| sed -e 's/^/kill -9 /g' | sh -

cls='com.yjy.apcs.rpc.server.AcpsRpcStartupMain'


export way=$(cd "$(dirname "${0}")";cd ..;pwd)

export clspath="classes"
for k in $way/libs/*.jar
do
    clspath=$clspath:$k
done

clspath=$way/conf:$clspath:$way/bin

export java_cls="$cls"
#echo $clspath
echo "sh $cls.sh"

export app_opts="-Xms128m -Xmx512m -XX:PermSize=64m -XX:MaxPermSize=128m -XX:CMSInitiatingOccupancyFraction=70 -DbaseHome=$way -Dfile.encoding=utf-8"
nohup nice java -classpath "$clspath" $app_opts "$java_cls" > "$way/logs/acps-thrift.log" 2>&1 < /dev/null &