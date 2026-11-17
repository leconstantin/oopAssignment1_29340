package com.auca.assessment;

import java.time.LocalDate;

import com.auca.enrollment.Enrollment;
import com.auca.exceptions.ValidationException;
import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;

public class Assessment implements Identifiable, Registrable {
  private String assessmentId;
  private Enrollment enrollment;
  private String assessmentType;
  private double maximumScore;
  private double obtainedScore;
  private LocalDate assessmentDate;

  public Assessment(String assessmentId, Enrollment enrollment, String assessmentType, double maximumScore,
      double obtainedScore, LocalDate assessmentDate) {
    this.assessmentId = assessmentId;
    this.enrollment = enrollment;
    this.assessmentType = assessmentType;
    this.maximumScore = maximumScore;
    this.obtainedScore = obtainedScore;
    this.assessmentDate = assessmentDate;
  }

  public String getAssessmentId() {
    return assessmentId;
  }

  public void setAssessmentId(String assessmentId) {
    this.assessmentId = assessmentId;
  }

  public Enrollment getEnrollment() {
    return enrollment;
  }

  public void setEnrollment(Enrollment enrollment) {
    this.enrollment = enrollment;
  }

  public String getAssessmentType() {
    return assessmentType;
  }

  public void setAssessmentType(String assessmentType) {
    this.assessmentType = assessmentType;
  }

  public double getMaximumScore() {
    return maximumScore;
  }

  public void setMaximumScore(double maximumScore) {
    this.maximumScore = maximumScore;
  }

  public double getObtainedScore() {
    return obtainedScore;
  }

  public void setObtainedScore(double obtainedScore) {
    this.obtainedScore = obtainedScore;
  }

  public LocalDate getAssessmentDate() {
    return assessmentDate;
  }

  public void setAssessmentDate(LocalDate assessmentDate) {
    this.assessmentDate = assessmentDate;
  }

  @Override
  public String getDisplayName() {
    return assessmentType + " for " + enrollment.getStudent().getDisplayName();
  }

  @Override
  public String getId() {
    return assessmentId;
  }

  @Override
  public boolean isActive() {
    return enrollment != null && enrollment.isActive();
  }

  @Override
  public String getRegistrationSummary() {
    return assessmentType + " | Score: " + obtainedScore + "/" + maximumScore + " | Student: " + enrollment.getStudent().getDisplayName();
  }

  @Override
  public void printDetails() {
    System.out.println(this);
  }

  @Override
  public void validate() throws ValidationException {
    if (assessmentId == null || assessmentId.isBlank()) {
      throw new ValidationException("Assessment ID is required.");
    }
    if (enrollment == null) {
      throw new ValidationException("Enrollment is required.");
    }
    if (assessmentType == null || assessmentType.isBlank()) {
      throw new ValidationException("Assessment type is required.");
    }
    if (maximumScore <= 0) {
      throw new ValidationException("Maximum score must be greater than zero.");
    }
    if (obtainedScore < 0 || obtainedScore > maximumScore) {
      throw new ValidationException("Obtained score must be between 0 and maximum score.");
    }
    if (assessmentDate == null || assessmentDate.isAfter(LocalDate.now())) {
      throw new ValidationException("Assessment date must not be in the future.");
    }
  }

  @Override
  public String toString() {
    return "Assessment{" +
        "assessmentId='" + assessmentId + '\'' +
        ", enrollment=" + enrollment.getDisplayName() +
        ", assessmentType='" + assessmentType + '\'' +
        ", maximumScore=" + maximumScore +
        ", obtainedScore=" + obtainedScore +
        ", assessmentDate=" + assessmentDate +
        '}';
  }
}
