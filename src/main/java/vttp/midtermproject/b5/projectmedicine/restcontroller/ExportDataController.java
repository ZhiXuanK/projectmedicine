package vttp.midtermproject.b5.projectmedicine.restcontroller;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import vttp.midtermproject.b5.projectmedicine.Constants;
import vttp.midtermproject.b5.projectmedicine.model.Medicine;
import vttp.midtermproject.b5.projectmedicine.model.Visit;
import vttp.midtermproject.b5.projectmedicine.service.MedicineService;
import vttp.midtermproject.b5.projectmedicine.service.VisitService;


@RestController
@RequestMapping("/export")
public class ExportDataController {

    //export data as pdf that is automatically downloaded when clicked on 
    @Autowired
    private MedicineService medSvc;

    @Autowired
    private VisitService visitSvc;
    
    @GetMapping(path="/medicine", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> exportMedicine(
        HttpSession sess
    ){
        String results = "";

        if (sess.getAttribute("username") == null){
            results = "please log in";
        } else {
            String username = (String) sess.getAttribute("username");

            List<Medicine> medicineList = new LinkedList<>();

            if (medSvc.getAllMedicine(username).size() != 0){
                for (Map.Entry<String, Medicine> en : medSvc.getAllMedicine(username).entrySet()) {
                    Medicine med = en.getValue();
                    medicineList.add(med);
                }
                results = Constants.generateMedCSV(medicineList);
            } else {
                results = "No Medicine History";
            }
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.setContentDisposition(ContentDisposition.builder("attachment").filename("medicine_history.csv").build());


        return ResponseEntity.ok().headers(headers).body(results);         
    }

    @GetMapping(path="/visit", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> exportVisit(
        HttpSession sess
    ){
        String results = "";

        if (sess.getAttribute("username") == null){
            results = "please log in";
        } else {
            String username = (String) sess.getAttribute("username");

            List<Visit> visitList = new LinkedList<>();

            if (visitSvc.getAllVisit(username).size() != 0){
                for (Map.Entry<String, Visit> en : visitSvc.getAllVisit(username).entrySet()) {
                    Visit visit = en.getValue();
                    visitList.add(visit);
                }
                results = Constants.generateVisitCSV(visitList);
            } else {
                results = "No Visit History";
            }
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.setContentDisposition(ContentDisposition.builder("attachment").filename("visit_history.csv").build());


        return ResponseEntity.ok().headers(headers).body(results);         
    }

}
