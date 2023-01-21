package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("users")
public class UserController {
	
	@Autowired
	private UserService service;
	
	
	@PostMapping
	@CrossOrigin("http://localhost:4200")
	public ApprovalResponse save(@RequestBody User user)
	{
		ApprovalResponse u=service.saveProduct(user);
		return u;
	}

//	@GetMapping("{id}")
//	public ResponseEntity<ResponseDTO> getId(@PathVariable("id") long id)
//	{
//		ResponseDTO re=service.response(id);
//		return new ResponseEntity<>(re,HttpStatus.OK);
//
//	}
	
	@PostMapping("/rest")
	@CrossOrigin("http://localhost:4200")
	public ResponseEntity<?> saveUserwithrest(@RequestBody User user)
	{
		ResponseEntity<ApprovalResponse> us=service.saveUserwithRest(user);
		return new ResponseEntity<>(us,HttpStatus.CREATED);
		
	}
	@PostMapping("/approval")
	@CrossOrigin("http://localhost:4200")
	public User saveApprovedProduct(@RequestBody User user) {
		return service.saveApprovedProduct(user);
	}
	
	@GetMapping("/{id}")
	@CrossOrigin("http://localhost:4200")

	public User getProductById(@PathVariable Long id) throws NotFoundException  {
		return service.getProductById(id);
	}

	/**
	 * retrieves list of all products
	 * 
	 * @return List<Product> returns list of all products.
	 */
	@GetMapping
	@CrossOrigin("http://localhost:4200")

	public List<User> getProducts() {
		return service.getProducts();
	}
}
