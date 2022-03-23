package com.franktran.springboot.springbootexception.student;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

  private final StudentService studentService;

  public StudentController(StudentService studentService) {
    this.studentService = studentService;
  }

  @GetMapping
  public ResponseEntity<List<Student>> getStudents() {
    return new ResponseEntity<>(studentService.getStudents(), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Student> getStudentById(@PathVariable long id) {
    return new ResponseEntity<>(studentService.getStudentById(id), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<Void> createStudent(@RequestBody Student student) {
    studentService.createStudent(student);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @PutMapping("{id}")
  public ResponseEntity<Void> updateStudent(@PathVariable Long id, @RequestBody Student student) {
    studentService.updateStudent(id, student);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
    studentService.deleteStudent(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping("/search")
  public ResponseEntity<Student> searchStudent(@RequestParam String email) {
    return new ResponseEntity<>(studentService.searchStudent(email), HttpStatus.OK);
  }

  @GetMapping("/throw")
  public ResponseEntity<Void> throwException() {
    throw new IllegalStateException("Error when processing...");
  }
}
