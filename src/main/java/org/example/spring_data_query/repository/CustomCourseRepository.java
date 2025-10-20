package org.example.spring_data_query.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.example.spring_data_query.dto.CourseFilterDTO;
import org.example.spring_data_query.dto.CustomFilterResultDTO;
import org.example.spring_data_query.dto.StudentFilterDTO;
import org.example.spring_data_query.entity.CourseEntity;
import org.example.spring_data_query.entity.StudentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CustomCourseRepository {
    @Autowired
    private EntityManager entityManager;

    public CustomFilterResultDTO<CourseEntity> filter(CourseFilterDTO filter, int page, int size) {
        StringBuilder conditionBuilder = new StringBuilder("where 1=1 ");
        Map<String, Object> params = new HashMap<>();
        if (filter.getName() != null) { // isBlank()
            conditionBuilder.append("and s.name like :name ");
            params.put("name", filter.getName());
        }
        if (filter.getPrice() != null) { // isBlank()
            conditionBuilder.append("and s.price = :price ");
            params.put("price", filter.getPrice());
        }
        if (filter.getDuration() != null) { // isBlank()
            conditionBuilder.append("and s.duration = :duration ");
            params.put("duration", filter.getDuration());
        }
        if (filter.getCreatedDateFrom() != null) { // isBlank()
            conditionBuilder.append("and s.createdDate >= :createdDateFrom ");
            params.put("createdDateFrom", filter.getCreatedDateFrom());
        }
        if (filter.getCreatedDateTo() != null) { // isBlank()
            conditionBuilder.append("and s.createdDate <= :createdDateTo ");
            params.put("createdDateTo", filter.getCreatedDateTo());
        }
        StringBuilder selectBuilder = new StringBuilder("From CourseEntity s ");
        selectBuilder.append(conditionBuilder);

        Query selectQuery = entityManager.createQuery(selectBuilder.toString());
        selectQuery.setFirstResult(page * size); // offset
        selectQuery.setMaxResults(size); // limit

        StringBuilder countBuilder = new StringBuilder("Select count(s) From CourseEntity s ");
        countBuilder.append(conditionBuilder);
        Query countQuery = entityManager.createQuery(countBuilder.toString());


        for (Map.Entry<String, Object> entry : params.entrySet()) {
            selectQuery.setParameter(entry.getKey(), entry.getValue());
            countQuery.setParameter(entry.getKey(), entry.getValue());
        }
//        params.forEach(selectQuery::setParameter);

        List<CourseEntity> entityList = selectQuery.getResultList();
        Long totalElements = (Long) countQuery.getSingleResult();

        return new CustomFilterResultDTO<>(entityList, totalElements);
    }
}
