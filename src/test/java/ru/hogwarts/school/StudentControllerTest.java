package ru.hogwarts.school;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() throws Exception {
        Assertions.assertThat(studentController).isNotNull();
    }

    @Test
    public void testCreateStudent() throws Exception {
        Student student1 = new Student();
        Faculty faculty = new Faculty();

        Long facultyId = 3L;
        String facultyName = "Ravenclaw";
        String facultyColor = "Blue";

        Long studentId = (55L);
        String studentName = "Bobby";
        int studentAge = 45;

        student1.setId(studentId);
        student1.setName(studentName);
        student1.setAge(studentAge);
        student1.setFaculty(faculty);

        faculty.setId(facultyId);
        faculty.setName(facultyName);
        faculty.setColor(facultyColor);

        Assertions
                .assertThat(this.restTemplate.postForObject
                        ("http://localhost:" + port + "/student", student1, String.class))
                .contains("name", studentName);
    }

    @Test
    public void testGetStudentById() throws Exception {

        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/1", Student.class))
                .isInstanceOf(Student.class);
    }


    @Test
    public void testGetStudentByAge() throws Exception {

        Student student1 = new Student();
        Faculty faculty = new Faculty();

        Long facultyId = 3L;
        String facultyName = "Ravenclaw";
        String facultyColor = "Blue";

        Long studentId = (55L);
        String studentName = "Bobby";
        int studentAge = 45;

        student1.setId(studentId);
        student1.setName(studentName);
        student1.setAge(studentAge);
        student1.setFaculty(faculty);

        faculty.setId(facultyId);
        faculty.setName(facultyName);
        faculty.setColor(facultyColor);


        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/age/45", String.class))
                .contains("age", "45");

    }

    @Test
    public void testGetStudentByAgeBetweenMinAndMax() throws Exception {

        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/?minAge=23&maxAge=55",
                        String.class))
                .contains("name", "Bobby");
    }

    @Test
    public void testGetStudentFaculty() throws Exception {

        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/faculty/3", String.class))
                .contains("name", "Bobby");
    }

    @Test
    public void testfindFacultyByStudentName() throws Exception {

        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/student/Bobby", String.class))
                .contains("name", "Ravenclaw");
    }


}
