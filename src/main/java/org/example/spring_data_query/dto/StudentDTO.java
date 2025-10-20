package org.example.spring_data_query.dto;

import lombok.Data;
import org.example.spring_data_query.enums.Gender;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class StudentDTO {
    private Integer id;
    private String name;
    private String surname;
    private Integer level;
    private Integer age;
    private Gender gender;
    private LocalDate birthday;
    private LocalDateTime createdDate;
}