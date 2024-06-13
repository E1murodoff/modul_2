package org.example;

import org.example.controller.dto.StudentsCreateService;
import org.example.controller.dto.TeachersCreateService;
import org.example.model.Students;
import org.example.model.Teachers;
import org.example.service.StudentsService;
import org.example.service.Teacherservice;

import java.util.*;

public class Main {
    static Scanner scannerStr = new Scanner(System.in);
    static Scanner scannerInt = new Scanner(System.in);

    public static void main(String[] args) {
        Teacherservice teacherservice = new Teacherservice();
        StudentsService studentsService = new StudentsService(teacherservice);
        int stepCode = 10;

        while (stepCode != 0) {
            System.out.println("1. Add Teacher 2. List Teachers 3. Delete Teacher 4. Add Student 5. List Students by Teacher ID 6. List All Students 7. Mark Attendance 8.Attendance table 0. Exit");
            stepCode = scannerInt.nextInt();

            switch (stepCode) {
                case 1 -> {
                    TeachersCreateService teachersCreateService = new TeachersCreateService();
                    System.out.println("Enter name: ");
                    teachersCreateService.setTeacherName(scannerStr.nextLine());
                    System.out.println("Enter surname: ");
                    teachersCreateService.setTeacherSurname(scannerStr.nextLine());
                    System.out.println("Enter degree: ");
                    teachersCreateService.setDegree(scannerStr.nextLine());
                    teacherservice.add("currentTeacher", teachersCreateService);
                }
                case 2 -> {
                    for (Teachers teacher : teacherservice.getList()) {
                        System.out.println("Name: " + teacher.getName() + ", Surname: " + teacher.getSurname() + ", Degree: " + teacher.getDegree());
                    }
                }
                case 3 -> {
                    Map<Integer, UUID> teacherMap = new HashMap<>();
                    int key = 1;
                    for (Teachers teacher : teacherservice.getList()) {
                        System.out.println(key + ". " + teacher.getName());
                        teacherMap.put(key++, teacher.getId());
                    }
                    System.out.println("Select the teacher to delete:");
                    int choice = scannerInt.nextInt();
                    teacherservice.deleteById("currentUser", teacherMap.get(choice));
                }
                case 4 -> {
                    StudentsCreateService studentsCreateService = new StudentsCreateService();

                    System.out.println("Choose teacher:");
                    List<Teachers> teacherList = teacherservice.getList();
                    for (int i = 0; i < teacherList.size(); i++) {
                        Teachers teacher = teacherList.get(i);
                        System.out.println((i + 1) + ". " + teacher.getName() + " " + teacher.getSurname());
                    }

                    System.out.println("Select a teacher by entering the number:");
                    int choice = scannerInt.nextInt();

                    if (choice >= 1 && choice <= teacherList.size()) {
                        Teachers selectedTeacher = teacherList.get(choice - 1);

                        System.out.println("Enter name:");
                        studentsCreateService.setStudentName(scannerStr.nextLine());
                        System.out.println("Enter surname:");
                        studentsCreateService.setStudentSurname(scannerStr.nextLine());

                        studentsCreateService.setTeacherId(selectedTeacher.getId());

                        try {
                            studentsService.add(studentsCreateService);
                            System.out.println("Student added successfully.");
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }
                    } else {
                        System.out.println("Invalid choice. Please select a valid teacher.");
                    }
                    break;
                }

                case 5 -> {
                    System.out.println("Choose teacher:");
                    List<Teachers> teacherList = teacherservice.getList();
                    for (int i = 0; i < teacherList.size(); i++) {
                        Teachers teacher = teacherList.get(i);
                        System.out.println((i + 1) + ". " + teacher.getName() + " " + teacher.getSurname());
                    }

                    System.out.println("Select a teacher by entering the number:");
                    int choice = scannerInt.nextInt();

                    if (choice >= 1 && choice <= teacherList.size()) {
                        Teachers selectedTeacher = teacherList.get(choice - 1);

                        UUID teacherId = selectedTeacher.getId();
                        List<Students> studentsByTeacher = studentsService.getStudentsListByTeacherId(teacherId);

                        if (!studentsByTeacher.isEmpty()) {
                            System.out.println("Students of " + selectedTeacher.getName() + " " + selectedTeacher.getSurname() + ":");
                            for (Students student : studentsByTeacher) {
                                System.out.println("Name: " + student.getName() + ", Surname: " + student.getSurname());
                            }
                        } else {
                            System.out.println("No students found for " + selectedTeacher.getName() + " " + selectedTeacher.getSurname());
                        }
                    } else {
                        System.out.println("Invalid choice. Please select a valid teacher.");
                    }
                    break;
                }

                case 6 -> {
                    List<Students> allStudents = studentsService.getStudentsList();
                    for (Students student : allStudents) {
                        System.out.println("Name: " + student.getName() + ", Surname: " + student.getSurname() + ", Teacher ID: " + student.getTeacherId());
                    }
                }


                case 7 -> {
                    System.out.println("Teachers:");
                    List<Teachers> teacherList = teacherservice.getList();
                    for (int i = 0; i < teacherList.size(); i++) {
                        Teachers teacher = teacherList.get(i);
                        System.out.println((i + 1) + ". " + teacher.getName() + " " + teacher.getSurname());
                    }

                    System.out.println("Select a teacher by entering the number:");
                    int choice = scannerInt.nextInt();

                    if (choice >= 1 && choice <= teacherList.size()) {
                        Teachers selectedTeacher = teacherList.get(choice - 1);
                        List<Students> studentsByTeacher = studentsService.getStudentsListByTeacherId(selectedTeacher.getId());

                        for (Students student : studentsByTeacher) {
                            System.out.println("Is " + student.getName() + " " + student.getSurname() + " present? (true (bor)/false (yoq)): ");
                            boolean present = scannerStr.nextBoolean();
                            try {
                                studentsService.markAttendance(student.getId(), present);
                            } catch (IllegalArgumentException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                    } else {
                        System.out.println("Invalid choice. Please select a valid teacher.");
                    }
                }
                case 8 -> {
                    System.out.println("Enter attendance status (true/false): ");
                    boolean presentStatus = scannerStr.nextBoolean();
                    List<Students> studentsByAttendance = studentsService.filterStudentsByAttendance(presentStatus);

                    System.out.println("Students with attendance status " + presentStatus + ":");
                    for (Students student : studentsByAttendance) {
                        System.out.println("Name: " + student.getName() + ", Surname: " + student.getSurname() + ", Teacher ID: " + student.getTeacherId());
                    }
                }

                case 0 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
