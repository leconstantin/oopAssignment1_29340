package com.auca.main;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.auca.abstracts.Person;
import com.auca.administrator.Administrator;
import com.auca.assessment.Assessment;
import com.auca.bookloan.BookLoan;
import com.auca.classroom.Classroom;
import com.auca.course.Course;
import com.auca.department.Department;
import com.auca.enrollment.Enrollment;
import com.auca.exceptions.BookUnavailableException;
import com.auca.exceptions.DuplicateRecordException;
import com.auca.exceptions.PrerequisiteMissingException;
import com.auca.exceptions.RecordNotFoundException;
import com.auca.exceptions.ValidationException;
import com.auca.feepayment.FeePayment;
import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.librarybook.LibraryBook;
import com.auca.student.Student;
import com.auca.teacher.Teacher;

public class Main {
  private static final Scanner SC = new Scanner(System.in);

  private static final List<Department> DEPARTMENTS = new ArrayList<>();
  private static final List<Student> STUDENTS = new ArrayList<>();
  private static final List<Teacher> TEACHERS = new ArrayList<>();
  private static final List<Administrator> ADMINISTRATORS = new ArrayList<>();
  private static final List<Course> COURSES = new ArrayList<>();
  private static final List<Classroom> CLASSROOMS = new ArrayList<>();
  private static final List<Enrollment> ENROLLMENTS = new ArrayList<>();
  private static final List<Assessment> ASSESSMENTS = new ArrayList<>();
  private static final List<FeePayment> PAYMENTS = new ArrayList<>();
  private static final List<LibraryBook> BOOKS = new ArrayList<>();
  private static final List<BookLoan> LOANS = new ArrayList<>();

  public static void main(String[] args) {
   

    boolean running = true;
    while (running) {
      printMenu();
      String input = prompt("Select an option: "); 
      try {
        int option = Integer.parseInt(input.trim());
        switch (option) {
          case 1 -> registerDepartment();
          case 2 -> registerStudent();
          case 3 -> registerTeacher();
          case 4 -> registerAdministrator();
          case 5 -> registerCourse();
          case 6 -> registerClassroom();
          case 7 -> enrollStudent();
          case 8 -> recordAssessment();
          case 9 -> recordFeePayment();
          case 10 -> registerLibraryBook();
          case 11 -> borrowLibraryBook();
          case 12 -> returnLibraryBook();
          case 13 -> viewAllRecords();
          case 14 -> searchById();
          case 15 -> updateRecord();
          case 16 -> showStudentAcademicSummary();
          case 17 -> showPaymentHistory();
          case 18 -> showAvailableBooks();
          case 19 -> demonstratePolymorphism();
          case 0 -> {
            System.out.println("Exit");
            running = false;
          }
          default -> System.out.println("Invalid menu option. Please choose a valid number.");
        }
   
      } catch (NumberFormatException e) {
        System.out.println("Please enter a valid numeric option.");
      } catch (DateTimeParseException e) {
        System.out.println("Invalid date format. Use yyyy-MM-dd.");
      } catch (ValidationException | DuplicateRecordException | RecordNotFoundException | PrerequisiteMissingException | BookUnavailableException e) {
        System.out.println("Error: " + e.getMessage());
      } catch (Exception e) {
        System.out.println("Unexpected error: " + e.getMessage());
      }
    }
  }

  private static void printMenu() {
    System.out.println();
    System.out.println("============== AUCA_ID SCHOOL MANAGEMENT ==============");
    System.out.println(" 1. Register Department          11. Borrow Library Book");
    System.out.println(" 2. Register Student             12. Return Library Book");
    System.out.println(" 3. Register Teacher             13. View All Records");
    System.out.println(" 4. Register Administrator       14. Search Record by ID");
    System.out.println(" 5. Register Course              15. Update Existing Record");
    System.out.println(" 6. Register Classroom           16. Student Academic Summary");
    System.out.println(" 7. Enroll Student               17. Student Payment History");
    System.out.println(" 8. Record Assessment            18. View Available Books");
    System.out.println(" 9. Record Fee Payment           19. Demonstrate Polymorphism");
    System.out.println("10. Register Library Book         0. Exit");
    System.out.println("=======================================================");
  }

