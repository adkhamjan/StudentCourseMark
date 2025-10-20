package org.example.spring_data_query.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class StudentCourseMarkFilterDTO {
    private Integer studentId;
    private Integer courseId;
    private Integer markFrom;
    private Integer markTo;
    private LocalDate createdDateFrom;
    private LocalDate createdDateTo;
    private String StudentName;
    private String CourseName;
}
