package com.app.gportal.model;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "status")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Status implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -9078311734664681712L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="active")
	private Boolean active;
	
	@Column(name="created_at")
	private Instant createdAt;
	
	@Column(name="updatedAt")
	private Instant updatedAt;

}
