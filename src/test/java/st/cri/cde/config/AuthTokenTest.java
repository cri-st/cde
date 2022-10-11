package st.cri.cde.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * The Auth token test.
 */
@ExtendWith(MockitoExtension.class)
class AuthTokenTest {

  @Mock
  private static AuthConfig authConfig;

  @InjectMocks
  private static AuthToken authToken;

  /**
   * Sets up.
   */
  @BeforeEach
  public void setUp() {
    when(authConfig.getSecret()).thenReturn("very-long-secret-key-for-test-R96UrtJm");
    when(authConfig.getRole()).thenReturn("role");
    when(authConfig.getSubject()).thenReturn("subject");
    when(authConfig.getAudience()).thenReturn("audience");
    when(authConfig.getIssuer()).thenReturn("issuer");
  }

  /**
   * Gets token should return a token with the correct role.
   */
  @Test
  @DisplayName("Should return a token with the correct role")
  void getTokenShouldReturnATokenWithTheCorrectRole() {
    String token = authToken.getToken();
    Claims claims =
        Jwts.parserBuilder()
            .setSigningKey(authConfig.getSecret().getBytes()).build()
            .parseClaimsJws(token.substring(7))
            .getBody();
    assertEquals("role", claims.get("role"), "role didn't match!");
  }

  /**
   * Gets token should return a token with the correct subject.
   */
  @Test
  @DisplayName("Should return a token with the correct subject")
  void getTokenShouldReturnATokenWithTheCorrectSubject() {
    String token = authToken.getToken();
    Claims claims =
        Jwts.parserBuilder()
            .setSigningKey(authConfig.getSecret().getBytes()).build()
            .parseClaimsJws(token.replace("Bearer ", ""))
            .getBody();
    assertEquals(claims.getSubject(), authConfig.getSubject(), "subject didn't match!");
  }

  /**
   * Create jwt should return token with correct audience.
   */
  @Test
  @DisplayName("Should return a token with the correct audience")
  void createJwtShouldReturnTokenWithCorrectAudience() {
    final String token = authToken.createJwt();
    final Claims claims =
        Jwts.parserBuilder()
            .setSigningKey("very-long-secret-key-for-test-R96UrtJm".getBytes()).build()
            .parseClaimsJws(token)
            .getBody();
    assertEquals("audience", claims.getAudience(), "audience didn't match!");
  }

  /**
   * Create jwt should return token with correct subject.
   */
  @Test
  @DisplayName("Should return a token with the correct subject")
  void createJwtShouldReturnTokenWithCorrectSubject() {
    final String token = authToken.createJwt();
    final Claims claims =
        Jwts.parserBuilder()
            .setSigningKey("very-long-secret-key-for-test-R96UrtJm".getBytes()).build()
            .parseClaimsJws(token)
            .getBody();
    assertEquals("subject", claims.getSubject(), "subject didn't match!");
  }
}