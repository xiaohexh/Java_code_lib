#!/bin/bash
BASEDIR=`dirname $0`/../lib
BASEDIR=`(cd "$BASEDIR"; pwd)`

CONFIGDIR=`dirname $0`/../resource
echo $CONFIGDIR
CONFIGDIR=`(cd $CONFIGDIR; pwd)`
echo $CONFIGDIR

FEATURE=com.xh.netty.udp.logevent.broadcast.LogEventBroadcaster

setsid /export/servers/jdk1.8.0_20/bin/java -server -Xms1024m -Xmx1024m -XX:MaxPermSize=1024m -XX:+UseParallelGC -XX:+UseParallelOldGC -DconfigPath=$CONFIGDIR -Djava.ext.dirs=$BASEDIR $FEATURE &