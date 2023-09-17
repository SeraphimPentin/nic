package nicstore.security.jwt;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


import java.time.ZonedDateTime;
import java.util.Date;

@Configuration
public class JwtUtil {

    @Value("${jwt_secret}")
    private String SECRET;

    public String generateToken(String email) {
        Date expirationDate = Date.from(ZonedDateTime.now().plusMinutes(60).toInstant());
        return JWT.create()
                .withSubject("User details")
                .withClaim("email", email)
//                .withIssuedAt(new Date())
                .withIssuer("nic-store")
//                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(SECRET));
    }

    public String validateTokenAndRetrieveToken(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET))
                .withSubject("User details")
                .withIssuer("nic-store")
                .build();

        DecodedJWT jwt =  verifier.verify(token);
        return jwt.getClaim("email").asString();
    }
}
