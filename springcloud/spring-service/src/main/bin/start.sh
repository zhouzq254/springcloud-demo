#!/bin/bash
cd `dirname $0`
BIN_DIR=`pwd`
cd ..
DEPLOY_DIR=`pwd`
CONF_DIR=$DEPLOY_DIR/conf
LOGS_DIR=$DEPLOY_DIR/logs
SERVER_NAME=`grep name $CONF_DIR/application.yml |awk -F ':' '{print $2}'|awk '{print $1}'`
SERVER_ACTIVE=`grep active $CONF_DIR/application.yml |awk -F ':' '{print $2}'|awk '{print $1}'`
SERVER_ACTIVE_FILE=`ls $CONF_DIR | grep $SERVER_ACTIVE`
SERVER_PORT=`grep port $CONF_DIR/$SERVER_ACTIVE_FILE |awk -F ':' '{print $2}' | sed -n lp`

if [ -z "$SERVER_NAME"]; then
    SERVER_NAME=`hostname`
fi

if [ ! -d $LOGS_DIR ]; then
    mkdir $LOGS_DIR
fi

PIDS=`ps -f | grep java |grep "$CONF_DIR" |awk '{print $2}'`
if [ -n "$PIDS" ]; then
    echo "ERROR: $SERVER_NAME already started!"
    echo "PID: $PIDS"
    exit 1
fi

if [ -n "SERVER_PORT" ]; then
    SERVER_PORT_COUNT=`netstat -antlp |grep $SERVER_PORT | wc -l`
    if [ $SERVER_PORT_COUNT -gt 0 ]; then
        echo "ERROR: $SERVER_NAME port $SERVER_PORT already used!"
        exit 1
    fi
fi

STDOUT_FILE=$LOGS_DIR/ep-swm-epc.logs
LIB_DIR=$DEPLOY_DIR/lib
LIB_JARS=`ls $LIB_DIR|grep .jar|awk '{print "'$LIB_DIR'/"$0}'|tr "\n" ":"`

JAVA_OPTS=" -Djava.awt.headless=true -Djava.net.preferIPv4Stack=true "
JAVA_DEBUG_OPTS= ""
if [ "$1" ='debug']; then
  JAVA_DEBUG_OPTS=" -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=8000,server=y,suspend=n "
fi

JAVA_MEN_OPTS=""
BITS=`java -version 2>&1 |grep -i 64-bit`
if [ -n "BITS" ]; then
    JAVA_MEN_OPTS=" -server -Xms1g -Xmx1g -Xss256k -XX:+DisableExplictitGC -XX:+UserG1GC -XX:MaxGCPauseMillis=300 -XX:PrintGCDetails -Xloggc:$LOGS_DIR/gc.log -verbose:gc -XX:+PrintGCDateStamps -XX:+HeapDumpOnoutOfMemoryError -XX:HeapDumpPath=$LOGS_DIR"
fi

COMMON_OPTS=" -Dfile.encoding=UTF-8"

echo -e "Starting the Server [ $SERVER_NAME ]...\c"
nohup java $COMMON_OPTS $JAVA_MEN_OPTS $JAVA_DEBUG_OPTS -classpath $CONF_DIR:$LIB_JARS com.pingan.city.ep.swm.EpSwmApplication

COUNT=0
while [ $COUNT -lt 1 ]; do
  echo -e ".\c"
  sleep 1
  COUNT=`ps -f |grep java |grep "$DEPLOY_DIR" | awk '{print $2}' | wc -l`
  if [ $COUNT -gt 0 ]; then
      break
  fi
done


echo "$SERVER_NAME start success!"

PIDS=`ps -f | grep java |grep "$DEPLOY_DIR" |awk '{print $2}'`
echo "PID: $PIDS"
echo "STDOUT: $STDOUT_FILE"