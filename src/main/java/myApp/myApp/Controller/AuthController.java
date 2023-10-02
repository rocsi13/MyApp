package myApp.myApp.Controller;

import myApp.myApp.Entity.User;
import myApp.myApp.Entity.UserDto;
import myApp.myApp.Service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
@Controller
public class AuthController {
    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/index")
    public String home(){
        System.out.println("home");
        return "index";
    }

    @GetMapping("/login")
    public String loginForm() {
        System.out.println("loginForm");
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
        System.out.println("registration");
        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "register";
        }
        userService.saveUser(user);
        System.out.println("SaveUser");
        return "login";
    }

    @GetMapping("/users")
    public String listRegisteredUsers(Model model){
        System.out.println("listRegisteredUsers");
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }
}
