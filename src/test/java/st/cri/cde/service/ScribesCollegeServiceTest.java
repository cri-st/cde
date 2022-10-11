package st.cri.cde.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.HttpClientErrorException;
import st.cri.cde.config.AuthConfig;
import st.cri.cde.config.AuthToken;
import st.cri.cde.dto.ScribeDto;

/**
 * The Scribes college service test.
 */
@ExtendWith(MockitoExtension.class)
class ScribesCollegeServiceTest {

  @Mock
  private static AuthToken authToken;
  @Mock
  private static AuthConfig authConfig;
  @InjectMocks
  private static ScribesCollegeService service;

  /**
   * Gets scribe when cuit is not a number then throw exception.
   */
  @Test
  @DisplayName("Should throw an exception when the cuit is not a number")
  void getScribeWhenCuitIsNotANumberThenThrowException() {
    final ScribeDto scribe = new ScribeDto();
    scribe.setCuit("asd");
    assertThrows(NumberFormatException.class, () -> service.getScribe(scribe.getCuit()));
  }

  /**
   * Gets scribe when cuit is invalid then throw exception.
   */
  @Test
  @DisplayName("Should throw an exception when the cuit is invalid")
  void getScribeWhenCuitIsInvalidThenThrowException() {
    final String cuit = "20-12345678-9";
    final String url = "https://servicios-testing.colegio-escribanos.org.ar:8444/nomina-escribanos-ws/";
    final String path = "/escribano/estado/{cuit}";
    final String token =
        "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJjYW5kaWRhdG8tcFRTdiIsImlhdCI6MTY2ODEzMjM0NC42ODksImV4cCI6MTY2ODEzMjM0NC42ODksImF1ZCI6Im5vbWluYS1lc2NyaWJhbm9zLXdzIiwic3ViIjoiZXhhbWVuLXRlY25pY28iLCJyb2xlIjpbIlJPTCJdfQ.bhEtX2AvymCpWnrTxw-lVpPWhjsVmDOy9OHy4y4kFq8";

    when(authToken.getToken()).thenReturn(token);
    when(authConfig.getUrl()).thenReturn(url);
    when(authConfig.getPath()).thenReturn(path);

    assertThrows(HttpClientErrorException.class, () -> service.getScribe(cuit),
        "Expected HttpClientErrorException to throw, but it didn't");
  }

  /**
   * Gets scribe when cuit is valid.
   */
  @Test
  @DisplayName("Should return a scribe when the cuit is valid")
  void getScribeWhenCuitIsValid() {
    final String cuit = "20-26157300-9";
    final String url = "https://servicios-testing.colegio-escribanos.org.ar:8444/nomina-escribanos-ws/";
    final String path = "/escribano/estado/{cuit}";
    final String token =
        "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJjYW5kaWRhdG8tcFRTdiIsImlhdCI6MTY2ODEzMjM0NC42ODksImV4cCI6MTY2ODEzMjM0NC42ODksImF1ZCI6Im5vbWluYS1lc2NyaWJhbm9zLXdzIiwic3ViIjoiZXhhbWVuLXRlY25pY28iLCJyb2xlIjpbIlJPTCJdfQ.bhEtX2AvymCpWnrTxw-lVpPWhjsVmDOy9OHy4y4kFq8";

    when(authConfig.getUrl()).thenReturn(url);
    when(authConfig.getPath()).thenReturn(path);
    when(authToken.getToken()).thenReturn(token);

    final ScribeDto scribe = service.getScribe(cuit);

    assertNotNull(scribe, "scribe should not be null");
    assertEquals(cuit, scribe.getCuit(), "cuit didn't match!");
  }
}