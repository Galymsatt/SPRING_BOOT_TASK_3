package com.project.demo.repositories;

import com.project.demo.entities.Groups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupsRepository extends JpaRepository<Groups, Long> {

    List<Groups> findAll();
    Optional<Groups> findById(Long id);
}
