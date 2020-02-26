- Before starting up, spin up Kafka broker and Zookeeper in Docker
    - To do this, ssh into VM and go into the kafka-docker directory
    and run `docker-compose -f docker-compose-single-broker.yml up`
    - This spins up the Kafka broker and Zookeeper
- The port it expects to find the broker on is 192.168.1.249 because I had to 
run Docker in VirtualBox
- To run with default profile, just run with IntelliJ or use `./gradlew bootRun`
    - default profile does not work with controller to publish message
- To run with test profile, run this:
    `./gradlew bootRun -Dspring.profiles.active=test`
    - Then test it by going to `http://localhost:9000/send` in a browser
