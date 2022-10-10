package st.cri.cde.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import st.cri.cde.config.AuthConfig;
import st.cri.cde.config.AuthToken;
import st.cri.cde.dto.ScribeDto;

import java.net.URI;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ScribesCollegeService {
    public static final String URI_PATH = "/escribano/estado/{cuit}";

    private AuthToken authToken;
    private AuthConfig authConfig;

    public ScribeDto getScribe(Long cuit) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.set("Authorization", authToken.getToken());
        HttpEntity<String> entity = new HttpEntity<>(headers);

        URI url = UriComponentsBuilder.fromUriString(authConfig.getUrl()).path(URI_PATH).buildAndExpand(cuit).toUri();
        ResponseEntity<ScribeDto> response = restTemplate.exchange(url, HttpMethod.GET, entity, ScribeDto.class);
        log.info("response {}", response.getBody());

        return response.getBody();
    }
}
