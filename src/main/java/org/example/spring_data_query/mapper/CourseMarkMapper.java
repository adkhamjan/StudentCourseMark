package org.example.spring_data_query.mapper;

import java.time.LocalDate;

public interface CourseMarkMapper {
    Integer getId();
    Integer getMark();
    LocalDate getDate();
    Integer getCourseId();
    String getCourseName();
}
