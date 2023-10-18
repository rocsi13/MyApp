package myApp.myApp.Controller;

import myApp.myApp.Entity.Candidate;
import myApp.myApp.Service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registerCandidate")
public class CandidateController {
    @Autowired
    private CandidateService candidateService;

    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @GetMapping
    public String showCandidateForm(Model model) {
        Candidate candidate = new Candidate();
        System.out.println("SHow register candidate");
        model.addAttribute("candidate", candidate);
        return "registerCandidate";
    }

    @PostMapping
    public String registrationCandidate(@ModelAttribute("candidate") Candidate candidate,
                                        BindingResult result,
                                        Model model) {
        Candidate existing = candidateService.findByEmail(candidate.getEmail());
        System.out.println("Save candidate");
        if (existing != null) {
            result.rejectValue("email", null, "There is another candidate registered with this email");
        }
        if (result.hasErrors()) {
            model.addAttribute("candidate", candidate);
            return "registerCandidate";
        }
        candidateService.saveCandidate(candidate);
        return "redirect:/homeLogged";
    }

}
