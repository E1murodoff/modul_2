package org.example.model;

import lombok.Data;

import java.util.UUID;

@Data
public class Students extends BaseModel{
    private UUID TeacherId;
    private boolean isActive;
    private boolean isPresent;
}
