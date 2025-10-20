package org.example.spring_data_query.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "student_course_mark")
@Getter
@Setter
public class StudentCourseMarkEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "student_id")
    private Integer studentId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", insertable = false, updatable = false)
    private StudentEntity student;

    @Column(name = "course_id")
    private Integer courseId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id",  insertable = false, updatable = false)
    private CourseEntity course;

    @Column(name = "mark")
    private Integer mark;
    @Column(name = "create_data")
    private LocalDate createdDate;
}
