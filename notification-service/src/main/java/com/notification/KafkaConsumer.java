package com.notification;

import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

	@KafkaListener(topics = "order-topic", groupId = "my-group")
	public void consume(String message) {
        System.out.println("Message Received : " + message);
    }
	
	@KafkaListener(topics = "new-order", groupId = "my-group")
	public void consume2(String msg) {
		System.out.println("message from order : "+msg);
	}
	
	
	
	@RetryableTopic(attempts = "3", // Initial try + 2 retries
			backoff = @Backoff(delay = 2000)
			)
	
	@KafkaListener(topics = "order-event", groupId = "my-group")
	public void consumeEvent(OrderEvent event) {
		System.out.println("================================");

        System.out.println("Notification Received");
        System.out.println("Order ID : " + event.getOrderId());
        System.out.println("Product : " + event.getProductName());
        System.out.println("Amount : " + event.getAmount());
        System.out.println("================================");
        
        throw new RuntimeException("Failure");
		
		
	}
	
	@DltHandler
	public void dlt(OrderEvent event) {
		System.out.println("---DLT----");
		System.out.println("Product : " + event.getProductName());
		
	}
	
	
}
