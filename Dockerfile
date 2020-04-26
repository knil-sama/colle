FROM ubuntu:18.04
ENV COLLE_USER=colle
RUN groupadd -r $COLLE_USER && useradd -m -g $COLLE_USER $COLLE_USER
WORKDIR /home/$COLLE_USER/
ENV MAVEN_VERSION=3.6.0
ENV SPARK_VERSION=2.4.3
ENV SPARK_HOME=/home/$COLLE_USER/spark-${SPARK_VERSION}-bin-hadoop2.8
ENV PATH=/home/$COLLE_USER/apache-maven-${MAVEN_VERSION}/bin:$PATH
#Don't work with default jdk
RUN apt-get update && apt-get install -y wget  openjdk-8-jdk
RUN wget --output-document=apache-maven-${MAVEN_VERSION}-bin.tar.gz https://aws-glue-etl-artifacts.s3.amazonaws.com/glue-common/apache-maven-${MAVEN_VERSION}-bin.tar.gz && tar -xvf apache-maven-${MAVEN_VERSION}-bin.tar.gz && rm apache-maven-${MAVEN_VERSION}-bin.tar.gz
RUN wget --output-document=spark-${SPARK_VERSION}-bin-hadoop2.8.tgz  https://aws-glue-etl-artifacts.s3.amazonaws.com/glue-1.0/spark-${SPARK_VERSION}-bin-hadoop2.8.tgz && tar -xvf spark-${SPARK_VERSION}-bin-hadoop2.8.tgz && rm spark-${SPARK_VERSION}-bin-hadoop2.8.tgz
COPY colle/ colle/
RUN cd colle && mvn clean install
RUN cd colle && mvn exec:java -e  -Dexec.mainClass="com.amazonaws.GlueApp" -Dexec.args="--JOB-NAME test"
