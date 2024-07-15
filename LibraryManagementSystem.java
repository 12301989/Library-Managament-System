import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LibraryManagementSystem {
private static Map<String, Book> books = new HashMap<>();
private static Map<String, Member> members = new HashMap<>();
private static Map<String, Loan> loans = new HashMap<>();
private static Scanner scanner = new Scanner(System.in);

public static void main(String[] args) {
while (true) {
displayMenu();
int choice = getChoice();
performAction(choice);
}
}

private static void displayMenu() {
System.out.println("\nLibrary Management System");
System.out.println("1. Add Book");
System.out.println("2. Add Member");
System.out.println("3. Borrow Book");
System.out.println("4. Return Book");
System.out.println("5. View Books");
System.out.println("6. View Members");
System.out.println("7. View Loans");
System.out.println("8. Search Books");
System.out.println("9. Exit");
}

private static int getChoice() {
System.out.print("Enter your choice: ");
return scanner.nextInt();
}

private static void performAction(int choice) {
scanner.nextLine(); // Consume newline character
switch (choice) {
case 1:
addBook();
break;
case 2:
addMember();
break;
case 3:
borrowBook();
break;
case 4:
returnBook();
break;
case 5:
viewBooks();
break;
case 6:
viewMembers();
break;
case 7:
viewLoans();
break;
case 8:
searchBooks();
break;
case 9:
System.out.println("Exiting Library Management System...");
System.exit(0);
default:
System.out.println("Invalid choice. Please try again.");
}
}

private static void addBook() {
System.out.print("Enter the book title: ");
String title = scanner.nextLine();
System.out.print("Enter the author: ");
String author = scanner.nextLine();
System.out.print("Enter the ISBN: ");
String isbn = scanner.nextLine();
Book book = new Book(title, author, isbn);
books.put(isbn, book);
System.out.println("Book added successfully.");
}

private static void addMember() {
System.out.print("Enter the member name: ");
String name = scanner.nextLine();
System.out.print("Enter the member ID: ");
String memberId = scanner.nextLine();
Member member = new Member(name, memberId);
members.put(memberId, member);
System.out.println("Member added successfully.");
}

private static void borrowBook() {
System.out.print("Enter the member ID: ");
String memberId = scanner.nextLine();
if (members.containsKey(memberId)) {
System.out.print("Enter the book ISBN: ");
String isbn = scanner.nextLine();
if (books.containsKey(isbn)) {
Book book = books.get(isbn);
if (!book.isBorrowed()) {
Member member = members.get(memberId);
Loan loan = new Loan(member, book);
loans.put(loan.getId(), loan);
member.borrowBook(book);
book.setBorrowed(true);
System.out.println("Book borrowed successfully.");
} else {
System.out.println("Book is currently borrowed.");
}
} else {
System.out.println("Book not found in the library.");
}
} else {
System.out.println("Member not found in the library.");
}
}

private static void returnBook() {
System.out.print("Enter the loan ID: ");
String loanId = scanner.nextLine();
if (loans.containsKey(loanId)) {
Loan loan = loans.get(loanId);
loan.getBook().setBorrowed(false);
loan.getMember().returnBook(loan.getBook());
loans.remove(loanId);
System.out.println("Book returned successfully.");
} else {
System.out.println("Loan not found in the library.");
}
}

private static void viewBooks() {
System.out.println("\nBooks in the Library:");
for (Book book : books.values()) {
System.out.println(book);
}
}

private static void viewMembers() {
System.out.println("\nMembers in the Library:");
for (Member member : members.values()) {
System.out.println(member);
}
}

private static void viewLoans() {
System.out.println("\nActive Loans:");
for (Loan loan : loans.values()) {
System.out.println(loan);
}
}

private static void searchBooks() {
System.out.print("Enter the search query: ");
String query = scanner.nextLine();
System.out.println("\nSearch Results:");
for (Book book : books.values()) {
if (book.getTitle().contains(query) || book.getAuthor().contains(query)) {
System.out.println(book);
}
}
}

private static class Book {
private String title;
private String author;
private String isbn;
private boolean borrowed;

Book(String title, String author, String isbn) {
  this.title = title;
  this.author = author;
  this.isbn = isbn;
  this.borrowed = false;
}

public String getTitle() {
  return title;
}

public String getAuthor() {
  return author;
}

public boolean isBorrowed() {
  return borrowed;
}

public void setBorrowed(boolean borrowed) {
  this.borrowed = borrowed;
}

@Override
public String toString() {
  return "Title: " + title + ", Author: " + author + ", ISBN: " + isbn + ", Borrowed: " + borrowed;
}
}

private static class Member {
private String name;
private String memberId;
private Map<String, Book> borrowedBooks;

Member(String name, String memberId) {
  this.name = name;
  this.memberId = memberId;
  this.borrowedBooks = new HashMap<>();
}

public void borrowBook(Book book) {
  borrowedBooks.put(book.isbn, book);
}

public void returnBook(Book book) {
  borrowedBooks.remove(book.isbn);
}

@Override
public String toString() {
  return "Name: " + name + ", Member ID: " + memberId + ", Borrowed Books: " + borrowedBooks.size();
}
}

private static class Loan {
private static int loanIdCounter = 1;
private String id;
private Member member;
private Book book;

Loan(Member member, Book book) {
  this.id = "L" + loanIdCounter++;
  this.member = member;
  this.book = book;
}

public String getId() {
  return id;
}

public Member getMember() {
  return member;
}

public Book getBook() {
  return book;
}

@Override
public String toString() {
  return "Loan ID: " + id + ", Member: " + member.name + ", Book: " + book.title;
}
}
}