package com.order;

import java.util.List;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OutboxPublisher {
		
	private final OutboxRepo outboxRepo;
	private final KafkaTemplate<String, OrderEvent> kafkaTemplate;
	private final ObjectMapper objectMapper;
	
	
	@Scheduled(fixedRate = 5000)
	public void publishEvent() {
		
		List<OutboxEvent> events = outboxRepo.findByProcessedFalse();
		
		for(OutboxEvent event : events) {
			
			OrderEvent orderEvent = null;
			try {
				orderEvent = objectMapper.readValue(event.getPayload(), OrderEvent.class);
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			
			//kafkaTemplate.send("order-event-3", orderEvent);
			
			//idempotent
			
//			kafkaTemplate.send( "order-event-3", orderEvent ).whenComplete((result, ex) -> {
//
//			    if (ex == null) { System.out.println("Message Sent Successfully");
//
//			    } else {
//
//			        System.out.println("Message Failed");
//			    }
//			});
			
			
			//kafka transaction
			
			kafkaTemplate.executeInTransaction(operations -> {operations.send("order-event-3",orderEvent);

                        event.setProcessed(true);

                        outboxRepo.save(event);

                        return true;
                    }
            );
			
			event.setProcessed(true);
			outboxRepo.save(event);
			System.out.println("Event Published : "+event.getId());
			
		}
	}
}
