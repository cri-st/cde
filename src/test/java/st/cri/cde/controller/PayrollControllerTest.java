package st.cri.cde.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import st.cri.cde.dto.ScribeDto;
import st.cri.cde.service.ScribesCollegeService;

/**
 * The Payroll controller test.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("PayrollController")
class PayrollControllerTest {

  @Mock
  private static ScribesCollegeService service;
  @Mock
  private static Model model;
  @InjectMocks
  private static PayrollController controller;

  /**
   * Query should return query view.
   */
  @Test
  @DisplayName("Should return the query view")
  void queryShouldReturnQueryView() {
    final String view = controller.query(model);
    assertEquals("query", view, "the view didn't match!");
  }

  /**
   * Run query when cuit is valid should return query view.
   */
  @Test
  @DisplayName("Should return the query view when the cuit is valid")
  void runQueryWhenCuitIsValidShouldReturnQueryView() {
    final ScribeDto scribe = new ScribeDto();
    scribe.setCuit("20-26157300-9");
    final String result = controller.runQuery(scribe, model);
    assertEquals("query", result, "the view didn't match!");
  }

  /**
   * Run query when cuit is not a number then return query page.
   */
  @Test
  @DisplayName("Should return the query page when the cuit is not a number")
  void runQueryWhenCuitIsNotANumberThenReturnQueryPage() {
    final ScribeDto scribe = new ScribeDto();
    scribe.setCuit("asd");
    final String result = controller.runQuery(scribe, model);
    assertEquals("query", result, "the view didn't match!");
  }

  /**
   * Run query when cuit is a number then return query page with scribe.
   */
  @Test
  @DisplayName("Should return the query page with the scribe when the cuit is a number")
  void runQueryWhenCuitIsANumberThenReturnQueryPageWithScribe() {
    final ScribeDto scribe = new ScribeDto();
    scribe.setCuit("20-12345678-9");
    when(service.getScribe(scribe.getCuit())).thenReturn(scribe);
    final String result = controller.runQuery(scribe, model);
    assertEquals("query", result, "the view didn't match!");
    verify(model, times(1)).addAttribute("scribe", scribe);
  }

  /**
   * Gets scribe when cuit is invalid then throw exception.
   */
  @Test
  @DisplayName("Should throw an exception when the cuit is invalid")
  void getScribeWhenCuitIsInvalidThenThrowException() {
    final ScribeDto scribe = new ScribeDto();
    scribe.setCuit("asd");
    assertThrows(NumberFormatException.class,
        () -> controller.getScribe(Long.parseLong(scribe.getCuit())));
  }

  /**
   * Gets scribe when cuit is valid.
   */
  @Test
  @DisplayName("Should return the scribe when the cuit is valid")
  void getScribeWhenCuitIsValid() {
    Long cuit = 20123456789L;
    final ScribeDto scribe = new ScribeDto();
    scribe.setCuit(cuit.toString());
    when(controller.getScribe(cuit)).thenReturn(scribe);
    final ScribeDto result = controller.getScribe(cuit);
    assertEquals(scribe.getCuit(), result.getCuit(), "the cuit didn't match!");
  }
}