  private static String prompt(String promptText) {
    System.out.print(promptText);
    return SC.nextLine().trim();
  }

  private static String readNonBlank(String fieldLabel) throws ValidationException {
    String value = prompt(fieldLabel + ": ");
    if (value == null || value.isBlank()) {
      throw new ValidationException(fieldLabel + " cannot be empty.");
    }
    return value;
  }

  private static int readInt(String fieldLabel) {
    while (true) {
      String input = prompt(fieldLabel + ": ");
      try {
        return Integer.parseInt(input);
      } catch (NumberFormatException e) {
        System.out.println("Please enter a valid integer.");
      }
    }
  }

  private static LocalDate readDate(String fieldLabel) {
    while (true) {
      String input = prompt(fieldLabel + " (yyyy-MM-dd): ");
      try {
        return LocalDate.parse(input);
      } catch (DateTimeParseException e) {
        System.out.println("Invalid date. Use yyyy-MM-dd.");
      }
    }
  }

  private static BigDecimal readBigDecimal(String fieldLabel) {
    while (true) {
      String input = prompt(fieldLabel + ": ");
      try {
        return new BigDecimal(input);
      } catch (NumberFormatException e) {
        System.out.println("Invalid amount. Please enter a valid number.");
      }
    }
  }

  private static boolean readYesNo(String message) {
    while (true) {
      String choice = prompt(message + " [Y/N]: ").toLowerCase();
      if (choice.equals("y") || choice.equals("yes")) {
        return true;
      }
      if (choice.equals("n") || choice.equals("no")) {
        return false;
      }
      System.out.println("Please answer Y for yes or N for no.");
    }
  }

  private static void printRecordList(String title, List<?> records) {
    System.out.println("\n--- " + title + " ---");
    if (records == null || records.isEmpty()) {
      System.out.println("No records available.");
      return;
    }
    
    for (int i = 0; i < records.size(); i++) {
      Object item = records.get(i);
      if (item instanceof Registrable registrable) {
        System.out.println((i + 1) + ". " + registrable.getRegistrationSummary());
      } else if (item instanceof Identifiable identifiable) {
        System.out.println((i + 1) + ". " + identifiable.getDisplayName());
      } else {
        System.out.println((i + 1) + ". " + item);
      }
    }
  }

  private static <T> T selectFromList(String title, List<T> items, String emptyMessage) throws ValidationException {
    if (items == null || items.isEmpty()) {
      System.out.println(emptyMessage);
      return null;
    }
    printRecordList(title, items);
    int choice = readInt("Select an option (0 to cancel)");
    if (choice == 0) {
      return null;
    }
    if (choice < 1 || choice > items.size()) {
      throw new ValidationException("Selection out of range.");
    }
    return items.get(choice - 1);
  }

  private static boolean hasDuplicateId(List<? extends Identifiable> items, String id) {
    for (Identifiable item : items) {
      if (item.getId() != null && item.getId().equalsIgnoreCase(id)) {
        return true;
      }
    }
    return false;
  }

  private static Department findDepartmentById(String id) {
    for (Department d : DEPARTMENTS) {
      if (d.getDepartmentId().equalsIgnoreCase(id)) {
        return d;
      }
    }
    return null;
  }

  private static Student findStudentById(String id) {
    for (Student s : STUDENTS) {
      if (s.getPersonId().equalsIgnoreCase(id) || s.getStudentNumber().equalsIgnoreCase(id)) {
        return s;
      }
    }
    return null;
  }

  private static Teacher findTeacherById(String id) {
    for (Teacher t : TEACHERS) {
      if (t.getPersonId().equalsIgnoreCase(id) || t.getEmployeeNumber().equalsIgnoreCase(id)) {
        return t;
      }
    }
    return null;
  }

