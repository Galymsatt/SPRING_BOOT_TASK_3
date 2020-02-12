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

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
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

        List<Courses> notAttend = coursesRepository.findAll();
        notAttend.removeAll(student.get().getCourses());
        model.addAttribute("notAttendCourses", notAttend);
        ///////////////////////////////////////////////////////////////////////

        List<Groups> notAttendGroups = groupsRepository.findAll();
        notAttendGroups.removeAll(student.get().getGroups());
        model.addAttribute("notAttendGroups", notAttendGroups);



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

/*    @GetMapping(path = "/addCourseToStudentPage/{idshka}")//Artyk, kerek emes eken
    public String addCourseToStudentPage(ModelMap model, @PathVariable(name = "idshka") Long id){
        Optional<Students> opStudent = studentsRepository.findById(id);
//        if (opStudent.isPresent()) {
            Students s = opStudent.get();
            List<Courses> notAttendCourses = coursesRepository.findCoursesByStudentsIsNotContaining(s);
//        }

//        for (Courses c : notAttendCourses)//Pashet
//            System.out.println(c.getName());

        model.addAttribute("notAttendCourses", notAttendCourses);
        model.addAttribute("studentId", id);

        return "addCourseToStudentPage";
    }*/


    @PostMapping(path = "/addCourseToStudent")
    public String addCourseToStudent(@RequestParam(name = "studentId") Long studentId,
                                    @RequestParam(name = "courseId") Long courseId){

        Optional<Students> student = studentsRepository.findById(studentId);//Searching student
        Optional<Courses> course = coursesRepository.findById(courseId);//Searching course
        if(student.isPresent() && course.isPresent()){
            student.get().getCourses().add(coursesRepository.findById(courseId).get());//Tut ya zakidyvayu sootvetstvuyushi curs k studentu
            studentsRepository.save(student.get());

        }

        return "redirect:/editStudentPage/"+studentId;
    }

    @PostMapping(path = "/removeCourseFromStudent")
    public String removeCourseFromStudent(@RequestParam(name = "course_id") Long course_id,
                                          @RequestParam(name = "student_id") Long student_id){

        Students student = studentsRepository.findById(student_id).get();
        student.getCourses().remove(coursesRepository.findById(course_id).get());
        studentsRepository.save(student);

        return "redirect:/editStudentPage/"+student_id;
    }

    /////////////////////////////ADD GROUP/////////////////////////////////////////////////////////////////////////

    @PostMapping(path = "/addGroupToStudent")
    public String addGroupToStudent(@RequestParam(name = "studentId") Long studentId,
                                    @RequestParam(name = "groupId") Long groupId){

        Students student = studentsRepository.findById(studentId).get();
        student.getGroups().add(groupsRepository.findById(groupId).get());
        studentsRepository.save(student);

        return "redirect:/editStudentPage/"+studentId;
    }

    @PostMapping(path = "/removeGroupFromStudent")
    public String removeGroupFromStudent(@RequestParam(name = "group_id") Long group_id,
                                          @RequestParam(name = "student_id") Long student_id){

        Students student = studentsRepository.findById(student_id).get();
        student.getGroups().remove(groupsRepository.findById(group_id).get());
        studentsRepository.save(student);

        return "redirect:/editStudentPage/"+student_id;
    }
}
