package ru.hogwarts.school;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTestDeleteAndUpdateMethods {


    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @MockBean
    private StudentRepository studentRepository;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    void testUpdateStudent() throws Exception {

        Student student1 = new Student();
        Faculty faculty = new Faculty();

        Long studentId = 1L;
        String studentName = "Bobby";
        int studentAge = 45;

        Long facultyId = 3L;
        String facultyName = "Ravenclaw";
        String facultyColor = "Blue";

        student1.setId(studentId);
        student1.setName(studentName);
        student1.setAge(studentAge);
        student1.setFaculty(faculty);

        faculty.setId(facultyId);
        faculty.setName(facultyName);
        faculty.setColor(facultyColor);

        when(studentRepository.save(any(Student.class))).thenReturn(student1);
        HttpEntity<Student> entity = new HttpEntity<>(student1);

        ResponseEntity<Student> response = this.restTemplate.exchange("http://localhost:" + port + "/student/update/",
                HttpMethod.PUT, entity, Student.class);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void testDeleteStudent() {

        Student student1 = new Student();
        Faculty faculty = new Faculty();

        Long studentId = 22L;
        String studentName = "Bobby";
        int studentAge = 45;

        Long facultyId = 3L;
        String facultyName = "Ravenclaw";
        String facultyColor = "Blue";

        student1.setId(studentId);
        student1.setName(studentName);
        student1.setAge(studentAge);
        student1.setFaculty(faculty);

        faculty.setId(facultyId);
        faculty.setName(facultyName);
        faculty.setColor(facultyColor);

        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student1));

        HttpEntity<String> entity = new HttpEntity<>(null, new HttpHeaders());
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:" + port + "/student/remove/"+ student1.getId(),
                HttpMethod.DELETE, entity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}
