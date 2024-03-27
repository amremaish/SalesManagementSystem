package com.sales.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class TokenUtils {

	private final long TOKEN_VALIDITY = 604800L; // 7 day
	private final String TOKEN_SECRET = "5e258d31b71f666a7f7134776739f14bbff371955c1848450d237be6";

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> clims = new HashMap<>();
		clims.put("sub", userDetails.getUsername());
		clims.put("created", new Date());

		return Jwts.builder().setClaims(clims).setExpiration(getExpriationDate())
				.signWith(SignatureAlgorithm.HS512, TOKEN_SECRET).compact();
	}

	private Date getExpriationDate() {

		return new Date(System.currentTimeMillis() + TOKEN_VALIDITY * 1000);
	}

	public String getUserNameFromToken(String token) {
		Claims claims = getClaims(token);
		return claims.getSubject();

	}

	public boolean isTokenValid(String token, UserDetails userDetails) {
		String username = getUserNameFromToken(token);

		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	private boolean isTokenExpired(String token) {
		Date expiration = getClaims(token).getExpiration();
		return expiration.before(new Date());
	}

	private Claims getClaims(String token) {
		return  Jwts.parser().setSigningKey(TOKEN_SECRET).parseClaimsJws(token).getBody();
	}
}
