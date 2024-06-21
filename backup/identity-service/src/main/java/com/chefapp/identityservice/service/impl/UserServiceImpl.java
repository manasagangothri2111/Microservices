package com.chefapp.identityservice.service.impl;

import com.chefapp.identityservice.repository.UserRepository;
import com.chefapp.identityservice.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;
	@Override
	public UserDetailsService userDetailsService(){
		return new UserDetailsService() {
			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found !!!"));
			}
		};
	}

	@Service
	public static class JWTServiceImpl {

		public String generateToken(UserDetails userDetails){
			return Jwts.builder()
					.subject(userDetails.getUsername())
					.issuedAt(new Date(System.currentTimeMillis()))
					.expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
					.signWith(getSignKey(), SignatureAlgorithm.HS256)
					.compact();
		}

		public String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails){
			return Jwts.builder()
					.claims(extraClaims)
					.subject(userDetails.getUsername())
					.issuedAt(new Date(System.currentTimeMillis()))
					.expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 ))
					.signWith(getSignKey(), SignatureAlgorithm.HS256)
					.compact();
		}

		private <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
			final Claims claims = extractAllClaims(token);
			return claimsResolver.apply(claims);
		}

		private Claims extractAllClaims(String token) {
			return Jwts.parser().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
		}

		public String extractUserName(String token){
			return extractClaim(token, Claims::getSubject);
		}

		private Key getSignKey() {
			byte[] key = Decoders.BASE64.decode("8523698521478569874563214587532569854769321458756985647315987582");
			return Keys.hmacShaKeyFor(key);
		}

		public boolean isTokenValid(String token, UserDetails userDetails){
			final String username = extractUserName(token);
			return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
		}

		private boolean isTokenExpired(String token) {
			return extractClaim(token, Claims::getExpiration).before(new Date());
		}
	}
}
