package org.example.spring_data_query.repository;

import jakarta.transaction.Transactional;
import org.example.spring_data_query.entity.StudentEntity;
import org.example.spring_data_query.enums.Gender;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface StudentRepository extends CrudRepository<StudentEntity, Integer>, PagingAndSortingRepository<StudentEntity, Integer> {
    @Override
    Optional<StudentEntity> findById(Integer id);

    @Transactional
    @Modifying
    void deleteById(Integer id);

    List<StudentEntity> getByName(String name);
    List<StudentEntity> getByAge(int age);
    List<StudentEntity> getBySurname(String surname);
    List<StudentEntity> getByLevel(Integer level);
    List<StudentEntity> getByGender(Gender gender);
    List<StudentEntity> getByCreatedDate(LocalDateTime createdDate);

    List<StudentEntity> findByCreatedDateBetween(LocalDateTime createdDate, LocalDateTime createdDate2);

    @Query("select s from StudentEntity s where s.id = :id ")
    Optional<StudentEntity> getById(@Param("id") Integer id);

    @Query("select s from StudentEntity s where s.name = ?1")
    List<StudentEntity> getByNames(String name);

    @Query("select s from StudentEntity s where s.age = ?1")
    List<StudentEntity> getByAges(int age);

    @Query("from StudentEntity where createdDate = ?1")
    List<StudentEntity> findByDateCreated(LocalDateTime dateCreated);

    @Query("select s from StudentEntity s where s.createdDate between :createdDateAfter and :createdDateBefore")
    List<StudentEntity> findBetween(@Param("createdDateAfter") LocalDate createdDateAfter,
                                                 @Param("createdDateBefore") LocalDate createdDateBefore);

    // select * from student where age = ? limit ? offset ?
    // select count(*) from student where age = ?
    Page<StudentEntity> findByAge(Integer age, Pageable pageable);

    // select * from student where age = ? limit ? offset ?
    // select count(*) from student where age = ?
    @Query("From StudentEntity where age =?1 ")
    Page<StudentEntity> findByAgeWithQuery(int age, Pageable pageable);

    @Query(value = "From StudentEntity where age =?1 ",
            countQuery = "select count(s) from StudentEntity s where s.age =?1")
    Page<StudentEntity> findByAgeWithQuery2(int age, Pageable pageable);

    Page<StudentEntity> findByLevel(Integer level, Pageable pageable);

    @Query("SELECT s FROM StudentEntity s WHERE s.level = ?1")
    Page<StudentEntity> findByLevelWithQuery(Integer level, Pageable pageable);

    Page<StudentEntity> findByGender(Gender gender, Pageable pageable);

    @Query("SELECT s FROM StudentEntity s WHERE s.gender = ?1")
    Page<StudentEntity> findByGenderWithQuery(Gender gender,  Pageable pageable);

//    @Query("Select id as id, name as name From StudentEntity")
//    Page<StudentMapper> findStudentMapper2(Pageable pageable);
}