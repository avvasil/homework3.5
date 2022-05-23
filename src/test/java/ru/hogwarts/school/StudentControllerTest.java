package ru.hogwarts.school;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.hogwarts.school.controller.StudentController;
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

    Student student1 = new Student();
    Student student2 = new Student();
    final long id = 1;
    final String name1 = "Bob";
    final String name2 = "Ivan";
    final int age1 = 23;
    final int age2 = 78;

    @Test
    public void testCreateStudent() throws Exception {

        student1.setId(id);
        student1.setName(name1);
        student1.setAge(age1);

        Assertions
                .assertThat(this.restTemplate.postForObject("http://localhost:" + port, student1, Student.class))
                .isNotNull();
    }

    @Test
    public void testGetStudentById() throws Exception {

        student1.setId(id);
        student1.setName(name1);
        student1.setAge(age1);

        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/1", Student.class))
                .isInstanceOf(Student.class);
    }

    @Test
    public void testGetStudentByAge() throws Exception {

        student1.setId(id);
        student1.setName(name1);
        student1.setAge(age1);

        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/age" + "/23", Student.class))
                .isEqualTo(student1);
    }

}
