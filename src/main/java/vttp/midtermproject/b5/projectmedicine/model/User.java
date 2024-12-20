package vttp.midtermproject.b5.projectmedicine.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class User {
    
    @NotBlank(message="please input your username")
    @Size(min=3, max=55, message="username should be within 3 and 55 characters")
    private String username;
    
    @NotBlank(message="please input your password")
    @Pattern(regexp="(?=.*\\\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}", message="password must contain minimum eight characters, at least one letter and one number")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    

}
