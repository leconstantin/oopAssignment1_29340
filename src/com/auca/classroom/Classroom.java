package com.auca.classroom;

import com.auca.exceptions.ValidationException;
import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;

public class Classroom implements Identifiable, Registrable {
  private String classroomId;
  private String buildingName;
  private String roomNumber;
  private int capacity;
  private int floorNumber;
  private boolean available;

  public Classroom(String classroomId, String buildingName, String roomNumber, int capacity, int floorNumber,
      boolean available) {
    this.classroomId = classroomId;
    this.buildingName = buildingName;
    this.roomNumber = roomNumber;
    this.capacity = capacity;
    this.floorNumber = floorNumber;
    this.available = available;
  }

  public String getClassroomId() {
    return classroomId;
  }

  public void setClassroomId(String classroomId) {
    this.classroomId = classroomId;
  }

  public String getBuildingName() {
    return buildingName;
  }

  public void setBuildingName(String buildingName) {
    this.buildingName = buildingName;
  }

  public String getRoomNumber() {
    return roomNumber;
  }

  public void setRoomNumber(String roomNumber) {
    this.roomNumber = roomNumber;
  }

  public int getCapacity() {
    return capacity;
  }

  public void setCapacity(int capacity) {
    this.capacity = capacity;
  }

  public int getFloorNumber() {
    return floorNumber;
  }

  public void setFloorNumber(int floorNumber) {
    this.floorNumber = floorNumber;
  }

  public boolean isAvailable() {
    return available;
  }

  public void setAvailable(boolean available) {
    this.available = available;
  }

  @Override
  public String getDisplayName() {
    return buildingName + " Room " + roomNumber;
  }

  @Override
  public String getId() {
    return classroomId;
  }

  @Override
  public boolean isActive() {
    return classroomId != null && !classroomId.isBlank();
  }

  @Override
  public String getRegistrationSummary() {
    return buildingName + " - " + roomNumber + " (Capacity: " + capacity + ")";
  }

  @Override
  public void printDetails() {
    System.out.println(this);
  }

  @Override
  public void validate() throws ValidationException {
    if (classroomId == null || classroomId.isBlank()) {
      throw new ValidationException("Classroom ID is required.");
    }
    if (buildingName == null || buildingName.trim().length() < MIN_NAME_LENGTH || buildingName.trim().length() > MAX_NAME_LENGTH) {
      throw new ValidationException("Building name must be between " + MIN_NAME_LENGTH + " and " + MAX_NAME_LENGTH + " characters.");
    }
    if (roomNumber == null || roomNumber.isBlank()) {
      throw new ValidationException("Room number is required.");
    }
    if (capacity <= 0) {
      throw new ValidationException("Classroom capacity must be greater than zero.");
    }
    if (floorNumber < 0) {
      throw new ValidationException("Floor number cannot be negative.");
    }
  }

  @Override
  public String toString() {
    return "Classroom{" +
        "classroomId='" + classroomId + '\'' +
        ", buildingName='" + buildingName + '\'' +
        ", roomNumber='" + roomNumber + '\'' +
        ", capacity=" + capacity +
        ", floorNumber=" + floorNumber +
        ", available=" + available +
        '}';
  }
}
