package com.project.demo.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "courses")
public class Courses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "credits")
    private int credits;

    @ManyToMany(fetch = FetchType.EAGER)
    //@JoinColumn(name = "students")
    private Set<Students> students;

    public Courses(Long id, String name, int credits) {//Что бы выйти из косяка
        this.name = name;
        this.credits = credits;
    }
}
