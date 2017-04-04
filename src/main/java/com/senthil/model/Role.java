package com.senthil.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Role {

	private Long id;

	private String name;

	private boolean active;

	private LocalDate createdDate;

	private LocalDate modifiedDate;

}
