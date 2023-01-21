package com.example.demo;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/approvals")
@AllArgsConstructor
public class ApprovalController {
	
	private ApprovalService approvalService;
	
	@GetMapping("/approve/{id}")
	@CrossOrigin("http://localhost:4200")
	public ResponseEntity<ApprovalResponse> approveProduct(@PathVariable("id") Long id) {
		approvalService.approveUser(id);
		return ResponseEntity.ok(new ApprovalResponse("Approved","Product with id:" + id + "  approved"));
	}
	
	@GetMapping("/rest/approve/{id}")
	@CrossOrigin("http://localhost:4200")
	public ResponseEntity<ApprovalResponse> approveProductRest(@PathVariable("id") Long id) {
		approvalService.approveUserRest(id);
		return ResponseEntity.ok(new ApprovalResponse("Approved","Product with id:" + id + "  approved"));
	}
	
	@GetMapping("/rest/reject/{id}")
	@CrossOrigin("http://localhost:4200")
	public ResponseEntity<ApprovalResponse> rejectProductRest(@PathVariable("id") Long id) {
		approvalService.rejectUserRest(id);
		return ResponseEntity.ok(new ApprovalResponse("Rejected","Product with id:" + id + "  rejected"));
	}
	
	@PostMapping
	@CrossOrigin("http://localhost:4200")
	public ApprovalResponse submitForApproval(@RequestBody User user) {
		approvalService.saveUser(user);
		return new ApprovalResponse("Submitted for approval", "Product has been received for approval by Admin team");
	}
	
	@GetMapping
	@CrossOrigin("http://localhost:4200")

	public ResponseEntity<List<User>> getAllApprovals(){
		return ResponseEntity.ok(approvalService.getAllUsersForApproval());
	}
	
	@GetMapping("/reject/{id}")
	@CrossOrigin("http://localhost:4200")

	public ResponseEntity<ApprovalResponse> rejectProduct(@PathVariable("id") Long id) {
		approvalService.rejectUser(id);
		return ResponseEntity.ok(new ApprovalResponse("Rejected","Product with id:" + id + "  rejected"));
	}
		
}
