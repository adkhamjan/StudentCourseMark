package org.example.spring_data_query.controller;

import org.example.spring_data_query.dto.CourseDTO;
import org.example.spring_data_query.dto.CourseFilterDTO;
import org.example.spring_data_query.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @PostMapping("")
    public ResponseEntity<CourseDTO> create(@RequestBody CourseDTO dto) {
        CourseDTO result = courseService.create(dto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("")
    public ResponseEntity<List<CourseDTO>> getAll() {
        List<CourseDTO> result = courseService.getAllCourse();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> getById(@PathVariable Integer id) {
        CourseDTO result = courseService.getById(id);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boolean> update(@PathVariable Integer id, @RequestBody CourseDTO dto) {
        Boolean result = courseService.update(dto, id);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        courseService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/find/{name}")
    public ResponseEntity<List<CourseDTO>> getByName(@PathVariable("name") String name) {
        List<CourseDTO> result = courseService.getByName(name);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/find/{price}")
    public ResponseEntity<List<CourseDTO>> getByPrice(@PathVariable("price") Double price) {
        List<CourseDTO> result = courseService.getByPrice(price);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/find/{duration}")
    public ResponseEntity<List<CourseDTO>> getByGender(@PathVariable("duration") LocalDate duration) {
        List<CourseDTO> result = courseService.getByDuration(duration);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/search/date")
    public ResponseEntity<List<CourseDTO>> getByCreateDateBetween(@RequestParam("start_date") LocalDateTime startDate,
                                                                   @RequestParam("endDate") LocalDateTime endDate) {
        List<CourseDTO> result = courseService.getByCreateDateBetween(startDate, endDate);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/search/price")
    public ResponseEntity<List<CourseDTO>> getByPriceBetween(@RequestParam("start_price") Double startPrice,
                                                                  @RequestParam("end_price") Double endPrice) {
        List<CourseDTO> result = courseService.getByPriceBetween(startPrice, startPrice);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/pagination")
    public ResponseEntity<PageImpl<CourseDTO>> searchByPagination(@RequestParam(value = "page") int page,
                                                            @RequestParam(value = "size") int size) {
        return ResponseEntity.ok(courseService.pagination(page - 1, size));
    }


    @GetMapping("/pagination/sort")
    public ResponseEntity<PageImpl<CourseDTO>> byAgePagination(@RequestParam(value = "page") int page,
                                                                @RequestParam(value = "size") int size) {
        return ResponseEntity.ok(courseService.paginationSort(page - 1, size));
    }

    @GetMapping("/pagination/price")
    public ResponseEntity<PageImpl<CourseDTO>> byLevelPagination(@RequestParam(value = "level") double price,
                                                                  @RequestParam(value = "page", defaultValue = "1") int page,
                                                                  @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(courseService.findByPriceWithPagination(price, page - 1, size));
    }

    @GetMapping("/pagination/price_between")
    public ResponseEntity<PageImpl<CourseDTO>> byGenderPagination(@RequestParam(value = "gender") double start,
                                                                  @RequestParam(value = "gender") double end,
                                                                   @RequestParam(value = "page", defaultValue = "1") int page,
                                                                   @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(courseService.findByPriceBetweenWithPagination(start, end, page - 1, size));
    }

    @PostMapping("/filter")
    public ResponseEntity<PageImpl<CourseDTO>> filter(@RequestBody CourseFilterDTO filter,
                                                  @RequestParam(value = "page", defaultValue = "1") int page,
                                                  @RequestParam(value = "size", defaultValue = "5") int size) {
        return ResponseEntity.ok(courseService.filter(filter, page-1, size));
    }
}
