package vttp.midtermproject.b5.projectmedicine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    //ADD VISIT

    @GetMapping("/add")
    public String getAddVisit(
        Model model,
        HttpSession sess
    ){
        //redirect if user is not logged in or if session expired
        if (sess.getAttribute("username") == null){
            return "redirect:/login";
        }

        model.addAttribute("visit", new Visit());

        return "add_visit";
    }

    @PostMapping(path={"/add"}, consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE)
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

        return "redirect:/dashboard/visit";
    }

    //EDIT VISIT

    @GetMapping("/{UUID}")
    public String getEditVisit(
        @PathVariable String UUID,
        Model model,
        HttpSession sess
    ){
        //redirect if user is not logged in or if session expired
        if (sess.getAttribute("username") == null){
            return "redirect:/login";
        }

        String username = (String) sess.getAttribute("username");

        Visit visit = svc.getVisit(username, UUID);

        model.addAttribute("visit", visit);

        return "edit_visit";

    }

    //no form validation for edit as the two fields that require validation(name + date) cannot be edited
    @PostMapping(path={"/edit"}, consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String postEditVisit(
        @ModelAttribute Visit visit,
        Model model,
        HttpSession sess
    ){
        String username = (String) sess.getAttribute("username");

        visit.setUsername(username);

        svc.addVisit(visit);

        return "redirect:/dashboard/visit";
    }

    //delete visit

    @PostMapping("/delete")
    public String postDeleteVisit(
        @RequestBody MultiValueMap<String, String> form,
        HttpSession sess
    ){
        String username = (String) sess.getAttribute("username");

        String uuid = form.getFirst("UUID");
        svc.deleteVisit(username, uuid);

        return "redirect:/dashboard/visit";
    }

    
}