  private static Course findCourseById(String id) {
    for (Course c : COURSES) {
      if (c.getCourseId().equalsIgnoreCase(id) || c.getCourseCode().equalsIgnoreCase(id)) {
        return c;
      }
    }
    return null;
  }

  private static boolean personExists(String personId) {
    return findStudentById(personId) != null || findTeacherById(personId) != null || findAdministratorById(personId) != null;
  }

  private static Administrator findAdministratorById(String id) {
    for (Administrator a : ADMINISTRATORS) {
      if (a.getPersonId().equalsIgnoreCase(id) || a.getStaffNumber().equalsIgnoreCase(id)) {
        return a;
      }
    }
    return null;
  }

  private static void registerDepartment() throws ValidationException, DuplicateRecordException {
    String departmentId = readNonBlank("Department ID");
    if (hasDuplicateId(DEPARTMENTS, departmentId)) {
      throw new DuplicateRecordException("Department ID already exists: " + departmentId);
    }
    String departmentName = readNonBlank("Department name");
    String departmentCode = readNonBlank("Department code");
    String officeLocation = readNonBlank("Office location");
    String contactEmail = readNonBlank("Contact email");
    Department department = new Department(departmentId, departmentName, departmentCode, officeLocation, contactEmail, null);
    department.validate();
    DEPARTMENTS.add(department);
    System.out.println("Department registered successfully: " + department.getRegistrationSummary());
  }

  private static void registerStudent() throws ValidationException, DuplicateRecordException, PrerequisiteMissingException {
    if (DEPARTMENTS.isEmpty()) {
      System.out.println("No department exists. Register a department first.");
      if (readYesNo("Register a department now")) {
        registerDepartment();
      } else {
        return;
      }
    }

    Department selectedDepartment = selectFromList("Select department", DEPARTMENTS, "No departments exist.");
    if (selectedDepartment == null) {
      System.out.println("Student registration cancelled.");
      return;
    }

    String personId = readNonBlank("Person ID");
    if (personExists(personId)) {
      throw new DuplicateRecordException("Person ID already exists: " + personId);
    }
    String firstName = readNonBlank("First name");
    String lastName = readNonBlank("Last name");
    String email = readNonBlank("Email");
    String phone = readNonBlank("Phone number");
    LocalDate dob = readDate("Date of birth");
    String memberNumber = readNonBlank("Member number");
    LocalDate joinDate = readDate("Join date");
    String academicStatus = readNonBlank("Academic status");
    String institutionalEmail = readNonBlank("Institutional email");
    String advisorName = readNonBlank("Advisor name");
    String studentNumber = readNonBlank("Student number");
    String programName = readNonBlank("Program name");
    LocalDate admissionDate = readDate("Admission date");
    int studyYear = readInt("Study year");
    String guardianPhone = readNonBlank("Guardian phone");
    boolean scholarshipStatus = readYesNo("Scholarship status");

    Student student = new Student(personId, firstName, lastName, email, phone, dob, memberNumber, selectedDepartment,
        joinDate, academicStatus, institutionalEmail, advisorName, studentNumber, programName, admissionDate,
        studyYear, guardianPhone, scholarshipStatus);
    student.validate();
    STUDENTS.add(student);
    System.out.println("Student registered successfully: " + student.getRegistrationSummary());
  }

