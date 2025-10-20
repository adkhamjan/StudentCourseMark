package org.example.spring_data_query.service;

import org.example.spring_data_query.dto.CustomFilterResultDTO;
import org.example.spring_data_query.dto.StudentDTO;
import org.example.spring_data_query.dto.StudentFilterDTO;
import org.example.spring_data_query.entity.StudentEntity;
import org.example.spring_data_query.enums.Gender;
import org.example.spring_data_query.repository.CustomStudentRepository;
import org.example.spring_data_query.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CustomStudentRepository customStudentRepository;

    public StudentDTO create(StudentDTO dto) {
        StudentEntity entity = new StudentEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setAge(dto.getAge());
        entity.setLevel(dto.getLevel());
        entity.setGender(dto.getGender());
        entity.setCreatedDate(LocalDateTime.now());

        studentRepository.save(entity);
        dto.setId(entity.getId());

        return dto;
    }

    public List<StudentDTO> getAllStudent() {
        Iterable<StudentEntity> entityList = studentRepository.findAll();

        return changeToDTO(entityList);
    }

    public StudentDTO getById(Integer id) {
        Optional<StudentEntity> optional = studentRepository.findById(id);
        if (optional.isEmpty()) {
            return null;
        }
        StudentEntity entity = optional.get();

        StudentDTO dto = new StudentDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setAge(entity.getAge());
        dto.setLevel(entity.getLevel());
        dto.setGender(entity.getGender());
        dto.setCreatedDate(entity.getCreatedDate());

        return dto;
    }

    public Boolean update(StudentDTO dto, Integer id) {
        Optional<StudentEntity> optional = studentRepository.findById(id);
        if (optional.isEmpty()) {
            return false;
        }
        StudentEntity entity = optional.get();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setAge(dto.getAge());
        entity.setLevel(dto.getLevel());
        entity.setGender(dto.getGender());
        entity.setCreatedDate(dto.getCreatedDate());
        studentRepository.save(entity);
        return true;
    }

    public void delete(Integer id) {
        studentRepository.deleteById(id);
    }

    public List<StudentDTO> getByName(String name) {
        Iterable<StudentEntity> entityList = studentRepository.getByName(name);

        return changeToDTO(entityList);
    }

    public List<StudentDTO> getBySurname(String surname) {
        Iterable<StudentEntity> entityList = studentRepository.getBySurname(surname);

        return changeToDTO(entityList);
    }

    public List<StudentDTO> getByLevel(Integer level) {
        Iterable<StudentEntity> entityList = studentRepository.getByLevel(level);

        return changeToDTO(entityList);
    }

    public List<StudentDTO> getByGender(String gender) {
        Iterable<StudentEntity> entityList = studentRepository.getByGender(Gender.valueOf(gender));

        return changeToDTO(entityList);
    }

    public List<StudentDTO> getByAge(Integer age) {
        Iterable<StudentEntity> entityList = studentRepository.getByAge(age);

        return changeToDTO(entityList);
    }

    public List<StudentDTO> getByCreateDate(LocalDateTime date) {
        Iterable<StudentEntity> entityList = studentRepository.getByCreatedDate(date);

        return changeToDTO(entityList);
    }

    public List<StudentDTO> getByCreateDateBetween(LocalDateTime date1, LocalDateTime date2) {
        Iterable<StudentEntity> entityList = studentRepository.findByCreatedDateBetween(date1, date2);

        return changeToDTO(entityList);
    }

    private List<StudentDTO> changeToDTO(Iterable<StudentEntity> entityList) {
        List<StudentDTO> dtoList = new LinkedList<>();
        for (StudentEntity entity : entityList) {
            StudentDTO dto = new StudentDTO();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setSurname(entity.getSurname());
            dto.setAge(entity.getAge());
            dto.setLevel(entity.getLevel());
            dto.setGender(entity.getGender());
            dto.setCreatedDate(entity.getCreatedDate());
            dtoList.add(dto);
        }
        return dtoList;
    }

    public PageImpl<StudentDTO> pagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<StudentEntity> pageResult = studentRepository.findAll(pageable);
        // select * from student order by createdDate desc limit ? offset ?  -> content
        // select count(*) ... totalCount

        List<StudentEntity> studentList = pageResult.getContent();
        long totalElement = pageResult.getTotalElements();

        List<StudentDTO> dtoList = changeToDTO(studentList);

        return new PageImpl<>(dtoList, pageable, totalElement);
    }

    public PageImpl<StudentDTO> findByAgeWithPagination(int age, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<StudentEntity> pageResult = studentRepository.findByAge(age, pageable);
        // select * from student order by createdDate desc limit ? offset ?  -> content
        // select count(*) ... totalCount

        List<StudentEntity> studentList = pageResult.getContent();
        long totalElement = pageResult.getTotalElements();

        List<StudentDTO> dtoList = changeToDTO(studentList);

        return new PageImpl<>(dtoList, pageable, totalElement);
    }

    public PageImpl<StudentDTO> findByLevelWithPagination(int level, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<StudentEntity> pageResult = studentRepository.findByAge(level, pageable);
        // select * from student order by createdDate desc limit ? offset ?  -> content
        // select count(*) ... totalCount

        List<StudentEntity> studentList = pageResult.getContent();
        long totalElement = pageResult.getTotalElements();

        List<StudentDTO> dtoList = changeToDTO(studentList);

        return new PageImpl<>(dtoList, pageable, totalElement);
    }

    public PageImpl<StudentDTO> findByGenderWithPagination(String gender, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<StudentEntity> pageResult = studentRepository.findByGender(Gender.valueOf(gender), pageable);
        // select * from student order by createdDate desc limit ? offset ?  -> content
        // select count(*) ... totalCount

        List<StudentEntity> studentList = pageResult.getContent();
        long totalElement = pageResult.getTotalElements();

        List<StudentDTO> dtoList = changeToDTO(studentList);

        return new PageImpl<>(dtoList, pageable, totalElement);
    }


    public PageImpl<StudentDTO> filter(StudentFilterDTO filter, int page, int size) {
        CustomFilterResultDTO<StudentEntity> result = customStudentRepository.filter(filter, page, size);

        List<StudentDTO> dtoList = changeToDTO(result.getContent());

        return new PageImpl<>(dtoList, PageRequest.of(page,size), result.getTotalElements());
    }
}