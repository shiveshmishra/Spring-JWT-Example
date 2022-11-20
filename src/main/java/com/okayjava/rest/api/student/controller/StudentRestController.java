package com.okayjava.rest.api.student.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.okayjava.rest.api.student.error.StudentException;
import com.okayjava.rest.api.student.model.Student;
import com.okayjava.rest.api.student.service.StudentService;

@RestController
@RequestMapping("/api")
public class StudentRestController {

	@Autowired
	StudentService service;

	// POST - Create new student
	// http://localhost:8080/api/students
	@PostMapping(path = "/students", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> save(@RequestBody Student newStudent) {
		ResponseEntity<?> resp = null;
		Student s = null;
		try {
			s = service.createOrUpdate(newStudent);
			//s = null; // test
			if (s != null) {
				resp = new ResponseEntity<Student>(s, HttpStatus.CREATED); // 201
			} else {
				resp = new ResponseEntity<String>("Could not create new resource.", HttpStatus.BAD_REQUEST); // 400
			}
		} catch (Exception se) {
			se.printStackTrace();
			throw new StudentException("Opps, something went wrong", HttpStatus.INTERNAL_SERVER_ERROR); // 500
		}
		return resp;
	}

	// GET - ALL
	// http://localhost:8080/api/students
	@GetMapping("/students")
	public ResponseEntity<?> findAll() {
		ResponseEntity<?> resp = null;
		try {
			List<Student> list = new ArrayList<Student>();
			list = service.findAll();
			if (list != null && list.size() > 0) {
				resp = new ResponseEntity<List<Student>>(list, HttpStatus.FOUND);
			} else {
				resp = new ResponseEntity<String>("No record(s) found.", HttpStatus.NOT_FOUND);
			//	String s = null; //test
			//	System.out.println(s.length()); //test
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			// test
			// resp = new ResponseEntity<String>("Oops,something went wrong ",
			// HttpStatus.INTERNAL_SERVER_ERROR);
			throw new StudentException("Oops,something went wrong ", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return resp;
	}

	// Get - http://localhost:8080/api/students/1
	@GetMapping(value = "students/{id}")
	public ResponseEntity<?> getStudent(@PathVariable("id") int id) {
		ResponseEntity<?> response = null;
		try {
			Student st = service.findById(id);
			response = new ResponseEntity<Student>(st, HttpStatus.FOUND); // 302
		} catch (StudentException se) {
			throw se;
		} catch (Exception se) {
			se.printStackTrace();
			response = new ResponseEntity<String>("Opps,something went wrong ", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return response;
	}

	// PUT - MODIFY EXISTING RESOURCE
	// http://localhost:8080/api/students/1
	@PutMapping(value = "/students/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Student> update(@PathVariable("id") int id, @RequestBody Student st) {
		Student s = new Student();
		try {
			if (service.findById(id) != null) { // if the resource exists
				s.setId(id);
				if (service.allAttributesReceived(st)) {
					s.setFname(st.getFname());
					s.setLname(st.getLname());
					s.setEmail(st.getEmail());
					s = service.createOrUpdate(s);
				}
			}
			
			// // Resource Id does not exists - Create a new Resource.
			
		} catch (StudentException se) {
			throw se;
		}
		return new ResponseEntity<Student>(s, HttpStatus.OK); // 200
	}

	// PATCH - PARTIALLY UPDATE A RESOURCE
	// http://localhost:8080/api/students?id=1&email=hello@okayjava.com
	@PatchMapping("/students")
	public ResponseEntity<String> updateEmailById(@RequestParam("id") int id, @RequestParam("email") String email) {
		ResponseEntity<String> resp = null;
		try {
			if (service.findById(id) != null) {
				if (service.updateEmailById(email, id) > 0) {
					resp = new ResponseEntity<String>("Student email updated where id = " + id,
							HttpStatus.PARTIAL_CONTENT); // 206
				} else {
					resp = new ResponseEntity<String>("Could not update email where id = " + id, HttpStatus.FORBIDDEN); // 403
				}
			}
		} catch (StudentException se) {
			//se.printStackTrace();
			throw se;
		}
		return resp;
	}

	// DELETE - A RESOURCE
	// http://localhost:8080/api/students/1
	@DeleteMapping(value = "/students/{id}")
	public ResponseEntity<String> deleteById(@PathVariable("id") int id) {
		ResponseEntity<String> resp = null;
		try {
			if (service.findById(id) != null) {
				service.deleteById(id);
				resp = new ResponseEntity<String>("Student '" + id + "' deleted", HttpStatus.OK);
			}
		} catch (StudentException se) {
			throw se;
		} catch (Exception e) {
			e.printStackTrace();
			resp = new ResponseEntity<String>("Unable to delete student id = " + id, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return resp;

	}

	// Delete- ALL
	// http://localhost:8080/api/students
	@DeleteMapping("/students")
	public ResponseEntity<String> deleteAll() {
		ResponseEntity<String> resp = null;
		try {
			service.deleteAll();
			resp = new ResponseEntity<String>("All Record deleted.", HttpStatus.OK);
		} catch (Exception ex) {
			ex.printStackTrace();
			resp = new ResponseEntity<String>("Unable to delete record(s)", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return resp;
	}
}
