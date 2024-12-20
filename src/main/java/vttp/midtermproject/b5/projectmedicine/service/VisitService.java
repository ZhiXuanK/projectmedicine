package vttp.midtermproject.b5.projectmedicine.service;

import java.io.StringReader;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import vttp.midtermproject.b5.projectmedicine.model.Visit;
import vttp.midtermproject.b5.projectmedicine.repository.VisitRepository;

@Service
public class VisitService {
    
    @Autowired
    private VisitRepository repo;

    public void addVisit(Visit visit){
        repo.addVisit(visit.getUsername(), visit.getUUID(), visitToJsonString(visit));
    }

    public String visitToJsonString(Visit visit){

        Long visitDate = visit.getDate().getTime();

        JsonObject obj = Json.createObjectBuilder()
            .add("username", visit.getUsername())
            .add("name", visit.getName())
            .add("date", visitDate)
            .add("note", visit.getNote())
            .add("UUID", visit.getUUID())
            .build();
        
        return obj.toString();
    }

    public Visit jsonToVisit(String json){

        JsonObject obj = Json.createReader(new StringReader(json)).readObject();

        Visit visit = new Visit();

        visit.setUsername(obj.getString("username"));
        visit.setName(obj.getString("name"));
        visit.setDate(new Date(obj.getJsonNumber("date").longValue()));
        visit.setNote(obj.getString("note"));
        visit.setUUID(obj.getString("UUID"));

        return visit;

    }

}
