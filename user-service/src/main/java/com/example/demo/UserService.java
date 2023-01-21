package com.example.demo;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import com.example.kafka.ProductPublisher;

@Service
public class UserService {
	
	
	@Autowired
	private UserRepository repo;
	
//	@Autowired
//	private RestTemplate resttemplate;
	@Autowired
	private ProductPublisher publisher;
	
//	@Autowired
//	private WebClient webclient;
	
	public User saveUser(User user)
	{
		return repo.save(user);
	}
	
	public List<User> getProducts() {
		List<User> products = new ArrayList<>();
		repo.findAll().forEach(products::add);
		return products;
	}

	/**
	 * retrieves product by id
	 * 
	 * @param productId Id of the product to be retrieved
	 * @throws ProductNotFoundException throws, if no product with given id found
	 * @return Product This returns product that matches the given id
	 * @throws NotFoundException 
	 */
	public User getProductById(Long id) throws NotFoundException  {
		Optional<User> optionalProduct = repo.findById(id);
		if (optionalProduct.isPresent()) {
			return optionalProduct.get();
		} else {
			throw new NotFoundException("Product with id: " + id + " not found");
		}
	} 
	
//	public ResponseDTO response(Long id)
//	{
//		ResponseDTO dto=new ResponseDTO();
//		User user=repo.findById(id).get();
//		System.out.println(user);
//		UserDTO u=mapToUser(user);
////		ResponseEntity <DepartmentDTO> responseEntity= 
////				resttemplate.getForEntity("http://localhost:8080/departments/"+ user.getDepartmentId(),
////				DepartmentDTO.class);
////		
////		DepartmentDTO deptdto= responseEntity.getBody();
//		DepartmentDTO webclientdto=webclient.get()
//				.uri("http://localhost:8080/departments/" +user.getDepartmentId())
//				.retrieve().bodyToMono(DepartmentDTO.class).block();
//		dto.setUser(u);
//		dto.setDep(webclientdto);
//		
//		return dto;
//		
//	}
//
//	private UserDTO mapToUser(User user) {
//		UserDTO u=new UserDTO();
//		u.setId(user.getId());
//		u.setEmail(user.getEmail());
//		u.setFirstname(user.getFirstname());
//		u.setLastname(user.getLastname());
//		return u;
//	}
	
	public ResponseEntity<ApprovalResponse> saveUserwithRest(User user)
	{
		RestTemplate restTemplate = new RestTemplateBuilder()
				.rootUri(new UriTemplate("http://localhost:9000").toString()).basicAuthentication("user", "password")
				.build();
		user.setApprovalStatus("Submitted for approval");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<User> request = new HttpEntity<>(user, headers);

		try {
			ResponseEntity<ApprovalResponse> responseEntity = restTemplate.postForEntity("/approvals", request,
					ApprovalResponse.class);
			ApprovalResponse response = responseEntity.getBody();
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
					.body(new ApprovalResponse(e.getLocalizedMessage(), e.getMessage()));
		}
		
	}

	public ApprovalResponse saveProduct(User user) {
		/*
		 * product.setId("123454124"); CosmosClient client = new CosmosClientBuilder()
		 * .endpoint(AccountSettings.HOST) .key(AccountSettings.MASTER_KEY)
		 * .consistencyLevel(ConsistencyLevel.EVENTUAL) .buildClient(); CosmosContainer
		 * container = client.getDatabase("stores").getContainer("product");
		 * CosmosItemRequestOptions options = new CosmosItemRequestOptions();
		 * options.setPreTriggerInclude( Arrays.asList("Trig1") );
		 * CosmosItemResponse<Product> response = container.createItem(product,
		 * options); System.out.println(response.getItem()); return response.getItem();
		 */
		user.setApprovalStatus("Submitted for approval");
	 this.publisher.publishForApproval(user);
		return new ApprovalResponse("Submitted for approval", "Product has been submitted for approval by Admin team");
	}
	
	public User saveApprovedProduct(User user) {
		User savedProduct = repo.save(user);
		return savedProduct;
	}
}
