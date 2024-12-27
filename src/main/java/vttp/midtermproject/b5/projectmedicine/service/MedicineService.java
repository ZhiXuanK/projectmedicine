package vttp.midtermproject.b5.projectmedicine.service;

import java.io.StringReader;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import vttp.midtermproject.b5.projectmedicine.model.Medicine;
import vttp.midtermproject.b5.projectmedicine.repository.MedicineRepository;

@Service
public class MedicineService {

    @Autowired
    private MedicineRepository repo;


    public void addMedicine(String username, Medicine medicine) {
        String med = MedicineToJsonString(medicine);
        repo.addMedicine(username, medicine.getUUID(), med);
    }

    public Medicine getMedicine(String username, String UUID){
        Medicine medicine = jsonToMedicine(repo.getMedicine(username, UUID));
        return medicine;
    }

    public Map<String, Medicine> getAllMedicine(String username) {
        Map<String, String> allMeds = repo.getAllMedicine(username);
        Map<String, Medicine> results = new HashMap<>();

        for (Map.Entry<String,String> e : allMeds.entrySet()){
            Medicine med = jsonToMedicine(e.getValue());
            results.put(e.getKey(), med);
        }

        //result is UUID, medicine
        return results;
    }

    public Map<String, List<Medicine>> getTodayMedicine(List<Medicine> medicineList){
        Date today = new Date();
        
        List<Medicine> morning = new LinkedList<>();
        List<Medicine> afternoon = new LinkedList<>();
        List<Medicine> night = new LinkedList<>();
        List<Medicine> needed = new LinkedList<>();

        for (Medicine m:medicineList){
            //should be taken today
            if (today.after(m.getStartDate()) && today.before(m.getEndDate())){
                //morning
                if (m.getFrequency().contains("morning")){
                    morning.add(m);
                }
                if (m.getFrequency().contains("afternoon")){
                    afternoon.add(m);
                }
                if (m.getFrequency().contains("night")){
                    night.add(m);
                }
                if (m.getFrequency().contains("as needed")){
                    needed.add(m);
                }
            }
        }

        Map<String, List<Medicine>> results = new HashMap<>();
        results.put("morning", morning);
        results.put("afternoon", afternoon);
        results.put("night", night);
        results.put("needed", needed);

        return results;

    }

    public void deleteMedicine(String username, String uuid){
        repo.deleteMedicine(username, uuid);
    }

    public String MedicineToJsonString(Medicine medicine) {

        JsonArrayBuilder frequencyBuilder = Json.createArrayBuilder();
        for (String s:medicine.getFrequency()){
            frequencyBuilder.add(s);
        }
        JsonArray frequency = frequencyBuilder.build();

        JsonArrayBuilder activeIngredientBuilder = Json.createArrayBuilder();
        for (String s:medicine.getActive_ingredients()){
            activeIngredientBuilder.add(s);
        }
        JsonArray activeIngredients = activeIngredientBuilder.build();

        Long startDate = medicine.getStartDate().getTime();
        Long endDate = medicine.getEndDate().getTime();

        JsonObject obj = Json.createObjectBuilder()
                .add("name", medicine.getName())
                .add("frequency", frequency)
                .add("food", medicine.getFood())
                .add("startDate", startDate)
                .add("endDate", endDate)
                .add("active_ingredients", activeIngredients)
                .add("adverse_reactions", medicine.getAdverse_reactions())
                .add("UUID", medicine.getUUID())
                .build();

        return obj.toString();
    }

    public Medicine jsonToMedicine(String json) {

        JsonObject obj = Json.createReader(new StringReader(json)).readObject();
        
        List<String> frequency = new LinkedList<>();
        JsonArray frequencyArray = obj.getJsonArray("frequency");
        for (int i=0; i<frequencyArray.size(); i++){
            frequency.add(frequencyArray.getString(i));
        }

        List<String> activeIngredients = new LinkedList<>();
        JsonArray activeIngredientsArray = obj.getJsonArray("active_ingredients");
        for (int j=0; j<activeIngredientsArray.size(); j++){
            activeIngredients.add(activeIngredientsArray.getString(j));
        }

        Medicine medicine = new Medicine();

        medicine.setName(obj.getString("name"));
        medicine.setFrequency(frequency);
        medicine.setFood(obj.getString("food"));
        medicine.setStartDate(new Date(obj.getJsonNumber("startDate").longValue()));
        medicine.setEndDate(new Date(obj.getJsonNumber("endDate").longValue()));
        medicine.setActive_ingredients(activeIngredients);
        medicine.setAdverse_reactions(obj.getString("adverse_reactions"));
        medicine.setUUID((obj.getString("UUID")));
        
        return medicine;
    }

}
