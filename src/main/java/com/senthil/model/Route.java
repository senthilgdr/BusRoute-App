package com.senthil.model;

import lombok.Data;

@Data
public class Route {

	private Long id;
	private String name;
	private boolean active;
	private String trip;
}
