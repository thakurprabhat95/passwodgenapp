package com.passwordgen.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassGenResponse {
	private String password;
	private String message;
	private boolean success;
}
