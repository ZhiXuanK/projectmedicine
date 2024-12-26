package vttp.midtermproject.b5.projectmedicine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import vttp.midtermproject.b5.projectmedicine.model.User;
import vttp.midtermproject.b5.projectmedicine.service.UserService;

@Controller
@RequestMapping
public class LoginController {

    @Autowired
    private UserService svc;

    //get login page
    @GetMapping(path={"/", "/login"})
    public String getIndex(
        Model model
    ){
        model.addAttribute("user", new User());

        return "login";
    }

    //form validation
    @PostMapping("/login")
    public String postCreateUser(
        @Valid @ModelAttribute User user,
        BindingResult bindings,
        Model model,
        HttpSession sess
    ){

        if (bindings.hasErrors()){
            return "login";
        }
        //check if username exist
        if (!svc.checkIfUserExist(user)){
            FieldError err = new FieldError("user", "username", "user does not exist. please create an account" );
            bindings.addError(err);
            return "login";
        }

        //check if username and password matches
        if (!svc.checkPassword(user.getUsername(), user.getPassword())){
            FieldError error = new FieldError("user", "password", "wrong password");
            bindings.addError(error);
            return "login";
        }

        //create user
        sess.setAttribute("username", user.getUsername());
        
        return "redirect:/dashboard";
    }

    
}
