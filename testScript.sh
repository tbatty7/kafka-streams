#!/bin/bash
yum install git
curl http://apache.mirrors.tds.net/kafka/2.2.0/kafka_2.12-2.2.0.tgz | tar -xzvf -
cd kafka_2.12-2.2.0/
bin/zookeeper-server-start.sh -daemon config/zookeeper.properties
bin/kafka-server-start.sh -daemon config/server.properties

git clone https://github.com/tbatty7/kafka-streams.git
cd kafka-streams/
./gradlew bootRun
# Still need to send a curl or run a test that sends a post message and asserts on the result.
# Need another process / shell terminal to run that, or figure out how to run multiple tasks in Gradle.
