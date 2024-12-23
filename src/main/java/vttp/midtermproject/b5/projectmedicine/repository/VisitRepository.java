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
public class VisitRepository {
    
    @Autowired @Qualifier("redis-0")
    private RedisTemplate<String, String> template;

    //hset visit_username UUID visit
    public void addVisit(String username, String UUID, String visit){
        template.opsForHash().put("visit_" + username, UUID, visit);
    }

    //hgetall visit_username
    public Map<String, String> getAllVisit(String username){
        HashOperations<String, String, String> hashOps = template.opsForHash();

        Map<String, String> entries = new HashMap<>();

        Set<String> hashKeys = hashOps.keys("visit_" + username);

        if (hashKeys != null){
            entries = hashOps.entries("visit_" + username);
        }
        return entries;
    }

    //hget visit_username UUID
    public String getVisit(String username, String UUID){
        return (String)template.opsForHash().get("visit_" + username, UUID);
    }

    //hdel visit_username UUID
    public void deleteVisit(String username, String UUID){
        template.opsForHash().delete("visit_"+username, UUID);
    }
}
