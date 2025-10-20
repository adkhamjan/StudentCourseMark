package org.example.spring_data_query.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Getter
@Setter
public class CourseFilterDTO {
    private Integer id;
    private String name;
    private Double price;
    private LocalDate duration;
    private LocalDateTime createdDateFrom;
    private LocalDateTime createdDateTo;
}