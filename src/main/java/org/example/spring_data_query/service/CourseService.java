package org.example.spring_data_query.service;

import org.example.spring_data_query.dto.CourseDTO;
import org.example.spring_data_query.dto.CourseFilterDTO;
import org.example.spring_data_query.dto.CustomFilterResultDTO;
import org.example.spring_data_query.entity.CourseEntity;
import org.example.spring_data_query.repository.CourseRepository;
import org.example.spring_data_query.repository.CustomCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CustomCourseRepository customCourseRepository;

    public CourseDTO create(CourseDTO dto) {
        CourseEntity courseEntity = new CourseEntity();
        courseEntity.setName(dto.getName());
        courseEntity.setDuration(dto.getDuration());
        courseEntity.setPrice(dto.getPrice());
        courseEntity.setCreatedDate(LocalDateTime.now());

        courseRepository.save(courseEntity);
        dto.setId(courseEntity.getId());
        return dto;
    }

    public List<CourseDTO> getAllCourse() {
        Iterable<CourseEntity> entityList = courseRepository.findAll();

        return changeToDTO(entityList);
    }

    public CourseDTO getById(Integer id) {
        Optional<CourseEntity> optional = courseRepository.findById(id);
        if (optional.isEmpty()) {
            return null;
        }
        CourseEntity entity = optional.get();

        CourseDTO dto = new CourseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDuration(entity.getDuration());
        dto.setPrice(entity.getPrice());
        dto.setCreatedDate(entity.getCreatedDate());

        return dto;
    }

    public Boolean update(CourseDTO dto, Integer id) {
        Optional<CourseEntity> optional = courseRepository.findById(id);
        if (optional.isEmpty()) {
            return false;
        }
        CourseEntity entity = optional.get();
        entity.setName(dto.getName());
        entity.setDuration(dto.getDuration());
        entity.setPrice(dto.getPrice());
        entity.setCreatedDate(dto.getCreatedDate());
        courseRepository.save(entity);
        return true;
    }

    public void  delete(Integer id) {
        courseRepository.deleteById(id);
    }

    private List<CourseDTO> changeToDTO(Iterable<CourseEntity> entityList) {
        List<CourseDTO> dtoList = new LinkedList<>();
        for (CourseEntity entity : entityList) {
            CourseDTO dto = new CourseDTO();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setDuration(entity.getDuration());
            dto.setPrice(entity.getPrice());
            dto.setCreatedDate(entity.getCreatedDate());
            dtoList.add(dto);
        }
        return dtoList;
    }

    public List<CourseDTO> getByName(String name) {
        Iterable<CourseEntity> entityList = courseRepository.getByName(name);

        return changeToDTO(entityList);
    }

    public List<CourseDTO> getByPrice(Double price) {
        Iterable<CourseEntity> entityList = courseRepository.getByPrice(price);

        return changeToDTO(entityList);
    }

    public List<CourseDTO> getByDuration(LocalDate duration) {
        Iterable<CourseEntity> entityList = courseRepository.getByDuration(duration);

        return changeToDTO(entityList);
    }

    public List<CourseDTO> getByCreateDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        Iterable<CourseEntity> entityList = courseRepository.getByCreatedDateBetween(startDate, endDate);

        return changeToDTO(entityList);
    }

    public List<CourseDTO> getByPriceBetween(Double startPrice, Double endPrice) {
        Iterable<CourseEntity> entityList = courseRepository.getByPriceBetween(startPrice, endPrice);

        return changeToDTO(entityList);
    }

    public PageImpl<CourseDTO> pagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CourseEntity> pageResult = courseRepository.findAll(pageable);
        // select * from student order by createdDate desc limit ? offset ?  -> content
        // select count(*) ... totalCount

        List<CourseEntity> courseList = pageResult.getContent();
        long totalElement = pageResult.getTotalElements();

        List<CourseDTO> dtoList = changeToDTO(courseList);

        return new PageImpl<>(dtoList, pageable, totalElement);
    }

    public PageImpl<CourseDTO> paginationSort(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<CourseEntity> pageResult = courseRepository.findAll(pageable);
        // select * from student order by createdDate desc limit ? offset ?  -> content
        // select count(*) ... totalCount

        List<CourseEntity> studentList = pageResult.getContent();
        long totalElement = pageResult.getTotalElements();

        List<CourseDTO> dtoList = changeToDTO(studentList);

        return new PageImpl<>(dtoList, pageable, totalElement);
    }

    public PageImpl<CourseDTO> findByPriceWithPagination(double price, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<CourseEntity> pageResult = courseRepository.findByPrice(price, pageable);
        // select * from student order by createdDate desc limit ? offset ?  -> content
        // select count(*) ... totalCount

        List<CourseEntity> courseList = pageResult.getContent();
        long totalElement = pageResult.getTotalElements();

        List<CourseDTO> dtoList = changeToDTO(courseList);

        return new PageImpl<>(dtoList, pageable, totalElement);
    }

    public PageImpl<CourseDTO> findByPriceBetweenWithPagination(double priceStart, double priceEnd, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<CourseEntity> pageResult = courseRepository.findByPriceBetween(priceStart, priceEnd, pageable);
        // select * from student order by createdDate desc limit ? offset ?  -> content
        // select count(*) ... totalCount

        List<CourseEntity> courseList = pageResult.getContent();
        long totalElement = pageResult.getTotalElements();

        List<CourseDTO> dtoList = changeToDTO(courseList);

        return new PageImpl<>(dtoList, pageable, totalElement);
    }

    public PageImpl<CourseDTO> filter(CourseFilterDTO filter,  int page, int size) {
        CustomFilterResultDTO<CourseEntity> result = customCourseRepository.filter(filter, page, size);

        List<CourseDTO> dtoList = changeToDTO(result.getContent());
        return new PageImpl<>(dtoList, PageRequest.of(page,size), result.getTotalElements());
    }
}
