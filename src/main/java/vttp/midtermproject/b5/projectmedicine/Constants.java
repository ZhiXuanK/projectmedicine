package vttp.midtermproject.b5.projectmedicine;

import java.util.List;
import java.util.UUID;

import vttp.midtermproject.b5.projectmedicine.model.Medicine;
import vttp.midtermproject.b5.projectmedicine.model.Visit;

public class Constants {
    
    public static String generateUUID(){
        String id = UUID.randomUUID().toString().substring(0, 8);
        return id;
    }

    //generate string of comma seperated values with appropriate line seperator
    public static String generateMedCSV(List<Medicine> medicineList){
        String results = "Name" + "," + "Frequency" + "," + "Before or After Food" + "," + "Start Date" + "," + "End Date" + "," + "Active Ingredients" + "," + "Adverse Reactions" + System.lineSeparator();
        for (Medicine med:medicineList){
            results = results + med.toPrintString() + System.lineSeparator();
        }
        return results;
    }

    //generate string of comma seperated values with appropriate line seperator
    public static String generateVisitCSV(List<Visit> visitList){
        String results = "Doctor's Name" + "," + "Date" + "," + "Notes" + System.lineSeparator();
        for (Visit visit:visitList){
            results = results + visit.toPrintString() + System.lineSeparator();
        }
        return results;
    }


}
