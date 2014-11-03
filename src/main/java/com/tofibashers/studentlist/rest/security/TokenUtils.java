package com.tofibashers.studentlist.rest.security;

import org.springframework.security.core.userdetails.UserDetails;


public class TokenUtils
{

	public static String createToken(UserDetails userDetails)
	{
		StringBuilder tokenBuilder = new StringBuilder();
		tokenBuilder.append(userDetails.getUsername());
		tokenBuilder.append(":");
        tokenBuilder.append(userDetails.getPassword());
		return tokenBuilder.toString();
	}



	public static String getUserNameFromToken(String authToken)
	{
		if (null == authToken) {
			return null;
		}

		String[] parts = authToken.split(":");
		return parts[0];
	}


	public static boolean validateToken(String authToken, UserDetails userDetails)
	{
		String[] parts = authToken.split(":");
		String username = parts[0];
        String password = parts[1];

		return (username.equals(userDetails.getUsername()) && password.equals(userDetails.getPassword()));
	}
}