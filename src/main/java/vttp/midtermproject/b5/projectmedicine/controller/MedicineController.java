package vttp.midtermproject.b5.projectmedicine.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import vttp.midtermproject.b5.projectmedicine.Constants;
import vttp.midtermproject.b5.projectmedicine.model.Medicine;
import vttp.midtermproject.b5.projectmedicine.service.MedicineAPIService;
import vttp.midtermproject.b5.projectmedicine.service.MedicineService;

@Controller
@RequestMapping("/medicine")
public class MedicineController {

    @Autowired
    private MedicineAPIService apisvc;

    @Autowired
    private MedicineService svc;

    @GetMapping("/search")
    public String getMedicine(
        HttpSession sess
    ){
        if (sess.getAttribute("username") == null){
            return "redirect:/login";
        }

        return "search_medicine";
    }

    @GetMapping("/add")
    public String getMedicineSearch(
        @RequestParam String medicine_name,
        Model model,
        HttpSession sess
    ){
        if (sess.getAttribute("username") == null){
            return "redirect:/login";
        }

        if (!apisvc.checkMedicine(medicine_name)){
            model.addAttribute("error", "medicine not found");
            return "search_medicine";
        }

        Medicine medicine = new Medicine();
        medicine.setName(medicine_name);

        model.addAttribute("name", medicine_name);
        model.addAttribute("medicine", medicine);

        return "add_medicine";
    }

    @PostMapping("/add")
    public String addMedicine(
        @RequestBody MultiValueMap<String, String> form,
        @Valid @ModelAttribute Medicine medicine,
        BindingResult bindings,
        Model model,
        HttpSession sess
    ){
        if (bindings.hasErrors()){
            return "add_medicine";
        }

        //populate fields of medicine with external API
        medicine.setActive_ingredients(apisvc.getActiveIngredients(medicine.getName()));
        medicine.setAdverse_reactions(apisvc.getAdverseEffects(medicine.getName()));
        medicine.setUUID(Constants.generateUUID());

        //save to repo
        svc.addMedicine((String)sess.getAttribute("username"), medicine);

        return "redirect:/dashboard";

    }

    //EDIT MEDICINE

    @GetMapping("/{UUID}")
    public String getEditMedicine(
        @PathVariable String UUID,
        Model model,
        HttpSession sess
    ){
        if (sess.getAttribute("username") == null){
            return "redirect:/login";
        }

        String username = (String) sess.getAttribute("username");

        Medicine medicine = svc.getMedicine(username, UUID);

        model.addAttribute("medicine", medicine);

        return "edit_medicine";

    }

    @PostMapping("/edit")
    public String postEditMedicine(
        @ModelAttribute Medicine medicine,
        Model model,
        HttpSession sess
    ){
        String username = (String) sess.getAttribute("username");

        svc.addMedicine(username, medicine);

        return "redirect:/dashboard/medicine";
    }
    
    //DELETE
    @PostMapping("/delete")
    public String postDeleteMedicine(
        @RequestBody MultiValueMap<String, String> form,
        HttpSession sess
    ){
        String username = (String) sess.getAttribute("username");

        String uuid = form.getFirst("UUID");
        svc.deleteMedicine(username, uuid);

        return "redirect:/dashboard/medicine";
    }
    
}
