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
import st.cri.cde.dto.ScribeDto;
import st.cri.cde.service.ScribesCollegeService;

@Slf4j
@Controller(value = "/")
@AllArgsConstructor
public class PayrollController {

    private ScribesCollegeService scribesCollegeService;

    @GetMapping
    public String query(Model model) {
        final ScribeDto scribe = new ScribeDto();
        model.addAttribute("scribe", scribe);
        return "query";
    }

    @PostMapping
    public String runQuery(@ModelAttribute("scribe") ScribeDto scribe, Model model) {
        ScribeDto result = new ScribeDto();
        try {
            Long.parseLong(scribe.getCuit().replace("-", ""));
            result = scribesCollegeService.getScribe(scribe.getCuit());
        } catch (Exception e) {
            result.setCuit(scribe.getCuit());
            log.error("service error {}", e.getMessage());
        }
        model.addAttribute("scribe", result);
        return "query";
    }

    @GetMapping("/{cuit}")
    @ResponseBody
    public ScribeDto getScribe(@PathVariable("cuit") Long cuit) {
        return scribesCollegeService.getScribe(cuit.toString());
    }
}
