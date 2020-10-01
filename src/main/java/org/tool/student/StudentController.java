package org.tool.student;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.tool.UserRepository;
import org.tool.auth.User;

import org.tool.teacher.TeacSubjectEntity;
import org.tool.teacher.TeacSubjectRepository;

import javassist.bytecode.Descriptor.Iterator;

@Component
@RestController
public class StudentController {
	
	

	@Autowired
	private StudentRepository tRepo;
	
	@Autowired
	private SubjectRepository sRepo;
	
	@Autowired
	private ResponseMessage resp;
	
	@Autowired
	private TeacSubjectRepository teacSubRepo;
	
	@Autowired
	private UserRepository uRepo;
	
		
	@GetMapping("/register-student")
	public String check() {
		
		System.out.println("get req");
		return "Got get!!";
	}
	
	
	@PostMapping("/register/student")
	public ResponseMessage registerStudent(@RequestBody StudentEntity student ) {
		

		
		if(! tRepo.existsStudentEntityByEmail(student.getEmail())) {
			
			List<SubjectEntity>  newList = new ArrayList<SubjectEntity>();
			newList.addAll(student.getSubjectList());
			student.getSubjectList().clear();
			
		
			
			
			for (int i = 0; i < newList.size(); i++) {
				
				TeacSubjectEntity teacherSubject = new TeacSubjectEntity();
				SubjectEntity studentSubject = new SubjectEntity();
				
				if (  teacSubRepo.existsTeacSubjectEntityById( newList.get(i).getId()) ) {
					
					teacherSubject = teacSubRepo.findTeacSubjectEntityById( newList.get(i).getId() );
				
			
					studentSubject.setId( newList.get(i).getId() );
					
					student.getSubjectList().add(studentSubject);
//					return(studentSubject);
				}
				else {
					resp.setStatus("Subject not Found");
					resp.setMessage("specify corrrect subjectList!!");
					return resp;
				}
				
			}
				

				//student.getSubjectList().clear();
				//student.setSubjects(newList);
				
				student.setPassword(RandomStringUtils.random(10, true, true));
				
				MailService.send(student.getEmail(), "Registration Successful ", " Your user_id : " + student.getEmail() +  "  password : " + student.getPassword());
				
				tRepo.save(student);
				User user = new User( student.getStudent_id(), student.getEmail(),  new BCryptPasswordEncoder(11).encode( student.getPassword()) , "STUDENT" , true, true, true, true);
				uRepo.save(user);
			
			
			resp.setStatus("success");
			resp.setMessage("Your User ID and Password are sent to your mail. Please use them to login and change password for successful registration.");
			return  resp;
			
		}else {
			
			resp.setStatus("failure");
			resp.setMessage("User already registered. Please register with a different email or click forgot password button to reset your password.");
			return resp;
		}
	
		
		
	}
	
	
	 
	
	
	

}
