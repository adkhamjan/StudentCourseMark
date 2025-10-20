package org.example.spring_data_query.repository;

import jakarta.transaction.Transactional;
import org.example.spring_data_query.entity.CourseEntity;
import org.example.spring_data_query.enums.Gender;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CourseRepository extends CrudRepository<CourseEntity,Integer>, PagingAndSortingRepository<CourseEntity,Integer> {
    Optional<CourseEntity> findById(Integer id);

    @Transactional
    @Modifying
    void deleteById(Integer id);

    List<CourseEntity> getByName(String name);
    List<CourseEntity> getByPrice(Double price);
    List<CourseEntity> getByDuration(LocalDate duration);

    List<CourseEntity> getByPriceBetween(Double priceStart, Double priceEnd);
    List<CourseEntity> getByCreatedDateBetween(LocalDateTime createdDate, LocalDateTime createdDate2);

    @Query("from CourseEntity where id = ?1")
    Optional<CourseEntity> getById(Integer id);

    @Query("from CourseEntity where name = ?1")
    List<CourseEntity> getByNameQ(String name);

    @Query("from CourseEntity where price = ?1")
    List<CourseEntity> getByPriceQ(Double price);

    @Query("from CourseEntity where duration = ?1")
    List<CourseEntity> getByDurationQ(LocalDate duration);

    @Query("from CourseEntity where price between ?1 and ?2")
    List<CourseEntity> getByPriceBetweenQ(Double priceStart, Double priceEnd);

    @Query("from CourseEntity where createdDate between ?1 and ?2")
    List<CourseEntity> getByDateBetween(LocalDateTime createdDate, LocalDateTime createdDate2);

    Page<CourseEntity> findByPrice(Double price, Pageable pageable);

    @Query("From CourseEntity where price =?1 ")
    Page<CourseEntity> findByPriceWithQuery(Double price, Pageable pageable);

    Page<CourseEntity> findByPriceBetween(Double priceAfter, Double priceBefore, Pageable pageable);

    @Query("SELECT c FROM CourseEntity c WHERE c.price between ?1 and ?2")
    Page<CourseEntity> findByEgeBetweenWithQuery(Integer level, Pageable pageable);
}