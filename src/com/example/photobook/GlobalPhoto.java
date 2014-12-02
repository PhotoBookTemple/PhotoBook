package com.example.photobook;

import android.app.Application;

public class GlobalPhoto extends Application{

	private String userName;
	private int userID;
	private String message;
	private boolean again;
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String uName) {
		userName = uName;
	}
	
	public int getUserID() {
		return userID;
	}
	
	public void setUserID(int uID) {
		userID = uID;
	}

	public String getMessage() {
		return message;
	}
	
	public void setMessage(String uMSG) {
		message = uMSG;
	}
	
	public boolean getAgain() {
		return again;
	}
	
	public void setAgain(boolean uAgain) {
		again = uAgain;
	}
	
}