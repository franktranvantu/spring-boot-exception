package com.franktran.springboot.springbootexception.student;

import com.franktran.springboot.springbootexception.exception.InvalidInputException;
import com.franktran.springboot.springbootexception.exception.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class StudentService {

  private final StudentRepository studentRepository;

  public StudentService(StudentRepository studentRepository) {
    this.studentRepository = studentRepository;
  }

  public List<Student> getStudents() {
    return studentRepository.findAll();
  }

  public Student getStudentById(long id) {
    return studentRepository.findById(id)
        .orElseThrow(() -> new NotFoundException(String.format("Student with id %d", id)));
  }

  public Student createStudent(Student student) {
    Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());
    if (studentOptional.isPresent()) {
      throw new IllegalArgumentException("email taken");
    }
    return studentRepository.save(student);
  }

  @Transactional
  public Student updateStudent(long studentId, Student student) {
    Student existStudent = studentRepository.findById(studentId)
        .orElseThrow(() -> new NotFoundException(String.format("Student with id %d", studentId)));
    if (Objects.nonNull(student.getName()) && !Objects.equals(existStudent.getName(), student.getName())) {
      existStudent.setName(student.getName());
    }
    if (Objects.nonNull(student.getEmail()) && !Objects.equals(existStudent.getEmail(), student.getEmail())) {
      Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());
      if (studentOptional.isPresent()) {
        throw new IllegalArgumentException("email taken");
      }
      existStudent.setEmail(student.getEmail());
    }

    return studentRepository.save(existStudent);
  }

  public void deleteStudent(long studentId) {
    boolean existsById = studentRepository.existsById(studentId);
    if (!existsById) {
      throw new NotFoundException(String.format("Student with id %d", studentId));
    }
    studentRepository.deleteById(studentId);
  }

  public Student searchStudent(String email) {
    String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
        + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    boolean isValidEmail = Pattern.compile(regexPattern).matcher(email).matches();
    if (!isValidEmail) {
      throw new InvalidInputException(String.format("Email %s", email));
    }
    return studentRepository.findStudentByEmail(email)
        .orElseThrow(() -> new NotFoundException(String.format("Student with email %s", email)));
  }
}
