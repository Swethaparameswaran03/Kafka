package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;


import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ApprovalService {

	@Autowired
	private UserRepository repository;
	@Autowired
	private ProductPublisher publisher;

	public void approveUser(Long id) {
		User user = repository.findById(id).get();
		user.setApprovalStatus("Approved");
		publisher.publishApprovedProduct(user);
		repository.deleteById(id);
	}

	public void approveUserRest(Long id) {
		RestTemplate restTemplate = new RestTemplateBuilder()
				.basicAuthentication("user", "password")
				.rootUri(new UriTemplate("http://localhost:8081/users").toString()).build();
		User user = repository.findById(id).get();
		user.setApprovalStatus("Approved");
		restTemplate.postForEntity("/approval", user, User.class);
		repository.deleteById(id);
	}
	
	public void rejectUserRest(Long id) {
		RestTemplate restTemplate = new RestTemplateBuilder().basicAuthentication("user", "password")
				.rootUri(new UriTemplate("http://localhost:8081/users").toString()).build();
		User user = repository.findById(id).get();
		user.setApprovalStatus("Rejected");
		restTemplate.postForEntity("/approval", user, User.class);
		repository.deleteById(id);
	}

	public List<User> getAllUsersForApproval() {
		List<User> Users = new ArrayList<>();
		repository.findAll().forEach(Users::add);
		return Users;
	}

	public void rejectUser(Long id) {
		User user = repository.findById(id).get();
		user.setApprovalStatus("Rejected");
		publisher.publishApprovedProduct(user);
		repository.deleteById(id);
	}

	public User saveUser(User User) {
		return repository.save(User);
	}

}
