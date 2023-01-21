package com.example.demo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Data
@Table(name="usertable")
public class User {
	
	@Id
	@GeneratedValue
	private Long id;
	private String firstname;
	private String lastname;
	@Column(nullable=false,unique = true)
	private String email;
//	private String departmentId;
	private String approvalStatus;

}