  private static void registerTeacher() throws ValidationException, DuplicateRecordException {
    if (DEPARTMENTS.isEmpty()) {
      System.out.println("No department exists. Register a department first.");
      if (readYesNo("Register a department now")) {
        registerDepartment();
      } else {
        return;
      }
    }

    Department selectedDepartment = selectFromList("Select department", DEPARTMENTS, "No departments exist.");
    if (selectedDepartment == null) {
      System.out.println("Teacher registration cancelled.");
      return;
    }

    String personId = readNonBlank("Person ID");
    if (personExists(personId)) {
      throw new DuplicateRecordException("Person ID already exists: " + personId);
    }
    String firstName = readNonBlank("First name");
    String lastName = readNonBlank("Last name");
    String email = readNonBlank("Email");
    String phone = readNonBlank("Phone number");
    LocalDate dob = readDate("Date of birth");
    String memberNumber = readNonBlank("Member number");
    LocalDate joinDate = readDate("Join date");
    String academicStatus = readNonBlank("Academic status");
    String institutionalEmail = readNonBlank("Institutional email");
    String advisorName = readNonBlank("Advisor name");
    String employeeNumber = readNonBlank("Employee number");
    String specialization = readNonBlank("Specialization");
    LocalDate hireDate = readDate("Hire date");
    BigDecimal salary = readBigDecimal("Salary");
    String officeNumber = readNonBlank("Office number");
    String employmentType = readNonBlank("Employment type");

    Teacher teacher = new Teacher(personId, firstName, lastName, email, phone, dob, memberNumber, selectedDepartment,
        joinDate, academicStatus, institutionalEmail, advisorName, employeeNumber, specialization, hireDate, salary,
        officeNumber, employmentType);
    teacher.validate();
    TEACHERS.add(teacher);
    System.out.println("Teacher registered successfully: " + teacher.getRegistrationSummary());
  }

  private static void registerAdministrator() throws ValidationException, DuplicateRecordException {
    String personId = readNonBlank("Person ID");
    if (personExists(personId)) {
      throw new DuplicateRecordException("Person ID already exists: " + personId);
    }
    String firstName = readNonBlank("First name");
    String lastName = readNonBlank("Last name");
    String email = readNonBlank("Email");
    String phone = readNonBlank("Phone number");
    LocalDate dob = readDate("Date of birth");
    String staffNumber = readNonBlank("Staff number");
    String jobTitle = readNonBlank("Job title");
    String office = readNonBlank("Assigned office");
    LocalDate employmentDate = readDate("Employment date");
    int accessLevel = readInt("Access level");
    boolean active = readYesNo("Active");

    Administrator administrator = new Administrator(personId, firstName, lastName, email, phone, dob, staffNumber,
        jobTitle, office, employmentDate, accessLevel, active);
    administrator.validate();
    ADMINISTRATORS.add(administrator);
    System.out.println("Administrator registered successfully: " + administrator.getRegistrationSummary());
  }

  private static void registerCourse() throws ValidationException, PrerequisiteMissingException, DuplicateRecordException {
    if (DEPARTMENTS.isEmpty()) {
      throw new PrerequisiteMissingException("No department exists. Register a department before creating a course.");
    }
    if (TEACHERS.isEmpty()) {
      throw new PrerequisiteMissingException("No teacher exists. Register a teacher before creating a course.");
    }

    Department department = selectFromList("Select department", DEPARTMENTS, "No departments exist.");
    if (department == null) {
      return;
    }
    Teacher teacher = selectFromList("Select teacher", TEACHERS, "No teachers exist.");
    if (teacher == null) {
      return;
    }

    String courseId = readNonBlank("Course ID");
    if (hasDuplicateId(COURSES, courseId)) {
      throw new DuplicateRecordException("Course ID already exists: " + courseId);
    }
    String courseCode = readNonBlank("Course code");
    String courseTitle = readNonBlank("Course title");
    int creditHours = readInt("Credit hours");

    Course course = new Course(courseId, courseCode, courseTitle, creditHours, department, teacher);
    course.validate();
    COURSES.add(course);
    System.out.println("Course registered successfully: " + course.getRegistrationSummary());
  }

  private static void registerClassroom() throws ValidationException, DuplicateRecordException {
    String classroomId = readNonBlank("Classroom ID");
    if (hasDuplicateId(CLASSROOMS, classroomId)) {
      throw new DuplicateRecordException("Classroom ID already exists: " + classroomId);
    }
    String buildingName = readNonBlank("Building name");
    String roomNumber = readNonBlank("Room number");
    int capacity = readInt("Capacity");
    int floorNumber = readInt("Floor number");
    boolean available = readYesNo("Available now");

    Classroom classroom = new Classroom(classroomId, buildingName, roomNumber, capacity, floorNumber, available);
    classroom.validate();
    CLASSROOMS.add(classroom);
    System.out.println("Classroom registered successfully: " + classroom.getRegistrationSummary());
  }

