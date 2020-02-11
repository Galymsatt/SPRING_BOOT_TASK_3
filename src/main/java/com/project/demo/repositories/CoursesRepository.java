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
public interface CoursesRepository extends JpaRepository<Courses, Long> {

    List<Courses> findAll();
    Optional<Courses> findById(Long id);
    List<Courses> findCoursesByStudentsIsNot(Students s);//Ne checkal, skoree ne pashet

    List<Courses> findCoursesByStudentsIsNotContaining(Students s);

    List<Courses> findByStudentsIsNotContaining(Students s);

//    @Query("SELECT c FROM Courses c WHERE :student not in : c.students")
//    List<Courses> findCoursesNotAttend(@Param("student") Students student);

//    @Modifying
//    @Query("SELECT c FROM Courses c WHERE c.")
//    void updateCategory(@Param("name") String name, @Param("id") Long id);
}
