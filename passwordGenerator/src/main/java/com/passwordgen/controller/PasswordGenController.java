package com.passwordgen.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.passwordgen.dto.PassGenRequest;
import com.passwordgen.dto.PassGenResponse;
import com.passwordgen.service.PasswordGenService;

@RestController
@RequestMapping("/api/v1")
public class PasswordGenController {

	private final PasswordGenService passwordGenService;

	public PasswordGenController(PasswordGenService passwordGenService) {
		this.passwordGenService = passwordGenService;
	}

	@PostMapping("/genRanPassword")
	public ResponseEntity<PassGenResponse> generateRandomPass(@RequestBody PassGenRequest passGenRequest) {
		int passwordLength = passGenRequest.getLength();
		boolean passUseUpper = passGenRequest.isUseUpper();
		boolean passUseDigits = passGenRequest.isUseDigits();
		boolean passUseSpecial = passGenRequest.isUseSpecial();

		// 1.input validation,if length is negative and at least one character type not
		// selected
		// then return HTTP 400 BAD request with a structured error response
		if (passwordLength <= 0 || (!passUseUpper && !passUseDigits && !passUseSpecial)) {
			PassGenResponse errorResponse = new PassGenResponse(null,
					"Invalid Input:Length must be positive and at least one charcater type must be selected", false);
			return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
		}

		// 2.generate password
		try {
			String generatedRandomPassword = passwordGenService.generatePassword(passwordLength, passUseUpper,
					passUseDigits, passUseSpecial);
			// 3.Prepare success response
			PassGenResponse successResponse = new PassGenResponse(generatedRandomPassword,
					"Password Generated Successfully", true);

			// return the structured response with HTTP 200 OK
			return new ResponseEntity<PassGenResponse>(successResponse, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: Handle Internal Error()
			// 4. Handle Internal Error (HTTP 500)
			PassGenResponse internalErrorResponse = new PassGenResponse(null,
					"An internal error occurred during generation.", false);
			return new ResponseEntity<>(internalErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
