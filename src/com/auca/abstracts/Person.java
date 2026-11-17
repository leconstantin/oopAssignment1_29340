package com.auca.abstracts;

import java.time.LocalDate;
import java.util.Objects;

import com.auca.exceptions.ValidationException;
import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;

public abstract class Person implements Identifiable, Registrable {
  private String personId;
  private String firstName;
  private String lastName;
  private String email;
  private String phoneNumber;
  private LocalDate dateOfBirth;

  public Person(String personId, String firstName, String lastName, String email, String phoneNumber,
      LocalDate dateOfBirth) {
    this.personId = personId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.dateOfBirth = dateOfBirth;
  }

  public String getPersonId() {
    return personId;
  }

  public void setPersonId(String personId) {
    this.personId = personId;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public LocalDate getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(LocalDate dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  @Override
  public String getId() {
    return personId;
  }

  @Override
  public String getDisplayName() {
    if (firstName == null && lastName == null) {
      return personId;
    }
    return ((firstName == null) ? "" : firstName) + " " + ((lastName == null) ? "" : lastName).trim();
  }

  @Override
  public boolean isActive() {
    return personId != null && !personId.isBlank() && firstName != null && !firstName.isBlank()
        && lastName != null && !lastName.isBlank();
  }

  public abstract String getRoleDescription();

  @Override
  public void printDetails() {
    System.out.println(this);
  }

  @Override
  public String getRegistrationSummary() {
    return getDisplayName() + " (ID: " + personId + ")";
  }

  @Override
  public void validate() throws ValidationException {
    if (personId == null || personId.isBlank()) {
      throw new ValidationException("Person ID is required.");
    }
    if (personId.length() < Identifiable.MIN_ID_LENGTH || personId.length() > Identifiable.MAX_ID_LENGTH) {
      throw new ValidationException("Person ID length must be between " + Identifiable.MIN_ID_LENGTH + " and "
          + Identifiable.MAX_ID_LENGTH + ".");
    }
    if (firstName == null || firstName.trim().length() < MIN_NAME_LENGTH || firstName.trim().length() > MAX_NAME_LENGTH) {
      throw new ValidationException("First name must be between " + MIN_NAME_LENGTH + " and " + MAX_NAME_LENGTH + " characters.");
    }
    if (lastName == null || lastName.trim().length() < MIN_NAME_LENGTH || lastName.trim().length() > MAX_NAME_LENGTH) {
      throw new ValidationException("Last name must be between " + MIN_NAME_LENGTH + " and " + MAX_NAME_LENGTH + " characters.");
    }
    if (email == null || email.trim().isEmpty() || !email.contains("@") || email.length() > MAX_EMAIL_LENGTH) {
      throw new ValidationException("Email must be a valid address with maximum length " + MAX_EMAIL_LENGTH + ".");
    }
    if (phoneNumber == null || !phoneNumber.matches("^\\+?[0-9]{10,15}$")) {
      throw new ValidationException("Phone number must contain 10 to 15 digits, optionally with a leading +.");
    }
    if (dateOfBirth == null || dateOfBirth.isAfter(LocalDate.now())) {
      throw new ValidationException("Date of birth must be in the past.");
    }
  }

  @Override
  public String toString() {
    return "Person{" +
        "personId='" + personId + '\'' +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", email='" + email + '\'' +
        ", phoneNumber='" + phoneNumber + '\'' +
        ", dateOfBirth=" + dateOfBirth +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Person)) {
      return false;
    }
    Person person = (Person) o;
    return Objects.equals(personId, person.personId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(personId);
  }
}
