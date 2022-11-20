package com.okayjava.rest.api.student.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.okayjava.rest.api.student.error.StudentException;
import com.okayjava.rest.api.student.model.Student;
import com.okayjava.rest.api.student.repo.StudentRepository;

@Service
public class StudentService {

	@Autowired
	StudentRepository repo;

	public Student findById(int id) {
		Optional<Student> st = repo.findById(id);
		if(st.isPresent()) {
			return st.get();
		}else {
			throw new StudentException("No record found", HttpStatus.NOT_FOUND);
		}
	}

	public List<Student> findAll() {
		List<Student> list = new ArrayList<Student>();
		Iterable<Student> all = repo.findAll();
		all.forEach(list::add);
		return list;
	}

	public Student createOrUpdate(Student newStudent) {
		Student s = repo.save(newStudent);
		return s;
	}

	public void deleteById(int id) {
		repo.deleteById(id);
	}

	@Transactional
	public int updateEmailById(String email, int id) {
		if(email!=null && email!="") {
			return repo.updateEmailById(email, id);
		}else {
			throw new StudentException("Missing email id from request.", HttpStatus.BAD_REQUEST);
		}
		
	}
	
	public boolean allAttributesReceived(Student st) {
		if (st.getFname() != null && st.getFname() != "" 
				&& st.getLname() != null && st.getLname() != ""
				&& st.getEmail() != null && st.getEmail() != "") {
			return true;
		} else {
			throw new StudentException("Missing content", HttpStatus.BAD_REQUEST);
		}

	}

	public void deleteAll() {
		repo.deleteAll();
	}

}
