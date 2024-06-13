package org.example.service;

import org.example.controller.dto.TeachersCreateService;
import org.example.fileUtill.FileUtils;
import org.example.model.Students;
import org.example.model.Teachers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Teacherservice {

    List<Teachers> teachersList;
    List<Students> studentsList;

    public Teacherservice() {
        try {
            teachersList = FileUtils.readTeachers();
            if (teachersList == null) {
                teachersList = new ArrayList<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
            teachersList = new ArrayList<>();
        }

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

    public Teachers add(String currentTeacher, TeachersCreateService teachersCreateService) {
        Teachers teachers = new Teachers();
        teachers.setName(teachersCreateService.getTeacherName());
        teachers.setSurname(teachersCreateService.getTeacherSurname());
        teachers.setDegree(teachersCreateService.getDegree());
        teachers.setActive(true);
        teachersList.add(teachers);
        saveTeachers();
        return teachers;
    }

    public Teachers getById(UUID id) {
        for (Teachers teacher : teachersList) {
            if (teacher.getId().equals(id)) {
                return teacher;
            }
        }
        return null;
    }

    public void deleteById(String currentUser, UUID id) {
        for (Teachers teacher : teachersList) {
            if (teacher.getId().equals(id)) {
                teacher.setActive(false);
                deleteStudent(currentUser, id);
                saveTeachers();
                return;
            }
        }
    }

    public List<Teachers> getList() {
        List<Teachers> activeTeachers = new ArrayList<>();
        for (Teachers teacher : teachersList) {
            if (teacher.isActive()) {
                activeTeachers.add(teacher);
            }
        }
        return activeTeachers;
    }

    public void deleteStudent(String currentUser, UUID teacherID) {
        for (Students student : studentsList) {
            if (student.getTeacherId().equals(teacherID)) {
                student.setActive(false);
                saveStudents();
                return;
            }
        }
    }

    public Teachers getTeacherByName(String name) {
        for (Teachers teacher : teachersList) {
            if (teacher.getName().equalsIgnoreCase(name)) {
                return teacher;
            }
        }
        return null;
    }

    private void saveTeachers() {
        try {
            FileUtils.writeTeachers(teachersList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveStudents() {
        try {
            FileUtils.writeStudents(studentsList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
