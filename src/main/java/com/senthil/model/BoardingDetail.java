package com.senthil.model;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class BoardingDetail {

	private Long id;
	private Route route;
	private String name;
	@JsonFormat(pattern = "KK:mm a")
	private LocalTime pickUpTime;
	private boolean active;
}
