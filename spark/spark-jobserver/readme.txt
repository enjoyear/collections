This collection contains the customized settings to run the spark-jobserver locally, and the changes you need to add to your .bashrc file.

To enable debugging the client side code, add this line to the JAVA_OPS in server_start.sh
-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5050

Call startLocalJobserver to start the spark job server locally
Call stopLocalJobserver to stop the spark job server locally