  private static void enrollStudent() throws ValidationException, DuplicateRecordException, PrerequisiteMissingException {
    if (STUDENTS.isEmpty()) {
      throw new PrerequisiteMissingException("No student exists. Register a student before enrolling.");
    }
    if (COURSES.isEmpty()) {
      throw new PrerequisiteMissingException("No course exists. Register a course before enrolling a student.");
    }

    Student student = selectFromList("Select student", STUDENTS, "No students exist.");
    if (student == null) {
      return;
    }
    Course course = selectFromList("Select course", COURSES, "No courses exist.");
    if (course == null) {
      return;
    }

    String enrollmentId = readNonBlank("Enrollment ID");
    LocalDate enrollmentDate = readDate("Enrollment date");
    String semester = readNonBlank("Semester");
    String status = "ACTIVE";

    for (Enrollment enrollment : ENROLLMENTS) {
      if (enrollment.getStudent().getPersonId().equalsIgnoreCase(student.getPersonId())
          && enrollment.getCourse().getCourseId().equalsIgnoreCase(course.getCourseId())
          && enrollment.getSemester().equalsIgnoreCase(semester)
          && enrollment.isActive()) {
        throw new DuplicateRecordException("Active enrollment for same student, course and semester already exists.");
      }
    }

    Enrollment enrollment = new Enrollment(enrollmentId, student, course, enrollmentDate, semester, status);
    enrollment.validate();
    ENROLLMENTS.add(enrollment);
    System.out.println("Enrollment created successfully: " + enrollment.getRegistrationSummary());
  }

  private static void recordAssessment() throws ValidationException, DuplicateRecordException, PrerequisiteMissingException {
    if (ENROLLMENTS.isEmpty()) {
      throw new PrerequisiteMissingException("No enrollment exists. Enroll a student before recording an assessment.");
    }
    Enrollment selectedEnrollment = selectFromList("Select enrollment", ENROLLMENTS, "No enrollments exist.");
    if (selectedEnrollment == null) {
      return;
    }
    if (!selectedEnrollment.isActive()) {
      throw new ValidationException("Only active enrollments can be assessed.");
    }

    String assessmentId = readNonBlank("Assessment ID");
    if (hasDuplicateId(ASSESSMENTS, assessmentId)) {
      throw new DuplicateRecordException("Assessment ID already exists: " + assessmentId);
    }
    String assessmentType = readNonBlank("Assessment type");
    double maximumScore = readInt("Maximum score");
    double obtainedScore = readInt("Obtained score");
    LocalDate assessmentDate = readDate("Assessment date");

    Assessment assessment = new Assessment(assessmentId, selectedEnrollment, assessmentType, maximumScore, obtainedScore,
        assessmentDate);
    assessment.validate();
    ASSESSMENTS.add(assessment);
    System.out.println("Assessment recorded successfully: " + assessment.getRegistrationSummary());
  }

  private static void recordFeePayment() throws ValidationException, DuplicateRecordException, PrerequisiteMissingException {
    if (STUDENTS.isEmpty()) {
      throw new PrerequisiteMissingException("No student exists. Register a student before recording a payment.");
    }

    Student student = selectFromList("Select student", STUDENTS, "No students exist.");
    if (student == null) {
      return;
    }

    String paymentId = readNonBlank("Payment ID");
    String receiptNumber = readNonBlank("Receipt number");
    for (FeePayment payment : PAYMENTS) {
      if (payment.getReceiptNumber().equalsIgnoreCase(receiptNumber)) {
        throw new DuplicateRecordException("Receipt number already exists: " + receiptNumber);
      }
    }
    BigDecimal amount = readBigDecimal("Amount");
    LocalDate paymentDate = readDate("Payment date");
    String paymentMethod = readNonBlank("Payment method");

    FeePayment feePayment = new FeePayment(paymentId, student, amount, paymentDate, paymentMethod, receiptNumber);
    feePayment.validate();
    PAYMENTS.add(feePayment);
    System.out.println("Fee payment recorded successfully: " + feePayment.getRegistrationSummary());
  }

