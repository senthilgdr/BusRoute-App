package com.senthil.model;

import lombok.Data;

@Data
public class UserBoardingDetail {

	private Long id;
	private User user;
	private BoardingDetail boardingDetail;
	private boolean active;
	
}
