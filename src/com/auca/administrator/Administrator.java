package com.auca.administrator;

import java.time.LocalDate;

import com.auca.abstracts.Person;
import com.auca.exceptions.ValidationException;

public class Administrator extends Person {
  private String staffNumber;
  private String jobTitle;
  private String assignedOffice;
  private LocalDate employmentDate;
  private int accessLevel;
  private boolean active;

  public Administrator(String personId, String firstName, String lastName, String email, String phoneNumber,
      LocalDate dateOfBirth, String staffNumber, String jobTitle, String assignedOffice, LocalDate employmentDate,
      int accessLevel, boolean active) {
    super(personId, firstName, lastName, email, phoneNumber, dateOfBirth);
    this.staffNumber = staffNumber;
    this.jobTitle = jobTitle;
    this.assignedOffice = assignedOffice;
    this.employmentDate = employmentDate;
    this.accessLevel = accessLevel;
    this.active = active;
  }

  public String getStaffNumber() {
    return staffNumber;
  }

  public void setStaffNumber(String staffNumber) {
    this.staffNumber = staffNumber;
  }

  public String getJobTitle() {
    return jobTitle;
  }

  public void setJobTitle(String jobTitle) {
    this.jobTitle = jobTitle;
  }

  public String getAssignedOffice() {
    return assignedOffice;
  }

  public void setAssignedOffice(String assignedOffice) {
    this.assignedOffice = assignedOffice;
  }

  public LocalDate getEmploymentDate() {
    return employmentDate;
  }

  public void setEmploymentDate(LocalDate employmentDate) {
    this.employmentDate = employmentDate;
  }

  public int getAccessLevel() {
    return accessLevel;
  }

  public void setAccessLevel(int accessLevel) {
    this.accessLevel = accessLevel;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  @Override
  public String getRoleDescription() {
    return "Administrator";
  }

  @Override
  public void validate() throws ValidationException {
    super.validate();
    if (staffNumber == null || staffNumber.isBlank()) {
      throw new ValidationException("Staff number is required.");
    }
    if (jobTitle == null || jobTitle.trim().length() < MIN_NAME_LENGTH || jobTitle.trim().length() > MAX_NAME_LENGTH) {
      throw new ValidationException("Job title must be between " + MIN_NAME_LENGTH + " and " + MAX_NAME_LENGTH + " characters.");
    }
    if (assignedOffice == null || assignedOffice.isBlank()) {
      throw new ValidationException("Assigned office is required.");
    }
    if (employmentDate == null || employmentDate.isAfter(LocalDate.now())) {
      throw new ValidationException("Employment date must not be in the future.");
    }
    if (accessLevel < 1 || accessLevel > 10) {
      throw new ValidationException("Access level must be between 1 and 10.");
    }
  }

  @Override
  public String getRegistrationSummary() {
    return super.getRegistrationSummary() + " | Staff #: " + staffNumber + " | Role: " + jobTitle;
  }

  @Override
  public String toString() {
    return "Administrator{" +
        "staffNumber='" + staffNumber + '\'' +
        ", jobTitle='" + jobTitle + '\'' +
        ", assignedOffice='" + assignedOffice + '\'' +
        ", employmentDate=" + employmentDate +
        ", accessLevel=" + accessLevel +
        ", active=" + active +
        ", " + super.toString() +
        '}';
  }
}
