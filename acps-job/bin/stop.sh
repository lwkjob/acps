#!/bin/sh

if [ $# -eq 0 ]; then
  echo "you must type a parameter of 'acps-jar|money'"
  exit 0
fi
cmd=$1
cls='AcpsStartup'
if [ "$cmd" = "acps-jar" ] ; then
    cls='AcpsStartup'
elif [ "$cmd" = "money" ] ; then
        cls='LogisticsMoneyPlatform'
else
	echo "No application to stop ."
	exit 1
fi	

jps | grep "$cls" | grep -v grep | awk '{print $1}'| sed -e 's/^/kill -9 /g' | sh -