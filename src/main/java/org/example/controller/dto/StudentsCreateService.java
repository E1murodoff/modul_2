package org.example.controller.dto;

import lombok.Data;

import java.util.UUID;
@Data
public class StudentsCreateService {
    private String StudentName;
    private String StudentSurname;
    private UUID TeacherId;
}