  private static void registerLibraryBook() throws ValidationException, DuplicateRecordException {
    String bookId = readNonBlank("Book ID");
    if (hasDuplicateId(BOOKS, bookId)) {
      throw new DuplicateRecordException("Book ID already exists: " + bookId);
    }
    String isbn = readNonBlank("ISBN");
    String title = readNonBlank("Title");
    String author = readNonBlank("Author");
    int totalCopies = readInt("Total copies");
    int availableCopies = readInt("Available copies");

    LibraryBook book = new LibraryBook(bookId, isbn, title, author, totalCopies, availableCopies);
    book.validate();
    BOOKS.add(book);
    System.out.println("Library book registered successfully: " + book.getRegistrationSummary());
  }

  private static void borrowLibraryBook() throws ValidationException, PrerequisiteMissingException, BookUnavailableException {
    if (STUDENTS.isEmpty()) {
      throw new PrerequisiteMissingException("No student exists. Register a student before borrowing a book.");
    }
    if (BOOKS.isEmpty()) {
      throw new PrerequisiteMissingException("No library book exists. Register a library book first.");
    }

    Student student = selectFromList("Select student", STUDENTS, "No students exist.");
    if (student == null) {
      return;
    }
    List<LibraryBook> availableBooks = new ArrayList<>();
    for (LibraryBook book : BOOKS) {
      if (book.getAvailableCopies() > 0) {
        availableBooks.add(book);
      }
    }
    LibraryBook book = selectFromList("Select available book", availableBooks, "No available book exists.");
    if (book == null) {
      return;
    }
    if (book.getAvailableCopies() <= 0) {
      throw new BookUnavailableException("Selected book is unavailable. No copies remain.");
    }

    String loanId = readNonBlank("Loan ID");
    LocalDate borrowedDate = readDate("Borrowed date");
    LocalDate dueDate = readDate("Due date");
    BookLoan loan = new BookLoan(loanId, student, book, borrowedDate, dueDate, null);
    loan.validate();
    book.setAvailableCopies(book.getAvailableCopies() - 1);
    LOANS.add(loan);
    System.out.println("Book borrowed successfully: " + loan.getRegistrationSummary());
  }

  private static void returnLibraryBook() throws ValidationException, RecordNotFoundException {
    if (LOANS.isEmpty()) {
      throw new RecordNotFoundException("No active loan exists.");
    }
    BookLoan selectedLoan = null;
    for (BookLoan loan : LOANS) {
      if (loan.getReturnedDate() == null) {
        selectedLoan = loan;
        break;
      }
    }
    if (selectedLoan == null) {
      throw new RecordNotFoundException("No active loan found.");
    }

    LocalDate returnedDate = readDate("Return date");
    selectedLoan.setReturnedDate(returnedDate);
    LibraryBook book = selectedLoan.getBook();
    book.setAvailableCopies(book.getAvailableCopies() + 1);
    System.out.println("Book returned successfully. Available copies updated to " + book.getAvailableCopies());
  }

  private static void viewAllRecords() {
    printRecordList("Departments", DEPARTMENTS);
    printRecordList("Students", STUDENTS);
    printRecordList("Teachers", TEACHERS);
    printRecordList("Administrators", ADMINISTRATORS);
    printRecordList("Courses", COURSES);
    printRecordList("Classrooms", CLASSROOMS);
    printRecordList("Enrollments", ENROLLMENTS);
    printRecordList("Assessments", ASSESSMENTS);
    printRecordList("Fee Payments", PAYMENTS);
    printRecordList("Library Books", BOOKS);
    printRecordList("Book Loans", LOANS);
  }

