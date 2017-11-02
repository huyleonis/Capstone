package com.fpt.capstone.Dtos;

import com.fpt.capstone.Entities.Transaction;
import java.util.Date;

public class TransactionDTO {

	private String id;
	private int vehicleId;
	private int stationId;
	private Date dateTime;
	private String status;
	private int priceId;
	private int laneId;
	private String type;
	private String photo;
	private String failReason;

	public TransactionDTO() {
	}

	public TransactionDTO(String id, int vehicleId, int stationId, Date dateTime, String status, int priceId,
			int laneId, String type, String photo, String failReason) {
		this.id = id;
		this.vehicleId = vehicleId;
		this.stationId = stationId;
		this.dateTime = dateTime;
		this.status = status;
		this.priceId = priceId;
		this.laneId = laneId;
		this.type = type;
		this.photo = photo;
		this.failReason = failReason;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(int vehicleId) {
		this.vehicleId = vehicleId;
	}

	public int getStationId() {
		return stationId;
	}

	public void setStationId(int stationId) {
		this.stationId = stationId;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getPriceId() {
		return priceId;
	}

	public void setPriceId(int priceId) {
		this.priceId = priceId;
	}

	public int getLaneId() {
		return laneId;
	}

	public void setLaneId(int laneId) {
		this.laneId = laneId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	public static TransactionDTO convertFromEntity(Transaction transaction) {
		TransactionDTO dto = new TransactionDTO();

		dto.setId(transaction.getId());
		dto.setVehicleId(transaction.getVehicle().getId());
		dto.setStationId(transaction.getStation().getId());
		dto.setDateTime(transaction.getDateTime());
		dto.setStatus(transaction.getStatus());
		dto.setPriceId(transaction.getPrice().getId());
		if (transaction.getLane() != null) {
			dto.setLaneId(transaction.getLane().getId());
		}
		dto.setType((transaction.getType() == 1) ? "Tự động" : "Thủ công");
		if (transaction.getPhoto() != null) {
			dto.setPhoto(transaction.getPhoto());
		}

		return dto;
	}
}