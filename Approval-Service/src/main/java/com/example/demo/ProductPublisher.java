package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;


@Service
public class ProductPublisher {
	
	@Autowired
	public KafkaTemplate<String, User> kafkaTemplate;

	public void publishApprovedProduct(User user) {
//		String name = SecurityContextHolder.getContext().getAuthentication().getName();
//		user.setLastModifiedBy(name);
		Message<User> message = MessageBuilder.withPayload(user)
				.setHeader(KafkaHeaders.TOPIC, "t-approved-products").build();
		kafkaTemplate.send(message);
	}
}
