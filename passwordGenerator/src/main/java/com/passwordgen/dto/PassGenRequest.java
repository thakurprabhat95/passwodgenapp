package com.passwordgen.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassGenRequest {
	
	private int length=12;
	private boolean useUpper=true;
	private boolean useDigits=true;
	private boolean useSpecial=false;

}
