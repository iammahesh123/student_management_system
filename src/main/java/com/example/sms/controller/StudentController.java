package com.example.sms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.sms.entity.Student;
import com.example.sms.service.StudentService;

import jakarta.servlet.http.HttpServletRequest;


@Controller
public class StudentController {
	private StudentService studentService;

	public StudentController(StudentService studentService) {
		super();
		this.studentService = studentService;
	}
	
	@GetMapping("/students")
	public String listStudents(Model model) {
		model.addAttribute("students",studentService.getAllStudents());
		return "students";	
		
	}
	

	
	@GetMapping("/students/new")
	public String createStudentForm(Model model) {
	    // create new object to hold new student data
	    Student student = new Student();
	    model.addAttribute("student", student);
	    return "create_students";
	}
	@PostMapping("/students")
	public String saveStudent(@ModelAttribute("student") Student student) {
		studentService.saveStudent(student);
		
		return "redirect:/students";	
	}
	
	@GetMapping("/students/edit/{id}")
	public String editStudent(@PathVariable Long id, Model model) {
		model.addAttribute("student", studentService.getStudentById(id));
		return "edit_student";	
	}
	
	@PostMapping("/students/{id}")
	public String updateStudent(@PathVariable Long id,
			@ModelAttribute("student") Student student,
			Model model) {
		
		// get student from database by id
		Student existingStudent = studentService.getStudentById(id);
		existingStudent.setId(id);
		existingStudent.setFristName(student.getFristName());
		existingStudent.setLastName(student.getLastName());
		existingStudent.setEmail(student.getEmail());
		
		// save updated student object
		studentService.updateStudent(existingStudent);
		return "redirect:/students";		
	}
	
	
	@GetMapping("/students/{id}")
	public String deleteStudent(@PathVariable Long id) {
		studentService.deleteStudentById(id);
		return "redirect:/students";
	}
	@Controller
	public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

	    @RequestMapping("/error")
	    public String handleError(HttpServletRequest request, Model model) {
	        // Provide custom error handling logic or information
	        model.addAttribute("error", "An error occurred");
	        return "error"; // Use an appropriate error page
	    }

	    public String getErrorPath() {
	        return "/error";
	    }
	}

	

}
