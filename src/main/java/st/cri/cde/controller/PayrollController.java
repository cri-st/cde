package st.cri.cde.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import st.cri.cde.dto.ScribeDto;
import st.cri.cde.service.ScribesCollegeService;

/**
 * The Payroll controller.
 */
@Slf4j
@Controller("/")
@AllArgsConstructor
public class PayrollController {

  private ScribesCollegeService service;

  /**
   * Query endpoint.
   *
   * @param model the model
   * @return the string
   */
  @GetMapping
  public String query(final Model model) {
    final ScribeDto scribe = new ScribeDto();
    model.addAttribute("scribe", scribe);
    return "query";
  }

  /**
   * Run query endpoint.
   *
   * @param scribe the scribe
   * @param model  the model
   * @return the string
   */
  @PostMapping
  public String runQuery(@ModelAttribute("scribe") final ScribeDto scribe, final Model model) {
    ScribeDto result;
    try {
      Long.parseLong(scribe.getCuit().replace("-", ""));
      result = service.getScribe(scribe.getCuit());
    } catch (NumberFormatException | HttpClientErrorException e) {
      result = scribe;
      if (log.isErrorEnabled()) {
        log.error("service error {}", e.getMessage());
      }
    }
    model.addAttribute("scribe", result);
    return "query";
  }

  /**
   * Gets scribe endpoint.
   *
   * @param cuit the cuit
   * @return the scribe
   */
  @GetMapping("/{cuit}")
  @ResponseBody
  public ScribeDto getScribe(@PathVariable("cuit") final Long cuit) {
    return service.getScribe(cuit.toString());
  }
}
