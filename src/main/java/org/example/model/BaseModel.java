package org.example.model;

import lombok.Data;
import lombok.Getter;

import java.util.Date;
import java.util.UUID;

@Data
public class BaseModel {
    private String name;
    private String surname;
    private UUID id;
    private boolean active;

    public BaseModel() {
        this.id = UUID.randomUUID();
    }

}
