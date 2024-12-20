package vttp.midtermproject.b5.projectmedicine.service;

import java.io.StringReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import vttp.midtermproject.b5.projectmedicine.model.User;
import vttp.midtermproject.b5.projectmedicine.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    public void createUser(User user) {
        String userString = userToJsonString(user);
        repo.addUser(user.getUsername(), userString);
    }

    public Boolean checkIfUserExist(User user){
        return repo.checkUserExistence(user.getUsername());
    }

    public Boolean checkPassword(String username, String password){
        if (repo.getPassword(username).equals(password)){
            return true;
        } else {
            return false;
        }
    }

    public String userToJsonString(User user) {
        JsonObject obj = Json.createObjectBuilder()
                .add("username", user.getUsername())
                .add("password", user.getPassword())
                .build();

        return obj.toString();
    }

    public User jsonToUser(String json) {
        JsonObject obj = Json.createReader(new StringReader(json)).readObject();
        User user = new User();
        user.setUsername(obj.getString("username"));
        user.setPassword(obj.getString("password"));

        return user;
    }
}
