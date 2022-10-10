package st.cri.cde.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import st.cri.cde.dto.ScribeDto;
import st.cri.cde.service.ScribesCollegeService;

@RestController
@AllArgsConstructor
public class PayrollController {

    private ScribesCollegeService scribesCollegeService;

    @GetMapping("/{cuit}")
    public ScribeDto getScribe(@PathVariable("cuit") Long cuit) {
        return scribesCollegeService.getScribe(cuit);
    }
}
