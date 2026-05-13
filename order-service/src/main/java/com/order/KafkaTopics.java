package com.order;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopics {
	
	@Bean
	public NewTopic orderTopic() {
		return TopicBuilder.name("new-order").partitions(3).replicas(1).build();
	}
	
	@Bean
	public NewTopic orderTopic2() {
		return TopicBuilder.name("order-event").partitions(3).replicas(1).build();
	}
	
	@Bean
	public NewTopic orderTopic3() {
		return TopicBuilder.name("order-event-3").partitions(3).replicas(1).build();
	}
	
}
