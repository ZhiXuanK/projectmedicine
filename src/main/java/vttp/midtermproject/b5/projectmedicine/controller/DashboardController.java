package vttp.midtermproject.b5.projectmedicine.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import vttp.midtermproject.b5.projectmedicine.model.Medicine;
import vttp.midtermproject.b5.projectmedicine.model.Visit;
import vttp.midtermproject.b5.projectmedicine.service.MedicineService;
import vttp.midtermproject.b5.projectmedicine.service.VisitService;

@Controller
@RequestMapping("/dashboard")
//dashboard made up of 2 pages, visit + medicine. medicine is first page shown, visit is second
public class DashboardController {

    @Autowired
    private MedicineService medSvc;

    @Autowired
    private VisitService visitSvc;

    @GetMapping(path = {"", "/medicine"})
    public String getDashboard(
        HttpSession sess,
        Model model
    ){
        if (sess.getAttribute("username") == null){
            return "redirect:/login";
        }

        String username = (String) sess.getAttribute("username");

        List<Medicine> medicineList = new LinkedList<>();
        for (Map.Entry<String,Medicine> en : medSvc.getAllMedicine(username).entrySet()) {
            Medicine med = en.getValue();
            medicineList.add(med);
        }

        model.addAttribute("medicineList", medicineList);

        return "dashboard_medicine";
    }

    @GetMapping("/visit")
    public String getVisitDashboard(
        HttpSession sess,
        Model model
    ){
        if (sess.getAttribute("username") == null){
            return "redirect:/login";
        }

        String username = (String) sess.getAttribute("username");

        List<Visit> visitList = new LinkedList<>();

        for(Map.Entry<String,Visit> en: visitSvc.getAllVisit(username).entrySet()){
            Visit visit = en.getValue();
            visitList.add(visit);
        }

        model.addAttribute("visitList", visitList);

        return "dashboard_visit";
    }

    @PostMapping("/logout")
    public String postLogOut(
        HttpSession sess
    ){
        sess.invalidate();

        return "redirect:/";
    }


}
