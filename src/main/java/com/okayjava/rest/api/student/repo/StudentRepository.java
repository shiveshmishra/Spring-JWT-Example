package com.okayjava.rest.api.student.repo;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.okayjava.rest.api.student.model.Student;

public interface StudentRepository extends CrudRepository<Student, Integer>{

	// Update is Non-Select Operation, so @Modifying is used
		@Modifying
		@Query("update STUDENT_APIDATA s set s.email=:email WHERE s.id=:id")
		Integer updateEmailById(String email,int id);
		
}
