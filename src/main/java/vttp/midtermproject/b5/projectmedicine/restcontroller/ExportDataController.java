package vttp.midtermproject.b5.projectmedicine.restcontroller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/export")
public class ExportDataController {

    //export data as pdf that is automatically downloaded when clicked on
/* 
    @Autowired
    private MedicineService medSvc;
    
    @GetMapping(path="/medicine", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> exportMedicine(
        HttpSession sess
    ){
        if (sess.getAttribute("username") == null){
            return ResponseEntity.badRequest().body("Please log in.");
        }

        String username = (String) sess.getAttribute("username");

        List<Medicine> medicineList = (List<Medicine>) medSvc.getAllMedicine(username).values();

        InputStreamReader reader = 



        public ResponseEntity<InputStreamResource> getFile(){
  // figure out what filePath is. filePath=...
  try (FileInputStream fileInputStream = new FileInputStream(filePath)){
    InputStreamResource inputStreamResource = new InputStreamResource(fileInputStream);
    HttpHeaders headers = new HttpHeaders();
    headers.setContentLength(Files.size(Paths.get(filePath)));//optional
    headers.setContentDisposition("attachment", "somefile.pdf"); //optional
    headers.setContentType("application/pdf"); //optional
    return new ResponseEntity(inputStreamResource, headers, HttpStatus.OK);
}
    }*/

}
