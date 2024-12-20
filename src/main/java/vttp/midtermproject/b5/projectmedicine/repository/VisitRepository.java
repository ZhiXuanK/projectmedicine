package vttp.midtermproject.b5.projectmedicine.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class VisitRepository {
    
    @Autowired @Qualifier("redis-0")
    private RedisTemplate<String, String> template;

    //hset visit username visit
    public void addVisit(String username, String visit){
        template.opsForHash().put("visit",username, visit);
    }

    //hget visit username
    public String getVisit(String username){
        return (String)template.opsForHash().get("visit", username);
    }
}
