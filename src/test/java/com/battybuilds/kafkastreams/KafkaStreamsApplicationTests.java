package com.battybuilds.kafkastreams;

import com.battybuilds.kafkastreams.avro.model.AvroHttpRequest;
import com.battybuilds.kafkastreams.utils.AvroSerDes;
import com.battybuilds.kafkastreams.utils.MessageStreams;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.test.context.junit4.SpringRunner;

import static com.battybuilds.kafkastreams.utils.RequestUtils.createAvroHttpRequest;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KafkaStreamsApplicationTests {

	@Autowired
	ProducerController producerController;

	@Test
	public void receivesMsgForAwesomeTopicFromKafka() {
		producerController.sendMessage();

	}

}
