#!/bin/sh
SERVICE_NAME=spring-eureka
## Adjust log dir if necessary
APP_LOG=../log
APP_MAIN=com.scnu.eureka.main.Main  
APP_HOME=/home/zhouzq/service/spring-eureka
PID=0
javaps=`$JAVA_HOME/bin/jps -l |grep $APP_MAIN`

if [ -n "$javaps" ]; then
        PID=`echo $javaps | awk '{print $1}'`
fi
if [ "$PID" -ne 0 ]; then
	PID=`echo $javaps | awk '{print $1}'`
        kill -9 $PID
        javaps=`$JAVA_HOME/bin/jps -l |grep $APP_MAIN`
        if [ -z "$javaps" ]; then
            echo -n "Stop $SERVICE_NAME success!pid=$PID"
        fi
fi
