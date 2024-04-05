package src.model;

import java.util.UUID;

public class Company extends  BaseModel{
    private String name;

    @Override
    public UUID getId() {
        return super.getId();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
