package com.auca.course;

import com.auca.department.Department;
import com.auca.exceptions.ValidationException;
import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.teacher.Teacher;

public class Course implements Identifiable, Registrable {
  private String courseId;
  private String courseCode;
  private String courseTitle;
  private int creditHours;
  private Department department;
  private Teacher assignedTeacher;

  public Course(String courseId, String courseCode, String courseTitle, int creditHours, Department department,
      Teacher assignedTeacher) {
    this.courseId = courseId;
    this.courseCode = courseCode;
    this.courseTitle = courseTitle;
    this.creditHours = creditHours;
    this.department = department;
    this.assignedTeacher = assignedTeacher;
  }

  public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
  }

  public String getCourseCode() {
    return courseCode;
  }

  public void setCourseCode(String courseCode) {
    this.courseCode = courseCode;
  }

  public String getCourseTitle() {
    return courseTitle;
  }

  public void setCourseTitle(String courseTitle) {
    this.courseTitle = courseTitle;
  }

  public int getCreditHours() {
    return creditHours;
  }

  public void setCreditHours(int creditHours) {
    this.creditHours = creditHours;
  }

  public Department getDepartment() {
    return department;
  }

  public void setDepartment(Department department) {
    this.department = department;
  }

  public Teacher getAssignedTeacher() {
    return assignedTeacher;
  }

  public void setAssignedTeacher(Teacher assignedTeacher) {
    this.assignedTeacher = assignedTeacher;
  }

  @Override
  public String getDisplayName() {
    return courseTitle;
  }

  @Override
  public String getId() {
    return courseId;
  }

  @Override
  public boolean isActive() {
    return courseId != null && !courseId.isBlank() && courseTitle != null && !courseTitle.isBlank();
  }

  @Override
  public String getRegistrationSummary() {
    return courseCode + " - " + courseTitle + " (" + creditHours + " hrs)" + " | Dept: " + (department == null ? "N/A" : department.getDepartmentName());
  }

  @Override
  public void printDetails() {
    System.out.println(this);
  }

  @Override
  public void validate() throws ValidationException {
    if (courseId == null || courseId.isBlank()) {
      throw new ValidationException("Course ID is required.");
    }
    if (courseCode == null || courseCode.isBlank()) {
      throw new ValidationException("Course code is required.");
    }
    if (courseTitle == null || courseTitle.trim().length() < MIN_NAME_LENGTH || courseTitle.trim().length() > MAX_NAME_LENGTH) {
      throw new ValidationException("Course title must be between " + MIN_NAME_LENGTH + " and " + MAX_NAME_LENGTH + " characters.");
    }
    if (creditHours < 1 || creditHours > 6) {
      throw new ValidationException("Credit hours must be between 1 and 6.");
    }
    if (department == null) {
      throw new ValidationException("Course department is required.");
    }
    if (assignedTeacher == null) {
      throw new ValidationException("Assigned teacher is required.");
    }
  }

  @Override
  public String toString() {
    return "Course{" +
        "courseId='" + courseId + '\'' +
        ", courseCode='" + courseCode + '\'' +
        ", courseTitle='" + courseTitle + '\'' +
        ", creditHours=" + creditHours +
        ", department=" + (department == null ? "null" : department.getDepartmentName()) +
        ", assignedTeacher=" + (assignedTeacher == null ? "null" : assignedTeacher.getDisplayName()) +
        '}';
  }
}
