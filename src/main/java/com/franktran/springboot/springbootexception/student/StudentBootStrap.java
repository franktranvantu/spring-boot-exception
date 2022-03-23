package com.franktran.springboot.springbootexception.student;

import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class StudentBootStrap implements CommandLineRunner {

  private final StudentRepository studentRepository;

  public StudentBootStrap(StudentRepository studentRepository) {
    this.studentRepository = studentRepository;
  }

  @Override
  public void run(String... args) throws Exception {
    Faker faker = new Faker();
    List<Student> students = Stream.of(
        new Student(faker.name().name(), "faker1@gmail.com", faker.date().birthday(10, 50).toInstant().atZone(ZoneId.systemDefault()).toLocalDate()),
        new Student(faker.name().name(), "faker2@gmail.com", faker.date().birthday(10, 50).toInstant().atZone(ZoneId.systemDefault()).toLocalDate()),
        new Student(faker.name().name(), "faker3@gmail.com", faker.date().birthday(10, 50).toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
    ).collect(Collectors.toList());

    studentRepository.saveAll(students);
  }
}
