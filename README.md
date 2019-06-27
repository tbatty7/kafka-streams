- Before starting up, spin up Kafka broker and Zookeeper in Docker
- The port it expects to find the broker on is 192.168.1.249 because I had to run Docker in VirtualBox
- To run with default profile, just run with IntelliJ or use ./gradlew bootRun
- To run with test profile, run this:
    ./gradlew bootRun -Dspring.profiles.active=test
