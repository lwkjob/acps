#!/bin/sh

if [ $# -eq 0 ]; then
  echo "you must type a parameter of 'order|assign|price|engine|money'"
  exit 0
fi

cmd=$1
cls='com.dili.dd.logistics.order.LogisticsOrderPlatform'
if [ "$cmd" = "order" ] ; then
    cls='com.dili.dd.logistics.order.platform.LogisticsOrderPlatform'
elif [ "$cmd" = "assign" ] ; then
    if [ "$2" = "debug" ] ; then
        cls='com.dili.dd.logistics.assign.app.AssignAppDebug'
    else
        cls='com.dili.dd.logistics.assign.app.AssignApp'
    fi
elif [ "$cmd" = "assign-debug" ] ; then
    cls='com.dili.dd.logistics.assign.app.AssignAppDebug'
elif [ "$cmd" = "los" ] ; then
    cls='com.dili.dd.logistics.los.order.platform.LogisticsExtOrderPlatform'
elif [ "$cmd" = "los-all" ] ; then
    cls='com.dili.dd.logistics.los.order.platform.LosOrderPlatform'
elif [ "$cmd" = "engine" ] ; then
    cls='com.dili.dd.logistics.engine.platform.LogisticsEnginePlatform'
elif [ "$cmd" = "price" ] ; then
    cls='com.dili.dd.logistics.price.LogisticsPricePlatform'
elif [ "$cmd" = "money" ] ; then
    cls='com.dili.dd.logistics.money.service.boot.LogisticsMoneyPlatform'
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

clspath=$way/conf:$clspath
export java_cls="$cls"
#echo $clspath
echo "sh $cls.sh"
java -Xms128m -Xmx256m -XX:PermSize=64m -XX:MaxPermSize=128m -XX:CMSInitiatingOccupancyFraction=70 -Dappname=$1 -DbaseHome=$way -Dfile.encoding=utf-8 -classpath "$clspath" "$java_cls"