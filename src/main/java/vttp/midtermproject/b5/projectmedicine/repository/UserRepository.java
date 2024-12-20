package vttp.midtermproject.b5.projectmedicine.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    @Autowired @Qualifier("redis-0")
    private RedisTemplate<String, String> template;

    //hset login_details username user
    public void addUser(String username, String user){
        template.opsForHash().put("login_details",username, user);
    }

    //hexists login_details username
    public Boolean checkUserExistence(String username){
        return template.opsForHash().hasKey("login_details", username);
    }

    //hget login_details username
    public String getUser(String username){
        return (String)template.opsForHash().get("login_details", username);
    }





}
