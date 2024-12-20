package vttp.midtermproject.b5.projectmedicine.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/medicine")
public class MedicineController {

    @GetMapping
    public String getMedicine(
        HttpSession sess
    ){
        if (sess.getAttribute("username") == null){
            return "redirect:/login";
        }

        return "search_medicine";
    }

    
    
}
