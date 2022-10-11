package st.cri.cde.service;

import java.net.URI;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import st.cri.cde.config.AuthConfig;
import st.cri.cde.config.AuthToken;
import st.cri.cde.dto.ScribeDto;

/**
 * The Scribes College's service.
 */
@Slf4j
@Service
@AllArgsConstructor
public class ScribesCollegeService {

  private AuthToken authToken;
  private AuthConfig authConfig;

  /**
   * Gets scribe data.
   *
   * @param cuit the cuit
   * @return the scribe data
   */
  public ScribeDto getScribe(final String cuit) {
    final RestTemplate restTemplate = new RestTemplate();

    final HttpHeaders headers = new HttpHeaders();
    headers.setAccept(List.of(MediaType.APPLICATION_JSON));
    headers.set("Authorization", authToken.getToken());
    final HttpEntity<String> entity = new HttpEntity<>(headers);

    final long formattedCuit = Long.parseLong(cuit.replace("-", ""));

    final URI url = UriComponentsBuilder.fromUriString(authConfig.getUrl())
        .path(authConfig.getPath())
        .buildAndExpand(formattedCuit).toUri();
    final ResponseEntity<ScribeDto> response = restTemplate.exchange(url, HttpMethod.GET, entity,
        ScribeDto.class);
    final ScribeDto scribe = response.getBody();
    assert scribe != null;
    scribe.setCuit(cuit);
    log.info("response {}", scribe);

    return scribe;
  }
}
