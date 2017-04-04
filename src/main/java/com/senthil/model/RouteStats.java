package com.senthil.model;

import java.time.LocalTime;

import lombok.Data;

@Data
public class RouteStats {

	private Long routeNo;
	private String routeName;
	private LocalTime startTime;
	private LocalTime finishTime;
	private Integer noOfBoardingPoints;
}
