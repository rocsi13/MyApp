package myApp.myApp.Controller;

import myApp.myApp.Entity.Candidate;
import myApp.myApp.Entity.User;
import myApp.myApp.Entity.UserDto;
import myApp.myApp.Service.CandidateService;
import myApp.myApp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private CandidateService candidateService;


    public AuthController(UserService userService, CandidateService candidateService) {
        this.userService = userService;
        this.candidateService = candidateService;
    }

    @GetMapping("/index")
    public String home(){
        return "index";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }


    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }


    @PostMapping("/register/save")
    public String registration(@ModelAttribute("user") UserDto user,
                               BindingResult result,
                               Model model){
        User existing = userService.findByEmail(user.getEmail());
        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "register";
        }
        userService.saveUser(user);
        return "login";
    }

    @GetMapping("/all-users")
    public String listRegisteredUsers(Model model){
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "all-users";
    }

    @GetMapping("/homeLogged")
    public String homeUser(Model model) {
        model.addAttribute("candidates", candidateService.findAll());
        return "homeLogged";
    }

    @GetMapping("/registerCandidate")
    public String showCandidateForm(Model model) {
        Candidate candidate = new Candidate();
        model.addAttribute("candidate", candidate);
        return "registerCandidate";
    }

    @PostMapping("/registerCandidate/save")
    public String registrationCandidate(@ModelAttribute("candidate") Candidate candidate,
                                        BindingResult result,
                                        Model model) {
        Candidate existing = candidateService.findByEmail(candidate.getEmail());
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

    @RequestMapping(value="/all-candidates", method = RequestMethod.GET)
    public String getCandidates(Model model) {
        model.addAttribute("candidates", candidateService.findAll());
        return "all-candidates";
    }


    @PostMapping("/all-candidates")
    public String registerVote(@RequestParam("idCandidate") Long idCandidate, @AuthenticationPrincipal UserDetails user, RedirectAttributes redirectAttributes) {
        if (user != null) {
            User user1 = userService.findByEmail(user.getUsername());
            Candidate candidate = candidateService.getCandidateById(idCandidate);
            if (!user1.getVoted()) {
                Long addVote = candidate.getNoVotes();
                ++addVote;
                candidate.setNoVotes(addVote);
                candidateService.saveCandidate(candidate);
                user1.setVoted(true);
                userService.updateUser(user1.getEmail(), user1);
                redirectAttributes.addFlashAttribute("message", "You have successfully voted");
                return "redirect:/homeLogged";
            } else {
                redirectAttributes.addFlashAttribute("message", "You have already voted");
                return "redirect:/alredyVoted";
            }
        }
        return "redirect:/homeLogged";
    }

}
