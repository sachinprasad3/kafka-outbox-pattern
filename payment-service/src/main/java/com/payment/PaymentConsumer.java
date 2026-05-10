package com.payment;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PaymentConsumer {
	
	@KafkaListener(groupId = "payment-group", topics = "order-event")
	public void consume(OrderEvent event) {
		
		 System.out.println("================================");

	        System.out.println("Payment Processing Started");

	        System.out.println("Order ID : " + event.getOrderId());

	        System.out.println("Amount : " + event.getAmount());

	        System.out.println("================================");
	        
	}
	
}
