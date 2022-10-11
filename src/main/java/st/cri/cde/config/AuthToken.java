package st.cri.cde.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import javax.crypto.spec.SecretKeySpec;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * The Authentication token.
 */
@Slf4j
@Component
@AllArgsConstructor
public class AuthToken {

  private AuthConfig authConfig;

  /**
   * Gets token.
   *
   * @return the token
   */
  public String getToken() {
    final String token = createJwt();
    log.info("Generated token : Bearer {}", token);
    return "Bearer " + token;
  }

  private String createJwt() {
    //The JWT signature algorithm we will be using to sign the token
    final SignatureAlgorithm signAlgorithm = SignatureAlgorithm.HS256;

    //We will sign our JWT with our ApiKey secret
    final byte[] apiKeySecretBytes = authConfig.getSecret().getBytes(StandardCharsets.UTF_8);
    final Key signingKey = new SecretKeySpec(apiKeySecretBytes, signAlgorithm.getJcaName());

    final long nowMillis = System.currentTimeMillis();
    final Date now = new Date(nowMillis);

    //Set Role in claims
    final Claims claims = Jwts.claims();
    claims.put("role", authConfig.getRole());

    //Let's set the JWT Claims
    final JwtBuilder builder = Jwts.builder()
        .setClaims(claims)
        .setSubject(authConfig.getSubject())
        .setAudience(authConfig.getAudience())
        .setIssuer(authConfig.getIssuer())
        .signWith(signingKey, signAlgorithm)
        .setIssuedAt(now);

    //Let's add the expiration
    builder.setExpiration(new Date(nowMillis + 100_000));

    //Builds the JWT and serializes it to a compact, URL-safe string
    return builder.compact();
  }
}
