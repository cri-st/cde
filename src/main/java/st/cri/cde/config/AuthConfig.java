package st.cri.cde.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * The Authentication configuration.
 */
@Data
@Configuration
@ConfigurationProperties("auth-config")
public class AuthConfig {
  private String url;
  private String path;
  private String issuer;
  private String subject;
  private String secret;
  private String audience;
  private String role;
}
