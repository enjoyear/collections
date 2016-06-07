#Content
This collection shows how to deploy spark-job-server(SJS) to EMR.

##Step 1. Clone all files to /mnt/lib/spark-jobserver
- You also need the spark-job-server.jar and kill-process-tree.sh file


##Step 2. Check spark-defaults.conf under /etc/alternatives/spark-conf/ in the cluster
- Make sure spark.dynamicAllocation.enabled is true
- Read spark.executor.memory. This number is needed for next step


##Step 3. Customize emr.conf file
- Set spark.executor.memory to the number from last step
- Set spark.yarn.executor.memoryOverhead to be 10% of the spark.executor.memory 
- Read more at http://spark.apache.org/docs/latest/running-on-yarn.html


##Step4. Call server_start.sh to start SJS


##More readings:
https://github.com/spark-jobserver/spark-jobserver/blob/master/doc/EMR.md
