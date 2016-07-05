#!/bin/sh
if [ $# -eq 0 ]; then
  echo "you must type a parameter of 'acps|money'"
  exit 0
fi

cmd=$1
cls='com.yjy.apcs.rpc.server.report.AcpsRpcStartup'
if [ "$cmd" = "acps" ] ; then
    cls='com.yjy.apcs.rpc.server.report.AcpsRpcStartup'
elif [ "$cmd" = "money" ] ; then
        cls='com.yjy.LogisticsMoneyPlatform'
else
	echo "No application to start ."
	exit 1
fi

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
nohup nice java -classpath "$clspath" $app_opts "$java_cls" > "$way/log/$cmd.log" 2>&1 < /dev/null &