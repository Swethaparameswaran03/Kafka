package com.example.kafka;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.demo.User;


@Component
public class ProductPublisher {

	@Autowired
	public KafkaTemplate<String, User> kafkaTemplate;

	public void publishForApproval(@Payload User user) {
//		String name = SecurityContextHolder.getContext().getAuthentication().getName();
//		product.setCreatedBy(name);
//		product.setCreatedAt(LocalDateTime.now());
		Message<User> message = MessageBuilder.withPayload(user)
				.setHeader(KafkaHeaders.TOPIC, "t-product-approval").build();
		kafkaTemplate.send(message);
	}
}
