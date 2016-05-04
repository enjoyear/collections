#!/bin/bash
# Script to start the job server
# Extra arguments will be spark-submit options, for example
#  ./server_start.sh --jars cassandra-spark-connector.jar
#
# Environment vars (note settings.sh overrides):
#   JOBSERVER_MEMORY - defaults to 1G, the amount of memory (eg 512m, 2G) to give to job server
#   JOBSERVER_CONFIG - alternate configuration file to use
#   JOBSERVER_FG    - launches job server in foreground; defaults to forking in background
set -e

get_abs_script_path() {
  pushd . >/dev/null
  cd "$(dirname "$0")"
  appdir=$(pwd)
  popd  >/dev/null
}

get_abs_script_path

. $appdir/setenv.sh

GC_OPTS="-XX:+UseConcMarkSweepGC
         -verbose:gc -XX:+PrintGCTimeStamps -Xloggc:$appdir/gc.out
         -XX:MaxPermSize=512m
         -XX:+CMSClassUnloadingEnabled "

# To truly enable JMX in AWS and other containerized environments, also need to set
# -Djava.rmi.server.hostname equal to the hostname in that environment.  This is specific
# depending on AWS vs GCE etc.
JAVA_OPTS="-XX:MaxDirectMemorySize=$MAX_DIRECT_MEMORY \
           -XX:+HeapDumpOnOutOfMemoryError -Djava.net.preferIPv4Stack=true \
           -Dcom.sun.management.jmxremote.port=9999 \
           -Dcom.sun.management.jmxremote.rmi.port=9999 \
           -Dcom.sun.management.jmxremote.authenticate=false \
           -Dcom.sun.management.jmxremote.ssl=false \
           -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5050"

MAIN="spark.jobserver.JobServer"

PIDFILE=$appdir/spark-jobserver.pid
if [ -f "$PIDFILE" ] && kill -0 $(cat "$PIDFILE"); then
   echo 'Job server is already running'
   exit 1
fi

cmd='$SPARK_HOME/bin/spark-submit --class $MAIN --driver-memory $JOBSERVER_MEMORY
  --conf "spark.executor.extraJavaOptions=$LOGGING_OPTS"
  --conf "spark.executor.extraClassPath=/usr/local/hadoop-2.7.2/share/hadoop/tools/lib/mysql-connector-java-5.1.38.jar:/usr/local/hadoop-2.7.2/share/hadoop/tools/lib/hadoop-aws-2.7.2.jar:/usr/local/hadoop-2.7.2/share/hadoop/tools/lib/aws-java-sdk-1.7.4.jar"
  --driver-class-path "/usr/local/hadoop-2.7.2/share/hadoop/tools/lib/mysql-connector-java-5.1.38.jar:/usr/local/hadoop-2.7.2/share/hadoop/tools/lib/hadoop-aws-2.7.2.jar:/usr/local/hadoop-2.7.2/share/hadoop/tools/lib/aws-java-sdk-1.7.4.jar"
  --driver-java-options "$GC_OPTS $JAVA_OPTS $LOGGING_OPTS $CONFIG_OVERRIDES"
  $@ $appdir/spark-job-server.jar $conffile'
if [ -z "$JOBSERVER_FG" ]; then
  eval $cmd > /dev/null 2>&1 < /dev/null &
  echo $! > $PIDFILE
else
  eval $cmd
fi
