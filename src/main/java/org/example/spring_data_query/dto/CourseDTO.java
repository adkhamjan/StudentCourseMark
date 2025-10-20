package org.example.spring_data_query.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class CourseDTO {
    private Integer id;
    private String name;
    private Double price;
    private LocalDate duration;
    private LocalDateTime createdDate;
}
