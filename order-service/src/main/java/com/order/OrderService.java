package com.order;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
	
	private final OrderRepo orderRepo;
	
	private final OutboxRepo outboxRepo;
	
	private final ObjectMapper objectMapper;
	 
	@Transactional
	public Orders saveOrder() throws JsonProcessingException {	
		Orders order = Orders.builder()
				.productName("apple")
				.amount(20.0)
				.build();
		Orders ord = orderRepo.save(order);
		
		//create order event
		OrderEvent orderEvent = new OrderEvent();
		orderEvent.setOrderId(ord.getId()+"");
		orderEvent.setProductName(ord.getProductName());
		orderEvent.setAmount(ord.getAmount()+"");
		
		//convert Eventr to Json
		String payload = objectMapper.writeValueAsString(orderEvent);
		
		 // SAVE OUTBOX EVENT
		OutboxEvent outbox = new OutboxEvent();
        outbox.setTopic("order-event");
        outbox.setPayload(payload);
        outbox.setProcessed(false);
        outboxRepo.save(outbox);
        
		return ord;

	}
	
}
