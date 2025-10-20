package org.example.spring_data_query.repository;

import jakarta.transaction.Transactional;
import org.example.spring_data_query.entity.StudentCourseMarkEntity;
import org.example.spring_data_query.mapper.CourseMarkMapper;
import org.example.spring_data_query.mapper.MarkCourseNameMapper;
import org.example.spring_data_query.mapper.StudentCourseMapper;
import org.example.spring_data_query.mapper.StudentMarkMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StudentCourseMarkRepository extends CrudRepository<StudentCourseMarkEntity,Integer>, PagingAndSortingRepository<StudentCourseMarkEntity,Integer> {
    Optional<StudentCourseMarkEntity> findById(Integer id);
    List<StudentCourseMarkEntity> findAll();

    @Transactional
    @Modifying
    void deleteById(Integer id);

    List<StudentCourseMarkEntity> getStudentCourseMarkEntitiesByStudentIdAndCreatedDate(Integer studentId, LocalDate createdDate);

    List<StudentCourseMarkEntity> getStudentCourseMarkEntitiesByStudentIdAndCreatedDateBetween(Integer studentId, LocalDate startDate, LocalDate endDate);

    List<StudentCourseMarkEntity> getStudentCourseMarkEntitiesByStudentIdOrderByCreatedDateDesc(Integer studentId);

    List<StudentCourseMarkEntity> getStudentCourseMarkEntitiesByStudentIdAndCourseIdOrderByCreatedDateDesc(Integer studentId, Integer courseId);

    @Query("from StudentCourseMarkEntity where id = :id ")
    Optional<StudentCourseMarkEntity> getById(@Param("id") Integer id);

    @Query("select s from StudentCourseMarkEntity s where s.studentId = :studentId and s.createdDate = :createdDate")
    List<StudentCourseMarkEntity> getMarkByStudentIdDate(@Param("studentId") Integer studentId,
                                                         @Param("createdDate") LocalDate createdDate);

    @Query("select s from StudentCourseMarkEntity s where s.studentId = :studentId and s.createdDate between :startDate and :endDate")
    List<StudentCourseMarkEntity> getMarkByStudentIdDateBetween(@Param("studentId") Integer studentId,
                                                                @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("select s from StudentCourseMarkEntity s where s.studentId = :studentId order by s.createdDate DESC")
    List<StudentCourseMarkEntity> getMarkByStudentIdOrder(@Param("studentId") Integer studentId);

    @Query("select s from StudentCourseMarkEntity s where s.studentId = :studentId and s.courseId = :courseId order by s.createdDate DESC")
    List<StudentCourseMarkEntity> getMarkByStudentIdCourseIdOrder(@Param("studentId") Integer studentId, @Param("courseId") Integer courseId);

    @Query("select m.id as id, s.id as studentId, s.name as name, s.surname as surname, " +
            "c.id as courseId, c.name as courseName, m.mark as mark, m.createdDate as date " +
            "from StudentCourseMarkEntity m join m.student s join m.course c where m.id = :id")
    Optional<StudentCourseMapper> getByIdDetail(@Param("id") Integer id);

    @Query("select m.mark as mark, c.name as courseNmae " +
            "from StudentCourseMarkEntity m join m.course c where m.id = :id order by m.createdDate desc")
    Optional<MarkCourseNameMapper> getByIdEndMark(@Param("id") Integer id);

    List<StudentCourseMarkEntity> findTop3ByStudentIdOrderByMarkDesc(Integer studentId);

    @Query("select m from StudentCourseMarkEntity m where m.studentId = :studentId order by m.mark desc")
    Page<StudentCourseMarkEntity> findTopMarks(@Param("studentId") Integer studentId, Pageable pageable);

    @Query("select m from StudentCourseMarkEntity m where m.studentId = ?1 order by m.createdDate asc ")
    Page<StudentCourseMarkEntity> findFirstByStudentId(@Param("studentId") Integer studentId, Pageable pageable);

    @Query("select max(m.mark) from StudentCourseMarkEntity m where m.studentId = :studentId and m.courseId = :courseId")
    Integer getMaxMark(@Param("studentId") Integer studentId, @Param("courseId") Integer courseId);

    @Query("select avg(m.mark) from StudentCourseMarkEntity m where m.studentId = :studentId")
    Double getAvgMark(@Param("studentId") Integer studentId);

    @Query("select avg(m.mark) from StudentCourseMarkEntity m where m.studentId = :studentId and m.courseId = :courseId")
    Double getByCourseIdAvgMark(@Param("studentId") Integer studentId, @Param("courseId") Integer courseId);

    @Query("select count(m.id) from StudentCourseMarkEntity m where m.studentId = ?1 and m.mark > ?2")
    Integer countByCourseId(@Param("studentId") Integer studentId, @Param("mark") Integer mark);

    @Query("select max(m.mark) from StudentCourseMarkEntity m where m.courseId = :courseId")
    Integer getMaxMarkByCourseId(@Param("courseId") Integer courseId);

    @Query("select avg(m.mark) from StudentCourseMarkEntity m where m.courseId = :courseId")
    Double getAvgMarkByCourseId(@Param("courseId") Integer courseId);

    @Query("select count (m.id) from StudentCourseMarkEntity m where m.courseId = :courseId")
    Integer getCountByCourseId(@Param("courseId") Integer courseId);

    Page<StudentCourseMarkEntity> findByStudentIdOrderByCreatedDateDesc(Integer studentId, Pageable pageable);

    @Query("from StudentCourseMarkEntity where studentId = ?1 order by createdDate desc ")
    Page<StudentCourseMarkEntity> getByStudentId(Integer studentId, Pageable pageable);

    Page<StudentCourseMarkEntity> findByCourseIdOrderByCreatedDateDesc(Integer courseId, Pageable pageable);

    @Query("from StudentCourseMarkEntity where courseId = ?1 order by createdDate desc ")
    Page<StudentCourseMarkEntity> getByCourseId(Integer courseId, Pageable pageable);

    @Query("select scm.id as id, scm.mark as mark, scm.createdDate date, c.id as courseId, c.name as courseName" +
            " from StudentCourseMarkEntity scm inner join scm.course c where scm.studentId = :studentId")
    List<CourseMarkMapper>  getCourseMarksByStudentId(@Param("studentId") Integer studentId);

    @Query("select scm.id as id, scm.mark as mark, scm.createdDate date, s.id as studentId, s.name as nama, s.surname as surname" +
            " from StudentCourseMarkEntity scm inner join scm.student s where scm.courseId = :courseId")
    List<StudentMarkMapper>  getStudentMarkByCourseId(@Param("courseId") Integer courseId);
}