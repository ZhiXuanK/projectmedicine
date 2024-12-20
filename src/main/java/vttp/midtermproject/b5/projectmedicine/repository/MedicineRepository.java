package vttp.midtermproject.b5.projectmedicine.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MedicineRepository {
    
    @Autowired @Qualifier("redis-0")
    private RedisTemplate<String, String> template;

    //hset medicine_username UUID medicine
    public void addMedicine(String username, String UUID, String medicine){
        template.opsForHash().put("medicine_" + username, UUID, medicine);
    }

    //hget medicine_username UUID
    public String getMedicine(String username, String UUID){
        return (String)template.opsForHash().get("medicine_" + username, UUID);
    }

    //hgetall medicine_username
    public Map<String, String> getAllMedicine(String username){
        HashOperations<String, String, String> hashOps = template.opsForHash();

        Map<String, String> entries = new HashMap<>();

        Set<String> hashKeys = hashOps.keys("medicine_" + username);

        if (hashKeys != null){
            entries = hashOps.entries("medicine_" + username);
        }
        return entries;
    }

}
