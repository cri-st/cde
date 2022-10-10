package st.cri.cde.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

@Slf4j
@Component
@AllArgsConstructor
public class AuthToken {

    private AuthConfig authConfig;

    public String getToken() {
        String token = createJWT();
        log.info("Generated token : Bearer {}", token);
        return "Bearer " + token;
    }

    private String createJWT() {
        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        //We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = authConfig.getSecret().getBytes();
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //Set Role in claims
        Claims claims = Jwts.claims();
        claims.put("role", authConfig.getRole());

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder()
                .setClaims(claims)
                .setSubject(authConfig.getSubject())
                .setAudience(authConfig.getAudience())
                .setIssuer(authConfig.getIssuer())
                .signWith(signingKey, signatureAlgorithm)
                .setIssuedAt(now);

        //Let's add the expiration
        builder.setExpiration(new Date(nowMillis + 100000));

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }
}
