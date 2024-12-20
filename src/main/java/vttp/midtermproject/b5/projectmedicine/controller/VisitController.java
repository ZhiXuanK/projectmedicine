package vttp.midtermproject.b5.projectmedicine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import vttp.midtermproject.b5.projectmedicine.Constants;
import vttp.midtermproject.b5.projectmedicine.model.Visit;
import vttp.midtermproject.b5.projectmedicine.service.VisitService;

@Controller
@RequestMapping("/visit")
public class VisitController {

    @Autowired
    private VisitService svc;

    @GetMapping("/add")
    public String getAddVisit(
        Model model,
        HttpSession sess
    ){
        if (sess.getAttribute("username") == null){
            return "redirect:/login";
        }

        model.addAttribute("visit", new Visit());

        return "add_visit";
    }

    @PostMapping("/add")
    public String postAddVisit(
        @Valid @ModelAttribute Visit visit,
        BindingResult bindings,
        HttpSession sess
    ){
        if (bindings.hasErrors()){
            return "add_visit";
        }

        visit.setUsername((String)sess.getAttribute("username"));
        visit.setUUID(Constants.generateUUID());

        //add visit into redis
        svc.addVisit(visit);

        return "temporarysuccess";
    }

    
}
