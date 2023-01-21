package com.example.demo;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApprovalResponse {
	
	public String approvalStatus;
	public String message;
}
