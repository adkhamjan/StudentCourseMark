package org.example.spring_data_query.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.example.spring_data_query.dto.CourseFilterDTO;
import org.example.spring_data_query.dto.CustomFilterResultDTO;
import org.example.spring_data_query.dto.StudentCourseMarkFilterDTO;
import org.example.spring_data_query.entity.CourseEntity;
import org.example.spring_data_query.mapper.StudentCourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CustomStudentCourseMarkRepository {
    @Autowired
    private EntityManager entityManager;

    public CustomFilterResultDTO<StudentCourseMapper> filter(StudentCourseMarkFilterDTO filter, int page, int size) {
        StringBuilder conditionBuilder = new StringBuilder("where 1=1 ");
        Map<String, Object> params = new HashMap<>();
        if (filter.getStudentId() != null) { // isBlank()
            conditionBuilder.append("and s.id = :studentId ");
            params.put("studentId", filter.getStudentId());
        }
        if (filter.getCourseId() != null) { // isBlank()
            conditionBuilder.append("and c.id = :courseId ");
            params.put("courseId", filter.getCourseId());
        }
        if (filter.getMarkFrom() != null) { // isBlank()
            conditionBuilder.append("and scm.markFrom >= :markFrom ");
            params.put("markFrom", filter.getMarkFrom());
        }
        if (filter.getMarkTo() != null) { // isBlank()
            conditionBuilder.append("and scm.markTo <= :markTo ");
            params.put("markTo", filter.getMarkTo());
        }
        if (filter.getCreatedDateFrom() != null) { // isBlank()
            conditionBuilder.append("and scm.createdDate >= :createdDateFrom ");
            params.put("createdDateFrom", filter.getCreatedDateFrom());
        }
        if (filter.getCreatedDateTo() != null) { // isBlank()
            conditionBuilder.append("and scm.createdDate <= :createdDateTo ");
            params.put("createdDateTo", filter.getCreatedDateTo());
        }
        if (filter.getStudentName() != null) {
            conditionBuilder.append("and s.studentName like :studentName ");
            params.put("studentName", filter.getStudentName());
        }
        if (filter.getCourseName() != null) {
            conditionBuilder.append("and c.courseName like :courseName ");
            params.put("courseName", filter.getCourseName());
        }

        StringBuilder selectBuilder = new StringBuilder("select scm.id as id, s.id as studentId, s.name as name, s.surname as surname, " +
                "c.id as courseId, c.name as courseName, scm.mark as mark, scm.createdDate as date " +
                "from StudentCourseMarkEntity scm join scm.student s join scm.course c ");
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

        List<StudentCourseMapper> entityList = selectQuery.getResultList();
        Long totalElements = (Long) countQuery.getSingleResult();

        return new CustomFilterResultDTO<>(entityList, totalElements);
    }
}
