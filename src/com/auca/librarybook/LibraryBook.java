package com.auca.librarybook;

import com.auca.exceptions.ValidationException;
import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;

public class LibraryBook implements Identifiable, Registrable {
  private String bookId;
  private String isbn;
  private String title;
  private String author;
  private int totalCopies;
  private int availableCopies;

  public LibraryBook(String bookId, String isbn, String title, String author, int totalCopies, int availableCopies) {
    this.bookId = bookId;
    this.isbn = isbn;
    this.title = title;
    this.author = author;
    this.totalCopies = totalCopies;
    this.availableCopies = availableCopies;
  }

  public String getBookId() {
    return bookId;
  }

  public void setBookId(String bookId) {
    this.bookId = bookId;
  }

  public String getIsbn() {
    return isbn;
  }

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public int getTotalCopies() {
    return totalCopies;
  }

  public void setTotalCopies(int totalCopies) {
    this.totalCopies = totalCopies;
  }

  public int getAvailableCopies() {
    return availableCopies;
  }

  public void setAvailableCopies(int availableCopies) {
    this.availableCopies = availableCopies;
  }

  @Override
  public String getDisplayName() {
    return title;
  }

  @Override
  public String getId() {
    return bookId;
  }

  @Override
  public boolean isActive() {
    return bookId != null && !bookId.isBlank() && totalCopies >= 0;
  }

  @Override
  public String getRegistrationSummary() {
    return title + " by " + author + " | Available: " + availableCopies + "/" + totalCopies;
  }

  @Override
  public void printDetails() {
    System.out.println(this);
  }

  @Override
  public void validate() throws ValidationException {
    if (bookId == null || bookId.isBlank()) {
      throw new ValidationException("Book ID is required.");
    }
    if (isbn == null || isbn.isBlank()) {
      throw new ValidationException("ISBN is required.");
    }
    if (title == null || title.trim().length() < MIN_NAME_LENGTH || title.trim().length() > MAX_NAME_LENGTH) {
      throw new ValidationException("Book title must be between " + MIN_NAME_LENGTH + " and " + MAX_NAME_LENGTH + " characters.");
    }
    if (author == null || author.trim().length() < MIN_NAME_LENGTH || author.trim().length() > MAX_NAME_LENGTH) {
      throw new ValidationException("Author name must be between " + MIN_NAME_LENGTH + " and " + MAX_NAME_LENGTH + " characters.");
    }
    if (totalCopies < 0) {
      throw new ValidationException("Total copies cannot be negative.");
    }
    if (availableCopies < 0 || availableCopies > totalCopies) {
      throw new ValidationException("Available copies must be between 0 and total copies.");
    }
  }

  @Override
  public String toString() {
    return "LibraryBook{" +
        "bookId='" + bookId + '\'' +
        ", isbn='" + isbn + '\'' +
        ", title='" + title + '\'' +
        ", author='" + author + '\'' +
        ", totalCopies=" + totalCopies +
        ", availableCopies=" + availableCopies +
        '}';
  }
}
