package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Collection<Student> findByAge(int age);

    Collection<Student> findStudentByAgeBetween(int minAge, int maxAge);

    Collection<Student> findStudentByFacultyId(Long faculty_id);


}
