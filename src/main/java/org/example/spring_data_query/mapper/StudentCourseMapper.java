package org.example.spring_data_query.mapper;

import java.time.LocalDate;

public interface StudentCourseMapper {
    Integer getId();
    Integer getStudentId();
    String getName();
    String getSurname();
    Integer getCourseId();
    String getCourseName();
    Integer getMark();
    LocalDate getDate();
}
