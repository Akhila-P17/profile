package org.tool.teacher;

import org.springframework.stereotype.Component;

@Component
public class ResponseMessage2 {
	
	private String Status;
	
	private String message;
	
	
	
	
	public ResponseMessage2() {
	}



	public ResponseMessage2(String status, String message) {
		Status = status;
		this.message = message;
	}
	
	

	public String getStatus() {
		return Status;
	}



	public void setStatus(String status) {
		Status = status;
	}



	public String getMessage() {
		return message;
	}



	public void setMessage(String message) {
		this.message = message;
	}



	
	
	

}
