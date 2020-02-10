package com.project.demo.repositories;

import com.project.demo.entities.Courses;
import com.project.demo.entities.Students;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentsRepository extends JpaRepository<Students, Long> {

    List<Students> findAll();
    Optional<Students> findById(Long id);
    void deleteById(Long id);

//    @Modifying
//    @Query("SELECT c FROM ")
//    void updateCategory(@Param("name") String name, @Param("id") Long id);
}
