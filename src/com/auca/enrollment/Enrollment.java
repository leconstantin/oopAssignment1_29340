package com.auca.enrollment;

import java.time.LocalDate;

import com.auca.course.Course;
import com.auca.exceptions.ValidationException;
import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.student.Student;

public class Enrollment implements Identifiable, Registrable {
  private String enrollmentId;
  private Student student;
  private Course course;
  private LocalDate enrollmentDate;
  private String semester;
  private String enrollmentStatus;

  public Enrollment(String enrollmentId, Student student, Course course, LocalDate enrollmentDate, String semester,
      String enrollmentStatus) {
    this.enrollmentId = enrollmentId;
    this.student = student;
    this.course = course;
    this.enrollmentDate = enrollmentDate;
    this.semester = semester;
    this.enrollmentStatus = enrollmentStatus;
  }

  public String getEnrollmentId() {
    return enrollmentId;
  }

  public void setEnrollmentId(String enrollmentId) {
    this.enrollmentId = enrollmentId;
  }

  public Student getStudent() {
    return student;
  }

  public void setStudent(Student student) {
    this.student = student;
  }

  public Course getCourse() {
    return course;
  }

  public void setCourse(Course course) {
    this.course = course;
  }

  public LocalDate getEnrollmentDate() {
    return enrollmentDate;
  }

  public void setEnrollmentDate(LocalDate enrollmentDate) {
    this.enrollmentDate = enrollmentDate;
  }

  public String getSemester() {
    return semester;
  }

  public void setSemester(String semester) {
    this.semester = semester;
  }

  public String getEnrollmentStatus() {
    return enrollmentStatus;
  }

  public void setEnrollmentStatus(String enrollmentStatus) {
    this.enrollmentStatus = enrollmentStatus;
  }

  @Override
  public String getDisplayName() {
    return student.getDisplayName() + " -> " + course.getCourseCode();
  }

  @Override
  public String getId() {
    return enrollmentId;
  }

  @Override
  public boolean isActive() {
    return enrollmentStatus != null && enrollmentStatus.equalsIgnoreCase("ACTIVE");
  }

  @Override
  public String getRegistrationSummary() {
    return student.getDisplayName() + " enrolled in " + course.getCourseCode() + " (" + semester + ")";
  }

  @Override
  public void printDetails() {
    System.out.println(this);
  }

  @Override
  public void validate() throws ValidationException {
    if (enrollmentId == null || enrollmentId.isBlank()) {
      throw new ValidationException("Enrollment ID is required.");
    }
    if (student == null) {
      throw new ValidationException("Student is required.");
    }
    if (course == null) {
      throw new ValidationException("Course is required.");
    }
    if (enrollmentDate == null || enrollmentDate.isAfter(LocalDate.now())) {
      throw new ValidationException("Enrollment date must not be in the future.");
    }
    if (semester == null || semester.isBlank()) {
      throw new ValidationException("Semester is required.");
    }
    if (enrollmentStatus == null || (!enrollmentStatus.equalsIgnoreCase("ACTIVE") && !enrollmentStatus.equalsIgnoreCase("COMPLETED"))) {
      throw new ValidationException("Enrollment status must be ACTIVE or COMPLETED.");
    }
  }

  @Override
  public String toString() {
    return "Enrollment{" +
        "enrollmentId='" + enrollmentId + '\'' +
        ", student=" + student.getDisplayName() +
        ", course=" + course.getCourseCode() +
        ", enrollmentDate=" + enrollmentDate +
        ", semester='" + semester + '\'' +
        ", enrollmentStatus='" + enrollmentStatus + '\'' +
        '}';
  }
}
