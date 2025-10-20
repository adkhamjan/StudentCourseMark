package org.example.spring_data_query.controller;

import org.example.spring_data_query.dto.StudentDTO;
import org.example.spring_data_query.dto.StudentFilterDTO;
import org.example.spring_data_query.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @PostMapping("")
    public ResponseEntity<StudentDTO> create(@RequestBody StudentDTO dto) {
        StudentDTO result = studentService.create(dto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("")
    public ResponseEntity<List<StudentDTO>> getAll() {
        List<StudentDTO> result = studentService.getAllStudent();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getById(@PathVariable Integer id) {
        StudentDTO result = studentService.getById(id);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boolean> update(@PathVariable Integer id, @RequestBody StudentDTO dto) {
        Boolean result = studentService.update(dto, id);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        studentService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/find/{name}")
    public ResponseEntity<List<StudentDTO>> getByName(@PathVariable("name") String name) {
        List<StudentDTO> result = studentService.getByName(name);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/find/surname/{surname}")
    public ResponseEntity<List<StudentDTO>> getBySurname(@PathVariable("surname") String surname) {
        List<StudentDTO> result = studentService.getBySurname(surname);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/find/gender/{gender}")
    public ResponseEntity<List<StudentDTO>> getByGender(@PathVariable("gender") String gender) {
        List<StudentDTO> result = studentService.getByGender(gender);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/find/level/{level}")
    public ResponseEntity<List<StudentDTO>> getByLevel(@PathVariable("level") Integer level) {
        List<StudentDTO> result = studentService.getByLevel(level);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/find/age/{age}")
    public ResponseEntity<List<StudentDTO>> getByAge(@PathVariable("age") Integer age) {
        List<StudentDTO> result = studentService.getByAge(age);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/find/date/{create_date}")
    public ResponseEntity<List<StudentDTO>> getByCreateDate(@PathVariable("create_date") LocalDateTime createDate) {
        List<StudentDTO> result = studentService.getByCreateDate(createDate);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/search")
    public ResponseEntity<List<StudentDTO>> getByCreateDateBetween(@RequestParam("start_date") LocalDateTime startDate,
                                                                   @RequestParam("endDate") LocalDateTime endDate) {
        List<StudentDTO> result = studentService.getByCreateDateBetween(startDate, endDate);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/pagination")
    public ResponseEntity<PageImpl<StudentDTO>> searchByPagination(@RequestParam(value = "page", defaultValue = "1") int page,
                                                            @RequestParam(value = "size", defaultValue = "3") int size) {
        return ResponseEntity.ok(studentService.pagination(page - 1, size));
    }


    @GetMapping("/pagination/age")
    public ResponseEntity<PageImpl<StudentDTO>> byAgePagination(@RequestParam(value = "age") int age,
                                                                @RequestParam(value = "page", defaultValue = "1") int page,
                                                                @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(studentService.findByAgeWithPagination(age, page - 1, size));
    }

    @GetMapping("/pagination/level")
    public ResponseEntity<PageImpl<StudentDTO>> byLevelPagination(@RequestParam(value = "level") int level,
                                                                @RequestParam(value = "page", defaultValue = "1") int page,
                                                                @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(studentService.findByLevelWithPagination(level, page - 1, size));
    }

    @GetMapping("/pagination/gender")
    public ResponseEntity<PageImpl<StudentDTO>> byGenderPagination(@RequestParam(value = "gender") String gender,
                                                                @RequestParam(value = "page", defaultValue = "1") int page,
                                                                @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(studentService.findByGenderWithPagination(gender, page - 1, size));
    }

    @PostMapping("/filter")
    public ResponseEntity<PageImpl<StudentDTO>> filter(@RequestBody StudentFilterDTO dto,
                                                       @RequestParam(value = "page", defaultValue = "1") int page,
                                                       @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(studentService.filter(dto, page - 1, size));
    }

}