  private static void searchById() throws RecordNotFoundException, ValidationException {
    String id = readNonBlank("Search ID");
    boolean found = false;
    for (Department d : DEPARTMENTS) {
      if (d.getId().equalsIgnoreCase(id)) {
        System.out.println(d.getRegistrationSummary());
        found = true;
      }
    }
    for (Student s : STUDENTS) {
      if (s.getId().equalsIgnoreCase(id) || s.getStudentNumber().equalsIgnoreCase(id)) {
        System.out.println(s.getRegistrationSummary());
        found = true;
      }
    }
    for (Teacher t : TEACHERS) {
      if (t.getId().equalsIgnoreCase(id) || t.getEmployeeNumber().equalsIgnoreCase(id)) {
        System.out.println(t.getRegistrationSummary());
        found = true;
      }
    }
    for (Administrator a : ADMINISTRATORS) {
      if (a.getId().equalsIgnoreCase(id) || a.getStaffNumber().equalsIgnoreCase(id)) {
        System.out.println(a.getRegistrationSummary());
        found = true;
      }
    }
    for (Course c : COURSES) {
      if (c.getId().equalsIgnoreCase(id) || c.getCourseCode().equalsIgnoreCase(id)) {
        System.out.println(c.getRegistrationSummary());
        found = true;
      }
    }
    if (!found) {
      throw new RecordNotFoundException("No record was found for ID: " + id);
    }
  }

  private static void updateRecord() throws ValidationException, RecordNotFoundException {
    System.out.println("1. Department\n2. Student\n3. Teacher\n4. Administrator\n5. Course\n6. Classroom\n0. Cancel");
    int option = readInt("Choose record type");
    switch (option) {
      case 1 -> {
        if (DEPARTMENTS.isEmpty()) {
          throw new RecordNotFoundException("No departments available to update.");
        }
        Department dept = selectFromList("Select department", DEPARTMENTS, "No departments available.");
        if (dept == null) {
          return;
        }
        dept.setDepartmentName(readNonBlank("New department name"));
        dept.setOfficeLocation(readNonBlank("New office location"));
        dept.validate();
        System.out.println("Department updated: " + dept.getRegistrationSummary());
      }
      case 2 -> {
        if (STUDENTS.isEmpty()) {
          throw new RecordNotFoundException("No students available to update.");
        }
        Student student = selectFromList("Select student", STUDENTS, "No students available.");
        if (student == null) {
          return;
        }
        student.setFirstName(readNonBlank("New first name"));
        student.setProgramName(readNonBlank("New program name"));
        student.validate();
        System.out.println("Student updated: " + student.getRegistrationSummary());
      }
      case 3 -> {
        if (TEACHERS.isEmpty()) {
          throw new RecordNotFoundException("No teachers available to update.");
        }
        Teacher teacher = selectFromList("Select teacher", TEACHERS, "No teachers available.");
        if (teacher == null) {
          return;
        }
        teacher.setSpecialization(readNonBlank("New specialization"));
        teacher.setOfficeNumber(readNonBlank("New office number"));
        teacher.validate();
        System.out.println("Teacher updated: " + teacher.getRegistrationSummary());
      }
      case 4 -> {
        if (ADMINISTRATORS.isEmpty()) {
          throw new RecordNotFoundException("No administrators available to update.");
        }
        Administrator admin = selectFromList("Select administrator", ADMINISTRATORS, "No administrators available.");
        if (admin == null) {
          return;
        }
        admin.setJobTitle(readNonBlank("New job title"));
        admin.setAssignedOffice(readNonBlank("New assigned office"));
        admin.validate();
        System.out.println("Administrator updated: " + admin.getRegistrationSummary());
      }
      case 5 -> {
        if (COURSES.isEmpty()) {
          throw new RecordNotFoundException("No courses available to update.");
        }
        Course course = selectFromList("Select course", COURSES, "No courses available.");
        if (course == null) {
          return;
        }
        course.setCourseTitle(readNonBlank("New course title"));
        course.setCreditHours(readInt("New credit hours"));
        course.validate();
        System.out.println("Course updated: " + course.getRegistrationSummary());
      }
      case 6 -> {
        if (CLASSROOMS.isEmpty()) {
          throw new RecordNotFoundException("No classrooms available to update.");
        }
        Classroom classroom = selectFromList("Select classroom", CLASSROOMS, "No classrooms available.");
        if (classroom == null) {
          return;
        }
        classroom.setRoomNumber(readNonBlank("New room number"));
        classroom.setCapacity(readInt("New capacity"));
        classroom.validate();
        System.out.println("Classroom updated: " + classroom.getRegistrationSummary());
      }
      case 0 -> System.out.println("Update cancelled.");
      default -> System.out.println("Invalid selection.");
    }
  }

