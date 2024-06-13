package org.example.service;

import org.example.controller.dto.StudentsCreateService;
import org.example.fileUtill.FileUtils;
import org.example.model.Students;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class StudentsService {
    List<Students> studentsList;
    private Teacherservice teacherservice;

    public StudentsService(Teacherservice teacherservice) {
        this.teacherservice = teacherservice;
        try {
            studentsList = FileUtils.readStudents();
            if (studentsList == null) {
                studentsList = new ArrayList<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
            studentsList = new ArrayList<>();
        }
    }

    public Students add(StudentsCreateService studentsCreateService) {
        if (!teacherExists(studentsCreateService.getTeacherId())) {
            throw new IllegalArgumentException("Teacher with ID " + studentsCreateService.getTeacherId() + " does not exist.");
        }

        Students student = new Students();
        student.setName(studentsCreateService.getStudentName());
        student.setSurname(studentsCreateService.getStudentSurname());
        student.setTeacherId(studentsCreateService.getTeacherId());
        student.setActive(true);
        studentsList.add(student);
        saveStudents();
        return student;
    }

    private boolean teacherExists(UUID teacherId) {
        return teacherservice.getById(teacherId) != null;
    }

    public Students getById(UUID studentId) {
        for (Students student : studentsList) {
            if (student.getId().equals(studentId)) {
                return student;
            }
        }
        return null;
    }

    public List<Students> getStudentsList() {
        List<Students> activeStudents = new ArrayList<>();
        for (Students student : studentsList) {
            if (student.isActive()) {
                activeStudents.add(student);
            }
        }
        return activeStudents;
    }

    public List<Students> getStudentsListByTeacherId(UUID teacherId) {
        List<Students> studentsByTeacher = new ArrayList<>();
        for (Students student : studentsList) {
            if (student.getTeacherId().equals(teacherId)) {
                studentsByTeacher.add(student);
            }
        }
        return studentsByTeacher;
    }


    public void markAttendance(UUID studentId, boolean present) {
        Students student = getById(studentId);
        if (student != null) {
            student.setPresent(present);
            saveStudents();
        } else {
            throw new IllegalArgumentException("Student with ID " + studentId + " not found.");
        }
    }

    public List<Students> filterStudentsByAttendance(boolean present) {
        return studentsList.stream()
                .filter(student -> student.isPresent() == present)
                .collect(Collectors.toList());
    }

    public void deleteStudentsByTeacher(UUID teacherId) {
        List<Students> studentsToDelete = getStudentsListByTeacherId(teacherId);

        for (Students student : studentsToDelete) {
            student.setActive(false);
        }

        saveStudents();
    }

    private void saveStudents() {
        try {
            FileUtils.writeStudents(studentsList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
