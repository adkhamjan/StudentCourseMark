package org.example.spring_data_query.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.spring_data_query.enums.Gender;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "student")
@Getter
@Setter
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "level")
    private Integer level;
    @Column(name = "age")
    private Integer age;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(name = "birthday")
    private LocalDate birthday;
    @Column(name = "create_date")
    private LocalDateTime createdDate;
}
