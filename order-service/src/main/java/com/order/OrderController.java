package com.order;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
	
	@Autowired
	private KafkaProducer kafkaProducer;
	
	@Autowired
	private OrderService orderService;
	
	@GetMapping("/order")
	public ResponseEntity<String> createOrder(){
		
		kafkaProducer.produceMessage("order-created >>> ");
		
		return ResponseEntity.status(HttpStatus.CREATED).body("order created");
	}
	
	@GetMapping("/order2")
	public ResponseEntity<String> createOrde2r(){
		
		kafkaProducer.producerMessage2("order-created by sachin >>> ");
		
		return ResponseEntity.status(HttpStatus.CREATED).body("order created");
	}
	
	@PostMapping("/order3")
	public ResponseEntity<String> createOrder3(@RequestBody OrderEvent event){
		
		kafkaProducer.producerEvent(event);
		
		return ResponseEntity.status(HttpStatus.CREATED).body("order created with event");
	}
	
	 @PostMapping("/create-order")
	    public String createOrder4() {
	        Orders order = orderService.saveOrder();
	        return "Order Saved : " + order.getId();
	    }
	
}
