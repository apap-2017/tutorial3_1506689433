package com.example.tutorial3.controller;

import com.example.tutorial3.model.StudentModel;
import com.example.tutorial3.service.InMemoryStudentService;
import com.example.tutorial3.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by AufaHR on 9/23/2017.
 */

@Controller
public class StudentController {
    private final StudentService studentService;

    @Autowired
    private ErrorAttributes errorAttributes;

    public StudentController() {
        this.studentService = new InMemoryStudentService();
    }

    @RequestMapping("/student/add")
    public String add(@RequestParam(value = "npm", required = true) String npm,
                      @RequestParam(value = "name", required = true) String name,
                      @RequestParam(value = "gpa", required = true) Double gpa) {
        this.studentService.addStudent(new StudentModel(name, npm, gpa));
        return "add";
    }

    @RequestMapping("/student/viewall")
    public String viewAll(Model model) {
        model.addAttribute("students", this.studentService.selectAllStudents());
        return "viewall";
    }

    @RequestMapping("/student/view/{npm}")
    public String viewbyPath(Model model, @PathVariable(value = "npm", required = true) String npm) {
        model.addAttribute("student", this.studentService.selectStudent(npm));
        return "view";
    }

    @RequestMapping("/student/delete/{npm}")
    public String deletebyPath(Model model, @PathVariable(value = "npm", required = true) String npm) {
        try {
            this.studentService.deleteStudent(npm);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
        return "delete";
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<String> handle(HttpServletRequest request, Exception exception) {
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        Map error = this.errorAttributes.getErrorAttributes(requestAttributes, false);
        String message = error.get("message").toString();

        return new ResponseEntity<String>(message, HttpStatus.BAD_REQUEST);
    }

}
