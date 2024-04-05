package src.model;


import java.util.UUID;

public class BaseModel {
    protected UUID id;

    public BaseModel() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

}
