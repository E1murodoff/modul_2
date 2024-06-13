package org.example.fileUtill;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.experimental.UtilityClass;
import org.example.model.Students;
import org.example.model.Teachers;

import java.io.*;
import java.util.List;

@UtilityClass
public class FileUtils {
    private static final File parentFile = new File("C:\\Users\\Asus\\Desktop\\Teachers\\src\\main\\java\\org\\example\\gsonFiles");
    private static final Gson gson = new Gson();

    public void writeTeachers(List<Teachers> teachers) throws IOException {
        try (FileWriter fileWriter = new FileWriter(new File(parentFile, "Teachers.json"))) {
            fileWriter.write(gson.toJson(teachers));
        }
    }

    public List<Teachers> readTeachers() throws IOException {
        try (InputStream inputStream = new FileInputStream(new File(parentFile, "Teachers.json"))) {
            byte[] bytes = inputStream.readAllBytes();
            return gson.fromJson(new String(bytes), new TypeToken<List<Teachers>>() {}.getType());
        }
    }

    public void writeStudents(List<Students> students) throws IOException {
        try (FileWriter fileWriter = new FileWriter(new File(parentFile, "Students.json"))) {
            fileWriter.write(gson.toJson(students));
        }
    }

    public List<Students> readStudents() throws IOException {
        try (InputStream inputStream = new FileInputStream(new File(parentFile, "Students.json"))) {
            byte[] bytes = inputStream.readAllBytes();
            return gson.fromJson(new String(bytes), new TypeToken<List<Students>>() {}.getType());
        }
    }
}