  private static void showStudentAcademicSummary() throws RecordNotFoundException, ValidationException {
    if (STUDENTS.isEmpty()) {
      throw new RecordNotFoundException("No students registered.");
    }
    Student student = selectFromList("Select student", STUDENTS, "No students registered.");
    if (student == null) {
      return;
    }
    System.out.println("Academic summary for " + student.getDisplayName());
    for (Enrollment enrollment : ENROLLMENTS) {
      if (enrollment.getStudent().getPersonId().equalsIgnoreCase(student.getPersonId())) {
        System.out.println("- " + enrollment.getRegistrationSummary());
      }
    }
    for (Assessment assessment : ASSESSMENTS) {
      if (assessment.getEnrollment().getStudent().getPersonId().equalsIgnoreCase(student.getPersonId())) {
        System.out.println("- " + assessment.getRegistrationSummary());
      } 
    }
  }

  private static void showPaymentHistory() throws RecordNotFoundException, ValidationException {
    if (STUDENTS.isEmpty()) {
      throw new RecordNotFoundException("No students registered.");
    }
    Student student = selectFromList("Select student", STUDENTS, "No students registered.");
    if (student == null) {
      return;
    }
    boolean found = false;
    for (FeePayment payment : PAYMENTS) {
      if (payment.getStudent().getPersonId().equalsIgnoreCase(student.getPersonId())) {
        System.out.println(payment.getRegistrationSummary());
        found = true;
      }
    }
    if (!found) {
      System.out.println("No payment history found for student: " + student.getDisplayName());
    }
  }

  private static void showAvailableBooks() {
    List<LibraryBook> available = new ArrayList<>();
    for (LibraryBook book : BOOKS) {
      if (book.getAvailableCopies() > 0) {
        available.add(book);
      }
    }
    printRecordList("Available Books", available);
  }

  private static void demonstratePolymorphism() {
    List<Person> people = new ArrayList<>();
    // preson:[{student(0), teacher(0), administrator(0)}]
    if (!STUDENTS.isEmpty()) {
      people.add(STUDENTS.get(0));
    }
    if (!TEACHERS.isEmpty()) {
      people.add(TEACHERS.get(0));
    }
    if (!ADMINISTRATORS.isEmpty()) {
      people.add(ADMINISTRATORS.get(0));
    }
    if (people.isEmpty()) {
      System.out.println("No person records exist yet. Add at least one student, teacher or administrator.");
      return;
    }
    System.out.println("Polymorphism demo using Person references:");
    for (Person person : people) {
      System.out.println(person.getRoleDescription() + " -> " + person.getDisplayName());
    }

    List<Identifiable> identifiableRecords = new ArrayList<>();
    identifiableRecords.addAll(DEPARTMENTS);
    identifiableRecords.addAll(COURSES);
    identifiableRecords.addAll(CLASSROOMS);
    identifiableRecords.addAll(BOOKS);
    System.out.println("\nInterface polymorphism demo:");
    for (Identifiable item : identifiableRecords) {
      if (item instanceof Registrable registrable) {
        System.out.println(registrable.getRegistrationSummary());
      } else {
        System.out.println(item.getDisplayName());
      }
    }
  }
}
