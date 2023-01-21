package com.example.demo;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;


import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductConsumer {
	
	private ApprovalService approvalService;
	
	@KafkaListener(topics = "t-product-approval", groupId = "approval-cg", concurrency = "3")
	public void consumeProduct(Message<User> user) {
		User savedProduct = approvalService.saveUser(user.getPayload());
	}
}
