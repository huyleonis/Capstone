package com.fpt.capstone.Firebase;

import java.util.HashMap;
import java.util.Map;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ReportNotification {
	
	public static void writeNewReport(String transactionId, String username) {
		
		Map<String, Object> update = new HashMap<>();
		DatabaseReference database =  FirebaseDatabase.getInstance().getReference();
		
		String key = database.child("reports").push().getKey();
		
		update.put("/reports/" + key, new Report(transactionId, username, "unread"));
		
		database.updateChildrenAsync(update);
	}
}
