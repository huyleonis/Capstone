package com.fpt.capstone.Firebase;

import java.util.HashMap;
import java.util.Map;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Report {

	public String transactionId;
	public String username;
	public String status;
	
	public Report() {
		// TODO Auto-generated constructor stub
	}

	public Report(String transactionId, String username, String status) {
		this.transactionId = transactionId;
		this.username = username;
		this.status = status;
	}
	
	public Map<String, Object> toMap() {
		HashMap<String, Object> result = new HashMap<>();
		
		result.put("transactionId", transactionId);
		result.put("username", username);
		result.put("status", status);
		
		return result;
	}
}
