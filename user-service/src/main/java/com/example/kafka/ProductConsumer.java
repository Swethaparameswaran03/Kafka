package com.example.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import com.example.demo.User;
import com.example.demo.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductConsumer {
	
	private UserService service;
	
	@KafkaListener(topics = "t-approved-products", groupId = "approved-products-cg", concurrency = "3")
	public void consumeProduct(Message<User> user) {
		User payload = user.getPayload();
		payload.setId(null);
		service.saveApprovedProduct(payload);
	}
}
