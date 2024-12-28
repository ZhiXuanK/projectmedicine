package vttp.midtermproject.b5.projectmedicine.service;

import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;

@Service
public class MedicineAPIService {

   @Value("${apikey}")
   private String apikey;
    
   public static final String NDC_API = "https://api.fda.gov/drug/ndc.json";
   public static final String LABEL_API = "https://api.fda.gov/drug/label.json";

   public Boolean checkMedicine(String medicineName){

      String searchParams = "brand_name:" + medicineName + "+generic_name:" + medicineName;

      String url = UriComponentsBuilder
         .fromUriString(NDC_API)
         .queryParam("api_key", apikey)
         .queryParam("search", searchParams)
         .toUriString();

      RequestEntity<Void> req = RequestEntity
         .get(url)
         .accept(MediaType.APPLICATION_JSON)
         .build();

      RestTemplate template = new RestTemplate();

      ResponseEntity<String> resp = null;

      try{
         resp = template.exchange(req, String.class);
         String payload = resp.getBody();
         //System.out.println(payload);
   
         JsonArray activeIngredientsArray = Json.createReader(new StringReader(payload))
            .readObject()
            .getJsonArray("results")
            .getJsonObject(0)
            .getJsonArray("active_ingredients");
         
         if (activeIngredientsArray == null){
            return false;
         } else {
            return true;
         }
      } catch (HttpStatusCodeException ex){
         return false;
      }
   }


   public List<String> getActiveIngredients(String medicineName){

      String searchParams = "brand_name:" + medicineName + "+generic_name:" + medicineName;

      String url = UriComponentsBuilder
         .fromUriString(NDC_API)
         .queryParam("api_key", apikey)
         .queryParam("search", searchParams)
         .toUriString();

      RequestEntity<Void> req = RequestEntity
         .get(url)
         .accept(MediaType.APPLICATION_JSON)
         .build();

      RestTemplate template = new RestTemplate();

      ResponseEntity<String> resp = null;

      List<String> results = new LinkedList<>();

      try{
         resp = template.exchange(req, String.class);
      } catch (HttpStatusCodeException ex){
         results.add("not found");
         return results;
      }

      String payload = resp.getBody();

      JsonArray activeIngredientsArray = Json.createReader(new StringReader(payload))
         .readObject()
         .getJsonArray("results")
         .getJsonObject(0)
         .getJsonArray("active_ingredients");

      //JsonArrayBuilder activeIngredientsBuilder = Json.createArrayBuilder();

      for (int i=0; i < activeIngredientsArray.size(); i++){
         JsonObject obj = activeIngredientsArray.getJsonObject(i);
         //loratidine [10mg]
         String ingredient = obj.getString("name") + " [" + obj.getString("strength") + "]";
         results.add(ingredient);
         //activeIngredientsBuilder.add(ingredient);
      }

      //JsonArray activeIngredients = activeIngredientsBuilder.build();

      return results;
   }



   public String getAdverseEffects(String medicineName){

      String searchParams = "openfda.brand_name:" + medicineName + "+openfda.generic_name:" + medicineName;

      String url = UriComponentsBuilder
         .fromUriString(LABEL_API)
         .queryParam("api_key", apikey)
         .queryParam("search", searchParams)
         .toUriString();

      RequestEntity<Void> req = RequestEntity
         .get(url)
         .accept(MediaType.APPLICATION_JSON)
         .build();

      RestTemplate template = new RestTemplate();

      ResponseEntity<String> resp = null;

      try{
         resp = template.exchange(req, String.class);
      } catch (HttpStatusCodeException ex){
         return "adverse reactions of this medicine is not recorded in database";
      }

      String payload = resp.getBody();

      try {
         String adverseReactions = Json.createReader(new StringReader(payload))
         .readObject()
         .getJsonArray("results")
         .getJsonObject(0)
         .getJsonArray("adverse_reactions")
         .getString(0);
          
         return adverseReactions;

      } catch (Exception e) {

         return "no adverse reactions recorded for this medicine";

      }
   }


}

