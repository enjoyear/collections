#Content
This collection contains the customized settings to run the spark-job-server(SJS) locally. Example settings are kept under bin folder. Add/Merge those files to your SJS bin folder.


##Environment Variable Changes in .bashrc
```bash
	export SPARK_HOME=/usr/local/spark/spark-1.6.1
	export PATH="$PATH:$SPARK_HOME/bin"
	export SPARK_CONF_DIR=$SPARK_HOME/conf
	export SPARK_JOBSERVER_HOME=/usr/local/spark/spark-jobserver
	export SPARK_MASTER_IP=127.0.0.1
	export SPARK_LOCAL_IP=127.0.0.1
```


##Debug with IntelliJ
To enable debugging the client side code, add this line to the JAVA_OPS in server_start.sh
```
-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5050
```


##Work with AWS S3 and MySQL
###Add AWS and MySQL jars to driver and executor class path in the cmd of server_start.sh.

```
--conf "spark.executor.extraClassPath=/usr/local/hadoop-2.7.2/share/hadoop/tools/lib/mysql-connector-java-5.1.38.jar:/usr/local/hadoop-2.7.2/share/hadoop/tools/lib/hadoop-aws-2.7.2.jar:/usr/local/hadoop-2.7.2/share/hadoop/tools/lib/aws-java-sdk-1.7.4.jar"
--driver-class-path "/usr/local/hadoop-2.7.2/share/hadoop/tools/lib/mysql-connector-java-5.1.38.jar:/usr/local/hadoop-2.7.2/share/hadoop/tools/lib/hadoop-aws-2.7.2.jar:/usr/local/hadoop-2.7.2/share/hadoop/tools/lib/aws-java-sdk-1.7.4.jar"

```
###Add access keys to your .bashrc file
```bash
    export AWS_ACCESS_KEY_ID=?????
    export AWS_SECRET_ACCESS_KEY=?????
```


##How to start and stop
Call startLocalJobserver to start the spark job server locally
Call stopLocalJobserver to stop the spark job server locally
