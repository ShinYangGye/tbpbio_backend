package com.meta.dto.account.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountJoinReqDto {

	private String email;
	private String password;
	private String name;
	private String role;
	
}
