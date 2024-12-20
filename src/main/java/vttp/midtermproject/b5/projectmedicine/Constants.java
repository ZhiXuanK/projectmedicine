package vttp.midtermproject.b5.projectmedicine;

import java.util.UUID;

public class Constants {
    
    public static String generateUUID(){
        String id = UUID.randomUUID().toString().substring(0, 8);
        return id;
    }

}
