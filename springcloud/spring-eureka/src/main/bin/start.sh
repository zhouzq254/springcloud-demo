#!/bin/sh
SERVICE_NAME=spring-eureka
## Adjust log dir if necessary
APP_LOG=../log
APP_MAIN=com.scnu.eureka.main.Main  
APP_HOME=/home/zhouzq/service/spring-eureka
CLASS_PATH=""
for i in "$APP_HOME"/lib/*.jar; do
    CLASS_PATH="$CLASS_PATH":"$i"
done
#echo -n "$CLASS_PATH"
## Adjust memory settings if necessary
export JAVA_OPTS="-server -Xms1g -Xmx1g -XX:+PrintGCDateStamps -XX:+UseParNewGC -XX:ParallelGCThreads=4 -XX:+PrintCommandLineFlags -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=../log/gc.bin -XX:+PrintGCDetails -XX:+PrintTenuringDistribution -Xloggc:../log/gc.log -Dlogging.file=../log/app.log"


PID=0
javaps=`$JAVA_HOME/bin/jps -l |grep $APP_MAIN`

if [ -n "$javaps" ]; then
        PID=`echo $javaps | awk '{print $1}'`
else
        PID=0
fi
if [ "$PID" -ne 0 ]; then
	PID=`echo $javaps | awk '{print $1}'`
	echo "$SERVICE_NAME already started pid=$PID"
else
	echo -n "Starting $SERVICE_NAME"  
	nohup $JAVA_HOME/bin/java $JAVA_OPTS -classpath $CLASS_PATH $APP_MAIN >/dev/null 2>&1 &
	javaps=`$JAVA_HOME/bin/jps -l |grep $APP_MAIN`
	#echo -n "just print $javaps"
        PID=`echo $javaps | awk '{print $1}'` 
        if [ "$PID" -ne 0 ]; then
	    echo -n "$SERVICE_NAME start success!pid=$PID"
        fi
fi
