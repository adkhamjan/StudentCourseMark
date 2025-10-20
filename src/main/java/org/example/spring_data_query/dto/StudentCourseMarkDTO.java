package org.example.spring_data_query.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class StudentCourseMarkDTO {
    private Integer id;
    private Integer studentId;
    private Integer courseId;
    private Integer mark;
    private LocalDate createdDate;
}
