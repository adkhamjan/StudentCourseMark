package org.example.spring_data_query.controller;

import org.example.spring_data_query.dto.StudentCourseMarkDTO;
import org.example.spring_data_query.dto.StudentCourseMarkFilterDTO;
import org.example.spring_data_query.mapper.MarkCourseNameMapper;
import org.example.spring_data_query.mapper.StudentCourseMapper;
import org.example.spring_data_query.mapper.StudentMarkMapper;
import org.example.spring_data_query.service.StudentCourseMarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/mark")
public class StudentCourseMarkController {
    @Autowired
    private StudentCourseMarkService studentCourseMarkService;

    @PostMapping("")
    public ResponseEntity<StudentCourseMarkDTO> create(@RequestBody StudentCourseMarkDTO dto) {
        StudentCourseMarkDTO result = studentCourseMarkService.create(dto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("")
    public ResponseEntity<List<StudentCourseMarkDTO>> getAll() {
        List<StudentCourseMarkDTO> result = studentCourseMarkService.getAllStudent();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentCourseMarkDTO> getById(@PathVariable Integer id) {
        StudentCourseMarkDTO result = studentCourseMarkService.getById(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<StudentCourseMapper> getByIdDetail(@PathVariable Integer id) {
        StudentCourseMapper result = studentCourseMarkService.getByIdDetail(id);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boolean> update(@PathVariable Integer id, @RequestBody StudentCourseMarkDTO dto) {
        Boolean result = studentCourseMarkService.update(dto, id);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        studentCourseMarkService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/student_date")
    public ResponseEntity<List<StudentCourseMarkDTO>> getByDate(@RequestParam("student_id") Integer studentId,
                                                                @RequestParam("date") LocalDate date) {
        List<StudentCourseMarkDTO> result = studentCourseMarkService.getByCreatedDateAndStudentId(date, studentId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/student_date/between")
    public ResponseEntity<List<StudentCourseMarkDTO>> getByDateBetween(@RequestParam("student_id") Integer studentId,
                                                                @RequestParam("start") LocalDate start, @RequestParam("end") LocalDate end) {
        List<StudentCourseMarkDTO> result = studentCourseMarkService.getByCreatedDateAndStudentIdBetween(studentId, start, end);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/order")
    public ResponseEntity<List<StudentCourseMarkDTO>> getByOrder(@RequestParam("student_id") Integer studentId) {
        List<StudentCourseMarkDTO> result = studentCourseMarkService.getByStudentIdOrder(studentId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/course")
    public ResponseEntity<List<StudentCourseMarkDTO>> getByCourseIdOrder(@RequestParam("student_id") Integer studentId,
                                                                         @RequestParam("course_id") Integer courseId) {
        List<StudentCourseMarkDTO> result = studentCourseMarkService.getByStudentIdOrder(studentId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/end_mark")
    public ResponseEntity<MarkCourseNameMapper> getEndMark(@RequestParam("student_id") Integer studentId) {
        MarkCourseNameMapper result = studentCourseMarkService.getByStudentIdEndMark(studentId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/top_park")
    public ResponseEntity<List<StudentCourseMarkDTO>> getTop3Mark(@RequestParam("student_id") Integer studentId) {
        List<StudentCourseMarkDTO> result = studentCourseMarkService.getTop3Mark(studentId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/pagination/studentId")
    public ResponseEntity<PageImpl<StudentCourseMarkDTO>> getPaginationByStudentId(@RequestParam("student_id") Integer studentId,
                                                                         @RequestParam("page") int page, @RequestParam("size") int size) {
        PageImpl<StudentCourseMarkDTO> result = studentCourseMarkService.paginationByStudentId(studentId, page-1, size);
        return  ResponseEntity.ok(result);
    }

    @GetMapping("/student_mark")
    public ResponseEntity<List<StudentMarkMapper>> getStudentMarkByCourseId(@RequestParam("course_id") Integer courseId) {
        List<StudentMarkMapper> result = studentCourseMarkService.getStudentMarksByCourseId(courseId);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/filter")
    public ResponseEntity<PageImpl<StudentCourseMapper>> filter(@RequestBody StudentCourseMarkFilterDTO filter,
                                                                @RequestParam(value = "page", defaultValue = "1") int page,
                                                                @RequestParam(value = "size", defaultValue = "5") int size) {
        return ResponseEntity.ok(studentCourseMarkService.filter(filter, page-1, size));
    }
}