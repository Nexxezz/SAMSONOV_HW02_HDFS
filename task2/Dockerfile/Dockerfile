#https://blog.mailon.com.ua/hadoop-hdfs-%D0%B2-%D1%80%D0%B0%D0%B6%D0%B5%D0%BC%D0%B8-ha-%D0%BD%D0%B0-centos-7/
FROM centos:7 AS img1
RUN yum -y update && \
	yum install -y nano which wget mc java-1.8.0-openjdk-devel && \
	yum -y install openssh-clients openssh-server 

FROM img1 AS img2
RUN ssh-keygen -t rsa -f /root/.ssh/id_rsa -q -N "" && \
	cat /root/.ssh/id_rsa.pub >> /root/.ssh/authorized_keys && \
	/usr/sbin/sshd -h /root/.ssh/id_rsa -q && \
	mkdir /home/data && \
	mkdir /home/data/zookeeper && \
	mkdir /home/data/nn1 /home/data/nn2 /home/data/dn1 && \
	chown  -R 777 /home/data && \
	cd /opt/ && \
	wget https://archive.apache.org/dist/hadoop/core/hadoop-2.9.1/hadoop-2.9.1.tar.gz && \
	wget https://archive.apache.org/dist/zookeeper/zookeeper-3.4.13/zookeeper-3.4.13.tar.gz && \
	tar -xvf hadoop-2.9.1.tar.gz && \
	tar -xvf zookeeper-3.4.13.tar.gz && \
	ln -s /opt/hadoop-2.9.1 /opt/hadoop && \
	ln -s /opt/zookeeper-3.4.13 /opt/zookeeper && \
	rm -f *.gz 
	
FROM img2 AS img3

ENV HADOOP_HOME=/opt/hadoop \
	JAVA_HOME=/usr/lib/jvm/jre-1.8.0-openjdk \
	ZOOKEEPER_HOME=/opt/zookeeper
ENV HADOOP_MAPRED_HOME=$HADOOP_HOME \
	HADOOP_COMMON_HOME=$HADOOP_HOME \
	HADOOP_HDFS_HOME=$HADOOP_HOME \
	YARN_HOME=$HADOOP_HOME \
	HADOOP_CONF_DIR=$HADOOP_HOME/etc/hadoop \
	YARN_CONF_DIR=$HADOOP_HOME/etc/hadoop \
	PATH=$PATH:$JAVA_HOME/bin:$HADOOP_HOME/bin:$HADOOP_HOME/sbin:$ZOOKEEPER_HOME/bin

FROM img3 AS img4

RUN mkdir /home/data/datanode/ && \
	chmod 755 /home/data/datanode/

COPY core-site.xml /opt/hadoop/etc/hadoop/core-site.xml
COPY hdfs-site.xml /opt/hadoop/etc/hadoop/hdfs-site.xml
COPY zoo.cfg /opt/zookeeper/conf/zoo.cfg


