package vttp.midtermproject.b5.projectmedicine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import vttp.midtermproject.b5.projectmedicine.model.User;
import vttp.midtermproject.b5.projectmedicine.service.UserService;

@Controller
@RequestMapping
public class CreateUserController {

    @Autowired
    private UserService svc;
    
    //get create user page
    @GetMapping("/createuser")
    public String getCreateUser(
        Model model
    ){
        model.addAttribute("user", new User());
        return "create_user";
    }

    //form validation
    @PostMapping("/createuser")
    public String postCreateUser(
        @Valid @ModelAttribute User user,
        BindingResult bindings,
        @RequestBody MultiValueMap<String, String> form, 
        Model model
    ){
        //check for errors
        if (bindings.hasErrors()){
            return "create_user";
        }

        //check if username exist
        if (svc.checkIfUserExist(user)){
            FieldError err = new FieldError("user", "username", "username taken. please choose another username." );
            bindings.addError(err);
            return "create_user";
        }

        //check if confirm password matches
        if (!form.getFirst("confirm_password").equals(user.getPassword())){
            FieldError error = new FieldError("user", "password", "password does not match");
            bindings.addError(error);
            return "create_user";
        }

        //create user
        svc.createUser(user);
        
        return "login";
    }

}
