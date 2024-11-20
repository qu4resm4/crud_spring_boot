package med.voll.api.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import med.voll.api.usuarios.Usuario;

@Service
public class TokenService {
	
	@Value("token.secret")
	private String secret;
	
	public String gerarToken(Usuario usuario) {
		try {
		    var algorithm = Algorithm.HMAC256(secret); 
		    return JWT.create()
		        .withIssuer("API Voll.med")
		        .withSubject(usuario.getLogin())
		        .withExpiresAt(dataExpiracao())
		        .sign(algorithm);
		} catch (JWTCreationException exception){
		    // Invalid Signing configuration / Couldn't convert Claims.
			throw new RuntimeException("erro ao gerar token");
		}
	}
	
	public String getSubject(String tokenJWT) {
		try {
			var algorithm = Algorithm.HMAC256(secret);
		    return JWT.require(algorithm)
		        // specify any specific claim validations
		        .withIssuer("API Voll.med")
		        // reusable verifier instance
		        .build()
		        .verify(tokenJWT)
		        .getSubject();
		} catch (JWTVerificationException exception){
		    throw new RuntimeException("Token JWT invalido ou expirado!");
		}
	}
	
	private Instant dataExpiracao( ) {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
		
	}
}
