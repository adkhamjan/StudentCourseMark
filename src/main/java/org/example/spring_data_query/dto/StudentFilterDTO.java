package org.example.spring_data_query.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class StudentFilterDTO {
    private Integer id;
    private String name;
    private String surname;
    private String age;
    private LocalDate birthdate;
    private LocalDateTime createdDateFrom;
    private LocalDateTime createdDateTo;
}
