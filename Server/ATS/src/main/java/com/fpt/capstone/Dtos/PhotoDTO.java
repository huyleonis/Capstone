package com.fpt.capstone.Dtos;

public class PhotoDTO {

	private String photoName;
	private String licensePlate;
	private String createdTime;
	
	public PhotoDTO() {
		// TODO Auto-generated constructor stub
	}
	
	
	public PhotoDTO(String photoName, String licensePlate, String createdTime) {
		this.photoName = photoName;
		this.licensePlate = licensePlate;
		this.createdTime = createdTime;
	}


	public String getPhotoName() {
		return photoName;
	}


	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}


	public String getLicensePlate() {
		return licensePlate;
	}


	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}


	public String getCreatedTime() {
		return createdTime;
	}


	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}
	
}
