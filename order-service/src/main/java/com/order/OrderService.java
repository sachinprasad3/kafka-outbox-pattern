package com.order;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
	
	private final OrderRepo orderRepo;
	
	public Orders saveOrder() {	
		Orders order = Orders.builder()
				.productName("apple")
				.amount(20.0)
				.build();
		return orderRepo.save(order);
		
	}
	
}
