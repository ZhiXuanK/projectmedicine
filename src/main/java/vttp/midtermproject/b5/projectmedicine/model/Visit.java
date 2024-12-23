package vttp.midtermproject.b5.projectmedicine.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class Visit {
    
    private String username;

    //doctor's name
    @NotBlank(message="please input doctor's name")
    @Size(min=3, max=128, message="name should be within 3 and 128 characters")
    private String name;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date date;

    private String note;

    private String UUID;

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String uUID) {
        UUID = uUID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Visit [username=" + username + ", name=" + name + ", date=" + date + ", note=" + note + ", UUID=" + UUID
                + "]";
    }

    

}
