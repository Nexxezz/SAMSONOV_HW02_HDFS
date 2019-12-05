HOMEWORK 2 
#####################################################################################################################################################################
TASK 1
Steps:
1. Download Hortonworks HDP image
2. Run Docker container via docker run -it IMAGE_NAME
3. copy dataset from localhost to HDP container via Ambari UI or via CLI command hdfs dfs -put /PATH/TO/DATASET/ /PATH/IN/HDFS/
4. Check that dataset is in HDFS via hdfs dfs -ls /PATH/TO/DATASET/ command and Ambari View(screenshot 1, 2)
5. Copy jar file form localhost to HDP container hdfs dfs -put /PATH/ container_name:/PATH/
6. Create jar file with maven-assembly-plugin with inteliJ idea or command maven clean compile assembly:single
7. Copy created jar file to the HDP container. Run command hadoop jar homework2-1.0-SNAPSHOT-jar-with-dependencies.jar SchemaReader arg1  command, where arg1 path to Avro or Parquet file(see results in screenshot 3,4,5)
#####################################################################################################################################################################
TASK2
Steps:
1. Create docker image from dockerfile with command docker build /PATH/TO/DOCKERFILE/
2. Create docker network with docker network create NETWORK_NAME command
3. Run containers with commands:
	docker run -it -e ZK_MYID=1 --hostname "namenode.cluster.com" --name "namenode" --network hadoop-network --network-alias namenode.cluster.com hadoop
	docker run -it -e ZK_MYID=2 --hostname "standbynode.cluster.com" --name "standbynode" --network hadoop-network --network-alias standbynode.cluster.com hadoop
	docker run -it -e ZK_MYID=3 --hostname "datanode.cluster.com" --name "datanode" --network hadoop-network --network-alias datanode.cluster.com hadoop
4. Start JournalNode in all containers with hadoop-daemon.sh start journalnode command(screenshot 6,7,8).
5. On active node:
	1)format hdfs: hdfs namenode -format (screenshot 9)
	2)start namenode: hadoop-daemon.sh start namenode(screenshot 10)

6. On standby node:
	1)move metadata from namenode: hdfs namenode -bootstrapStandby(screenshot 11)
	2)start namenode: hadoop-daemon.sh start namenode(screenshot 12)

7. Start datanode with hadoop-daemon.sh start datanode(screenshot 13).
8. Start Zookeeper on all nodes with zkServer.sh start command(screenshot 14,15,16).
9. On active node: 
	1)format ZKFC: hdfs zkfc -formatZK(screenshot 17)
	2)start ZKFC: hadoop-daemon.sh start zkfc(screenshot 18)
	3)check namenode status: hdfs haadmin -getServiceState namenode(screenshot 19)
	4)check standbynode status: hdfs haadmin -getServiceState standbynode(screenshot 219)
10. On standby node:
1)start ZKFC: hadoop-daemon.sh start zkfc(screenshot 20)
On namenode:
1) execute jps command and get Namenode PID(screenshot 21).
2) kill namenode with kill -9 PID command(screenshot 21).
11. On standby node check standbynode status hdfs haadmin -getServiceState standbynode. It should be "active" insread of "standby".
#####################################################################################################################################################################