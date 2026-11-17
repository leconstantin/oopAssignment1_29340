package com.auca.abstracts;

import java.time.LocalDate;

import com.auca.department.Department;
import com.auca.exceptions.ValidationException;

public abstract class AcademicMember extends Person {
  private String memberNumber;
  private Department department;
  private LocalDate joinDate;
  private String academicStatus;
  private String institutionEmail;
  private String advisorName;

  public AcademicMember(String personId, String firstName, String lastName, String email, String phoneNumber,
      LocalDate dateOfBirth, String memberNumber, Department department, LocalDate joinDate, String academicStatus,
      String institutionEmail, String advisorName) {
    super(personId, firstName, lastName, email, phoneNumber, dateOfBirth);
    this.memberNumber = memberNumber;
    this.department = department;
    this.joinDate = joinDate;
    this.academicStatus = academicStatus;
    this.institutionEmail = institutionEmail;
    this.advisorName = advisorName;
  }

  public String getMemberNumber() {
    return memberNumber;
  }

  public void setMemberNumber(String memberNumber) {
    this.memberNumber = memberNumber;
  }

  public Department getDepartment() {
    return department;
  }

  public void setDepartment(Department department) {
    this.department = department;
  }

  public LocalDate getJoinDate() {
    return joinDate;
  }

  public void setJoinDate(LocalDate joinDate) {
    this.joinDate = joinDate;
  }

  public String getAcademicStatus() {
    return academicStatus;
  }

  public void setAcademicStatus(String academicStatus) {
    this.academicStatus = academicStatus;
  }

  public String getInstitutionEmail() {
    return institutionEmail;
  }

  public void setInstitutionEmail(String institutionEmail) {
    this.institutionEmail = institutionEmail;
  }

  public String getAdvisorName() {
    return advisorName;
  }

  public void setAdvisorName(String advisorName) {
    this.advisorName = advisorName;
  }

  public abstract String getAcademicCategory();

  @Override
  public void validate() throws ValidationException {
    super.validate();
    if (memberNumber == null || memberNumber.isBlank()) {
      throw new ValidationException("Member number is required.");
    }
    if (department == null) {
      throw new ValidationException("Department is required for an academic member.");
    }
    if (joinDate == null || joinDate.isAfter(LocalDate.now())) {
      throw new ValidationException("Join date must be present and not in the future.");
    }
    if (institutionEmail == null || !institutionEmail.contains("@")) {
      throw new ValidationException("Institutional email is required and must be valid.");
    }
  }

  @Override
  public String getRegistrationSummary() {
    return super.getDisplayName() + " | Member: " + memberNumber + " | Dept: " + (department == null ? "N/A" : department.getDepartmentName());
  }

  @Override
  public String toString() {
    return "AcademicMember{" +
        "memberNumber='" + memberNumber + '\'' +
        ", department=" + department +
        ", joinDate=" + joinDate +
        ", academicStatus='" + academicStatus + '\'' +
        ", institutionEmail='" + institutionEmail + '\'' +
        ", advisorName='" + advisorName + '\'' +
        '}';
  }
}
