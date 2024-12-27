package Library;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import static Library.Book.*;
import static Library.Member.*;

public class LibrarySystem {
    private static Scanner scanner = new Scanner(System.in);
    private static Library library = new Library();

    public static void main(String[] args) {
        while (true) {
            try {
                displayMenu();
                int choice = scanner.nextInt();
                scanner.nextLine();
                handleMenuChoice(choice);
            } catch (SQLException e) {
                System.out.println("Database error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                scanner.nextLine();
            }
        }
    }

    private static void displayMenu() {
        System.out.println("\n***** Library Management System *****");
        System.out.println("1. Add Book");
        System.out.println("2. Add Member");
        System.out.println("3. Borrow Book");
        System.out.println("4. Return Book");
        System.out.println("5. Display All Books");
        System.out.println("6. Display All Members");
        System.out.println("7. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void handleMenuChoice(int choice) throws SQLException {
        switch (choice) {
            case 1:
                addBookOption();
                break;
            case 2:
                addMemberOption();
                break;
            case 3:
                borrowBookOption();
                break;
            case 4:
                returnBookOption();
                break;
            case 5:
                displayAllBooksOption();
                break;
            case 6:
                displayAllMembersOption();
                break;
            case 7:
                System.out.println("Exiting the system. Goodbye!");
                System.exit(0);
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void addBookOption() throws SQLException {
        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine();
        System.out.print("Enter Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter Author: ");
        String author = scanner.nextLine();
        addBook(new Book(isbn, title, author));
        System.out.println("Book added successfully!");
    }

    private static void addMemberOption() throws SQLException {
        System.out.print("Enter Member ID: ");
        String memberId = scanner.nextLine();
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        addMember(new Member(memberId, name));
        System.out.println("Member added successfully!");
    }

    private static void borrowBookOption() throws SQLException {
        System.out.print("Enter Member ID: ");
        String memberId = scanner.nextLine();
        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine();
        if (library.borrowBook(memberId, isbn)) {
            System.out.println("Book borrowed successfully!");
        } else {
            System.out.println("Book is not available or does not exist.");
        }
    }

    private static void returnBookOption() throws SQLException {
        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine();
        if (library.returnBook(isbn)) {
            System.out.println("Book returned successfully!");
        } else {
            System.out.println("Failed to return the book. It might not have been borrowed.");
        }
    }

    private static void displayAllBooksOption() throws SQLException {
        List<Book> books = getAllBooks();
        System.out.println("\n****** List of Books *******");
        for (Book book : books) {
            System.out.printf("ISBN: %s, Title: %s, Author: %s, Available: %s\n",
                    book.getIsbn(), book.getTitle(), book.getAuthor(), book.isAvailable());
        }
    }

    private static void displayAllMembersOption() throws SQLException {
        List<Member> members = getAllMembers();
        System.out.println("\n***** List of Members *****");
        for (Member member : members) {
            System.out.printf("Member ID: %s, Name: %s\n", member.getMemberId(), member.getName());
        }
    }
}
