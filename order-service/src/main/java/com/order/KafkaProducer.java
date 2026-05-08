package com.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
    @Autowired
    private KafkaTemplate<String, OrderEvent> kafkaTemplate2;
	
	public void produceMessage(String msg) {
		kafkaTemplate.send("order-topic", msg);
	}
	
	
	public void producerMessage2(String msg) {
		kafkaTemplate.send("new-order", msg);
	}


	public void producerEvent(OrderEvent event) {
		kafkaTemplate2.send("order-event", event);
		
	}
	
}
