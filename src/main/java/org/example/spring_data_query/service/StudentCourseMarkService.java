package org.example.spring_data_query.service;

import org.example.spring_data_query.dto.CustomFilterResultDTO;
import org.example.spring_data_query.dto.StudentCourseMarkDTO;
import org.example.spring_data_query.dto.StudentCourseMarkFilterDTO;
import org.example.spring_data_query.dto.StudentDTO;
import org.example.spring_data_query.entity.StudentCourseMarkEntity;
import org.example.spring_data_query.entity.StudentEntity;
import org.example.spring_data_query.mapper.CourseMarkMapper;
import org.example.spring_data_query.mapper.MarkCourseNameMapper;
import org.example.spring_data_query.mapper.StudentCourseMapper;
import org.example.spring_data_query.mapper.StudentMarkMapper;
import org.example.spring_data_query.repository.CustomStudentCourseMarkRepository;
import org.example.spring_data_query.repository.StudentCourseMarkRepository;
import org.example.spring_data_query.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentCourseMarkService {
    @Autowired
    private StudentCourseMarkRepository studentCourseMarkRepository;

    @Autowired
    private CustomStudentCourseMarkRepository  customStudentCourseMarkRepository;

    public StudentCourseMarkDTO create(StudentCourseMarkDTO dto) {
        StudentCourseMarkEntity entity = new StudentCourseMarkEntity();
        entity.setMark(dto.getMark());
        entity.setCourseId(dto.getCourseId());
        entity.setStudentId(dto.getStudentId());
        entity.setCreatedDate(LocalDate.now());

        studentCourseMarkRepository.save(entity);
        dto.setId(entity.getId());

        return dto;
    }

    public List<StudentCourseMarkDTO> getAllStudent() {
        Iterable<StudentCourseMarkEntity> entityList = studentCourseMarkRepository.findAll();

        return changeToDTO(entityList);
    }

    public StudentCourseMarkDTO getById(Integer id) {
        Optional<StudentCourseMarkEntity> optional = studentCourseMarkRepository.findById(id);
        if (optional.isEmpty()) {
            return null;
        }
        StudentCourseMarkEntity entity = optional.get();

        StudentCourseMarkDTO dto = new StudentCourseMarkDTO();
        dto.setId(entity.getId());
        dto.setMark(entity.getMark());
        dto.setCourseId(entity.getCourseId());
        dto.setStudentId(entity.getStudentId());
        dto.setCreatedDate(entity.getCreatedDate());

        return dto;
    }

    public StudentCourseMapper getByIdDetail(Integer id) {
        Optional<StudentCourseMapper> optional = studentCourseMarkRepository.getByIdDetail(id);
        if (optional.isEmpty()) {
            return null;
        }
        StudentCourseMapper mapper = optional.get();

        return mapper;
    }

    public Boolean update(StudentCourseMarkDTO dto, Integer id) {
        Optional<StudentCourseMarkEntity> optional = studentCourseMarkRepository.findById(id);
        if (optional.isEmpty()) {
            return false;
        }
        StudentCourseMarkEntity entity = optional.get();
        entity.setMark(dto.getMark());
        entity.setCourseId(dto.getCourseId());
        entity.setStudentId(dto.getStudentId());
        entity.setCreatedDate(dto.getCreatedDate());
        studentCourseMarkRepository.save(entity);
        return true;
    }

    public void delete(Integer id) {
        studentCourseMarkRepository.deleteById(id);
    }

    public List<StudentCourseMarkDTO> getByCreatedDateAndStudentId(LocalDate date, Integer studentId) {
        Iterable<StudentCourseMarkEntity> entityList = studentCourseMarkRepository.getStudentCourseMarkEntitiesByStudentIdAndCreatedDate(studentId, date);

        return changeToDTO(entityList);
    }

    public List<StudentCourseMarkDTO> getByCreatedDateAndStudentIdBetween(Integer studentId, LocalDate start, LocalDate end) {
        Iterable<StudentCourseMarkEntity> entityList = studentCourseMarkRepository.getStudentCourseMarkEntitiesByStudentIdAndCreatedDateBetween(studentId, start, end);

        return changeToDTO(entityList);
    }

    public List<StudentCourseMarkDTO> getByStudentIdOrder(Integer studentId) {
        Iterable<StudentCourseMarkEntity> entityList = studentCourseMarkRepository.getStudentCourseMarkEntitiesByStudentIdOrderByCreatedDateDesc(studentId);

        return changeToDTO(entityList);
    }

    public List<StudentCourseMarkDTO> getByStudentIdAndCourseIdOrder(Integer studentId, Integer courseId) {
        Iterable<StudentCourseMarkEntity> entityList = studentCourseMarkRepository.getStudentCourseMarkEntitiesByStudentIdAndCourseIdOrderByCreatedDateDesc(studentId,  courseId);

        return changeToDTO(entityList);
    }

    private List<StudentCourseMarkDTO> changeToDTO(Iterable<StudentCourseMarkEntity> entityList) {
        List<StudentCourseMarkDTO> dtoList = new LinkedList<>();
        for (StudentCourseMarkEntity entity : entityList) {
            StudentCourseMarkDTO dto = new StudentCourseMarkDTO();
            dto.setId(entity.getId());
            dto.setMark(entity.getMark());
            dto.setCourseId(entity.getCourseId());
            dto.setStudentId(entity.getStudentId());
            dto.setCreatedDate(entity.getCreatedDate());
            dtoList.add(dto);
        }
        return dtoList;
    }

    public MarkCourseNameMapper getByStudentIdEndMark(Integer studentId) {
        Optional<MarkCourseNameMapper>  optional = studentCourseMarkRepository.getByIdEndMark(studentId);
        if (optional.isEmpty()) {
            return null;
        }
        return optional.get();
    }

    public List<StudentCourseMarkDTO> getTop3Mark(Integer studentId) {
        Iterable<StudentCourseMarkEntity> entityList = studentCourseMarkRepository.findTop3ByStudentIdOrderByMarkDesc(studentId);

        return changeToDTO(entityList);
    }

    public PageImpl<StudentCourseMarkDTO> pagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<StudentCourseMarkEntity> pageResult = studentCourseMarkRepository.findAll(pageable);
        // select * from student order by createdDate desc limit ? offset ?  -> content
        // select count(*) ... totalCount

        List<StudentCourseMarkEntity> studentList = pageResult.getContent();
        long totalElement = pageResult.getTotalElements();

        List<StudentCourseMarkDTO> dtoList = changeToDTO(studentList);

        return new PageImpl<>(dtoList, pageable, totalElement);
    }

    public PageImpl<StudentCourseMarkDTO> paginationByStudentId(Integer studentId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<StudentCourseMarkEntity> pageResult = studentCourseMarkRepository.findByStudentIdOrderByCreatedDateDesc(studentId, pageable);

        List<StudentCourseMarkEntity> marktList = pageResult.getContent();
        List<StudentCourseMarkDTO> dtoList = changeToDTO(marktList);

        return new PageImpl<>(dtoList, pageable, pageResult.getTotalElements());
    }

    public List<StudentMarkMapper>  getStudentMarksByCourseId(Integer courseId) {
        return studentCourseMarkRepository.getStudentMarkByCourseId(courseId);
    }

    public PageImpl<StudentCourseMapper> filter(StudentCourseMarkFilterDTO filter, int page, int size) {
        CustomFilterResultDTO<StudentCourseMapper> result = customStudentCourseMarkRepository.filter(filter, page, size);

        return new PageImpl<>(result.getContent(), PageRequest.of(page, size), result.getTotalElements());
    }
}
