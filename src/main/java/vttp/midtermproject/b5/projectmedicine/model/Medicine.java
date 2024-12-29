package vttp.midtermproject.b5.projectmedicine.model;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class Medicine {
    
    private String name;

    @NotEmpty(message="please input your medication intake schedule")
    private List<String> frequency;
    
    @NotBlank(message="please select if the medication has to be taken before or after food")
    private String food;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @NotNull(message="please input the day at which you start taking the medication")
    private Date startDate;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @NotNull(message="please input the day at which you are supposed to stop taking the medication")
    private Date endDate;

    private List<String> active_ingredients;

    private String adverse_reactions;

    private String UUID;

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String uUID) {
        UUID = uUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getFrequency() {
        return frequency;
    }

    public void setFrequency(List<String> frequency) {
        this.frequency = frequency;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public List<String> getActive_ingredients() {
        return active_ingredients;
    }

    public void setActive_ingredients(List<String> active_ingredients) {
        this.active_ingredients = active_ingredients;
    }

    public String getAdverse_reactions() {
        return adverse_reactions;
    }

    public void setAdverse_reactions(String adverse_reactions) {
        this.adverse_reactions = adverse_reactions;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Medicine [name=" + name + ", frequency=" + frequency + ", food=" + food + ", startDate=" + startDate
                + ", endDate=" + endDate + ", active_ingredients=" + active_ingredients + ", adverse_reactions="
                + adverse_reactions + ", UUID=" + UUID + "]";
    }

    public String toPrintString(){
        return name +","+ frequency.toString().replace(",", ";") +","+  food +","+  startDate +","+  endDate +","+  active_ingredients.toString().replace(",", ";") +","+  adverse_reactions;
    }

    
}
