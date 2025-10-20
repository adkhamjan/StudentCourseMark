package org.example.spring_data_query.mapper;

import java.time.LocalDate;

public interface StudentMarkMapper {
    Integer getId();
    Integer getMark();
    LocalDate getDate();
    Integer getStudentId();
    String getName();
    String getSurname();
}
