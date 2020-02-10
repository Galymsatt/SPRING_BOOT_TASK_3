package com.project.demo.controller;

import com.project.demo.entities.Courses;
import com.project.demo.entities.Groups;
import com.project.demo.entities.Students;
import com.project.demo.repositories.CoursesRepository;
import com.project.demo.repositories.GroupsRepository;
import com.project.demo.repositories.StudentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class MainController {

    @Autowired
    CoursesRepository coursesRepository;

    @Autowired
    GroupsRepository groupsRepository;

    @Autowired
    StudentsRepository studentsRepository;


    @GetMapping(path = "/")
    public String index(ModelMap model){
        return "index";
    }

    @GetMapping(path = "/courses")
    public String courses(ModelMap model){

        List<Courses> courses = coursesRepository.findAll();
        model.addAttribute("courses", courses);
        return "courses";
    }

    @PostMapping(path = "/addCourse")
    public String addCourse(@RequestParam(name = "course") String name,
                            @RequestParam(name = "credits") int credits){

        coursesRepository.save(new Courses(null, name, credits));

        return "redirect:/courses";
    }

    @GetMapping(path = "/editCoursePage/{id}")
    public String editCoursePage(ModelMap model,
                                 @PathVariable(name = "id") Long id){

        Optional<Courses> course = coursesRepository.findById(id);
        model.addAttribute("course", course.orElse(new Courses(0L, "No Name", 0)));

        return "editCoursePage";
    }

    @PostMapping(path = "/editCourse")
    public String editCourse(@RequestParam(name = "id") Long id,
                             @RequestParam(name = "course") String name,
                             @RequestParam(name = "credits") int credits){

        Optional<Courses> course = coursesRepository.findById(id);
        if(course.isPresent()){
            course.get().setName(name);
            course.get().setCredits(credits);

            coursesRepository.save(course.get());
        }

        return "redirect:/courses";
    }

    /////////////////////////////END COURSE////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////////GROUPS//////////////////////////////////////////////////////////////////

    @GetMapping(path = "/groups")
    public String groups(ModelMap model){

        List<Groups> groups = groupsRepository.findAll();
        model.addAttribute("groups", groups);
        return "groups";
    }

    @PostMapping(path = "/addGroup")
    public String addGroup(@RequestParam(name = "name") String name,
                           @RequestParam(name = "shortName") String shortName){

        groupsRepository.save(new Groups(null, name, shortName));

        return "redirect:/groups";
    }

    //////////////////////////END GROUPS///////////////////////////////////////////////////////

    ///////////////////////////////STUDENTS///////////////////////////////////////////////////////

    @GetMapping(path = "/students")
    public String students(ModelMap model){

        List<Students> students = studentsRepository.findAll();
        model.addAttribute("students", students);
        return "students";
    }

    @PostMapping(path = "/addStudent")
    public String addStudent(@RequestParam(name = "name") String name,
                             @RequestParam(name = "surname") String surname,
                             @RequestParam(name = "year") int year){

        studentsRepository.save(new Students(null, name, surname, year, null, null));

        return "redirect:/students";
    }

    @GetMapping(path = "/editStudentPage/{id}")
    public String editStudentPage(ModelMap model, @PathVariable(name = "id") Long id){

        Optional<Students> student = studentsRepository.findById(id);
        model.addAttribute("student", student.orElse(new Students(null, "No Name", "No Name", 0, null, null)));


        //model.addAttribute("studentCourses", studentCourses);

        return "editStudentPage";
    }

    @PostMapping(path = "/editStudent")
    public String editStudent(@RequestParam(name = "id") Long id,
                              @RequestParam(name = "name") String name,
                              @RequestParam(name = "surname") String surname,
                              @RequestParam(name = "year") int year){

        Optional<Students> student = studentsRepository.findById(id);
        if(student.isPresent()){
            student.get().setName(name);
            student.get().setSurname(surname);
            student.get().setYearOfAddmission(year);

            studentsRepository.save(student.get());
        }


        return "redirect:/editStudentPage/"+id;
    }

    @PostMapping(path = "/deleteStudent")
    public String deleteStudent(@RequestParam(name = "id") Long id){

        studentsRepository.deleteById(id);
        return "redirect:/students";
    }
}
