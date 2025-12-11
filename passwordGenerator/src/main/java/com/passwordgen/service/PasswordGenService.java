package com.passwordgen.service;

import java.security.SecureRandom;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

@Service
public class PasswordGenService {
	
	private static final String LOWERCASE = "abcdefghijkmnpqrstuvwxyz";
    private static final String UPPERCASE = "ABCDEFGHJKLMNPQRSTUVWXYZ";
    private static final String DIGITS = "23456789";
    private static final String SPECIAL = "!@#$%^&*()_+[]{}|";
    
    public String generatePassword(int length,boolean useUpper,boolean useDigits,boolean useSpecial)
    {
    	StringBuilder charPool = new StringBuilder(LOWERCASE);
        if (useUpper) charPool.append(UPPERCASE);
        if (useDigits) charPool.append(DIGITS);
        if (useSpecial) charPool.append(SPECIAL);

        if (charPool.isEmpty() || length <= 0) {
            return ""; // Or throw an exception
        }

        SecureRandom random = new SecureRandom();
        
        // Use streams to select random characters
        String password = IntStream.range(0, length)
            .mapToObj(i -> String.valueOf(charPool.charAt(random.nextInt(charPool.length()))))
            .collect(Collectors.joining());

        // Basic shuffling (optional, but good practice)
        return shuffleString(password);
    }

    // Helper method to shuffle the password characters
    private String shuffleString(String input) {
        // Convert string to a list of characters, shuffle, and convert back
        return input.chars()
            .mapToObj(c -> (char) c)
            .collect(Collectors.collectingAndThen(
                Collectors.toList(),
                list -> {
                    Collections.shuffle(list, new SecureRandom());
                    return list.stream().map(String::valueOf).collect(Collectors.joining());
                }
            ));
    }
    }


