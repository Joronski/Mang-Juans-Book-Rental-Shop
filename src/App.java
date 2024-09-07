/*

CCS107 Laboratory Activity 1 - Mang Juan's Book Rental Shop

Created by the team consisting of the following members:

Bea Arabelle Atienza
Blessreal Cantalejo
Mark Vincent Carag
Alexa Carlos
Joaquin Aaron Recio
Luiz Gabriel Rosales

*/
import java.util.Scanner;

public class App {

    // arrays to store book details and statuses
	// limited number of books is 10
    String[] bookIDs = new String[10];
    String[] titles = new String[10];
    String[] authors = new String[10];
    double[] rentalPrices = new double[10];
    String[] statuses = new String[10];
    int bookCount = 0;  // counter to track the number of books
    
    // arrays to store customer names and transaction details
    // we only assume to have 20 customers and 20 transactions
    String[] customerRented = new String[20];
    String[] transactionBookIDs = new String[20];
    String[] transactionTitles = new String[20];
    String[] transactionStatuses = new String[20];
    String[] transactionCustomers = new String[20];
    static int transactionCount = 0;  // counter to track the number of transactions

    public static void main(String[] args) {
    	// create an instance of the Bookstore class
        App store = new App();
        Scanner scan = new Scanner(System.in); // scanner object for user input
       
        store.addDummyBooks(); // add some dummy books to the bookstore
        store.displayBooks(); // display the list of books

        // main menu loop
        while (true) {
        	// display menu options to the user
            System.out.println("Welcome to Mang Juan's Bookstore\n"
                                + "What can I do for you?\n"
                                + "\n[1] Add New Book\n"
                                + "[2] View Book Records\n" 
                                + "[3] Book Rental\n"
                                + "[4] Book Return\n"
                                + "[5] View Transaction History\n"
                                + "[6] Exit\n");
            System.out.print("Please select an option: ");
            int choice = scan.nextInt(); // get user's choice
            scan.nextLine(); // consume newline
            
            System.out.println();

            // switch case to handle user's choice
            switch (choice) {
                case 1:
                    store.handleAddBook(scan); // add a new book
                    break;
                case 2:
                    store.displayBooks(); // view book records
                    break;
                case 3:
                    store.handleRentBook(scan); // rent a book
                    break;
                case 4:
                    store.handleReturnBook(scan); // return a book
                    break;
                case 5:
                	if ( transactionCount  > 0) {
                        store.viewTransactionHistory(); // view transaction history
                    } else {
                        System.out.println("No transactions have been made yet. Please make a transaction first.\n");
                    }
                    break;
                case 6:
                    if (store.confirmAction(scan, "Are you sure you want to exit?")) {
                        System.out.println("Thank you for using the application... \nExiting Program...");
                        scan.close(); // close scanner
                        return; // exit the program
                    }
                    break;
                default:
                    System.out.println("Invalid option. Please try again."); // handle invalid choice
            }
        } 
    }
    
    // method to add dummy books to the bookstore
    public void addDummyBooks() {
        addBook("2023-001", "To Kill a Mockingbird", "Harper Lee", 120.00, "Available");
        addBook("2023-002", "1984", "George Orwell", 150.00, "Available");
        addBook("2023-003", "Pride and Prejudice", "Jane Austen", 110.00, "Available");
        addBook("2024-001", "The Great Gatsby", "F. Scott Fitzgerald", 120.00, "Available");
        addBook("2024-002", "The Lord of the Rings", "J.R.R. Tolkien", 130.00, "Available");
    }

    // method to add a new book to the bookstore
    public void addBook(String bookID, String title, String author, double rentalPrice, String status) {
        bookIDs[bookCount] = bookID; // store book ID
        titles[bookCount] = title; // store title
        authors[bookCount] = author; // store author
        rentalPrices[bookCount] = rentalPrice; // store rental price
        statuses[bookCount] = status; // store status
        bookCount++; // increment book count
    }

    // method to check if a book ID already exists
    public boolean checkBookID(String bookID) {
        for (int i = 0; i < bookCount; i++) {
            if (compareStrings(bookIDs[i], bookID)) {
                return true; // return true if book ID is found
            }
        }
        return false; // return false if book ID is not found
    }
    
    // return false if book ID is not found
    public int findBookIndex(String bookID) {
        for (int i = 0; i < bookCount; i++) {
            if (compareStrings(bookIDs[i], bookID)) {
                return i; // return the index if book ID is found
            }
        }
        return -1; // return -1 if book ID is not found
    }

    // Method to compare two strings character by character
    public boolean compareStrings(String s1, String s2) {
        if (s1.length() != s2.length()) {
            return false; // If lengths are different, strings can't be equal
        }
        
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                return false; // Characters at position i are different
            }
        }
        
        return true; // All characters match, so the strings are equal
    }
    
    public boolean compareStringsIgnoreCase(String s1, String s2) {
        if (s1.length() != s2.length()) {
            return false;  // If lengths are different, the strings can't be equal
        }

        for (int i = 0; i < s1.length(); i++) {
            char c1 = s1.charAt(i);
            char c2 = s2.charAt(i);

            // Convert both characters to lowercase (manually) and compare
            if (toLowerCase(c1) != toLowerCase(c2)) {
                return false;  // Characters at position i are different, ignoring case
            }
        }

        return true;  // All characters match (ignoring case)
    }

    // Helper method to manually convert a character to lowercase
    public char toLowerCase(char c) {
        if (c >= 'A' && c <= 'Z') {
            return (char) (c + 'a' - 'A');  // Convert uppercase to lowercase
        }
        return c;  // Return as is if it's already lowercase or not a letter
    }
    
    // method to display the list of books
    public void displayBooks() {
    	// variables to store the maximum width of each column
        int idWidth = 0;
        int titleWidth = 0;
        int authorWidth = 0;
        int priceWidth = 0;
        int statusWidth = 0;

        // calculate the width of each column based on the longest entry
        for (int i = 0; i < bookCount; i++) {
            if (bookIDs[i].length() > idWidth) {
                idWidth = bookIDs[i].length() + 4;
            }
            if (titles[i].length() > titleWidth) {
                titleWidth = titles[i].length() + 4;
            }
            if (authors[i].length() > authorWidth) {
                authorWidth = authors[i].length() + 4;
            }
            if (String.valueOf(rentalPrices[i]).length() > priceWidth) {
                priceWidth = String.valueOf(rentalPrices[i]).length() + 5;
            }
            if (statuses[i].length() > statusWidth) {
                statusWidth = statuses[i].length() + 4;
            }
        }

        int totalWidth = idWidth + titleWidth + authorWidth + priceWidth + statusWidth + 15; // total table width

        String title = "Mang Juan's Book Rental Shop"; // header title
        // create header border based on the calculated width
        String headerBorder = "+";
        for (int i = 0; i < totalWidth; i++) {
            headerBorder += "-";
        }
        headerBorder += "+";
        System.out.println(headerBorder); // top border
        System.out.printf("|%s|%n", centerText(title, totalWidth));

        System.out.println(headerBorder); // top Border
        String format = "| %-"+idWidth+"s | %-"+titleWidth+"s |  %-"+authorWidth+"s | %-"+priceWidth+"s | %-"+statusWidth+"s |%n";  // table format
        System.out.printf(format, "Book ID", "Title", "Author", "Price", "Status"); // display column headers
        System.out.println(headerBorder); // inside Border

        // display each book's details
        for (int i = 0; i < bookCount; i++) {
            System.out.printf(format, bookIDs[i], titles[i], authors[i], "PHP" + String.format("%.2f", rentalPrices[i]), statuses[i]);
        }

        System.out.println(headerBorder); // bottom Border
        System.out.println();
    }
    
    // method to center text within a specified width
    public String centerText(String text, int width) {
        int padding = (width - text.length()) / 2; // calculate padding
        String format = "%" + padding + "s%s%" + padding + "s"; // title centered by adding spaces to both sides
        return String.format(format, "", text, "");  // return centered text
    }

    // method to rent a book
    public void rentBook(String bookID, String customerName) {
        for (int i = 0; i < bookCount; i++) {
            if (compareStrings(bookIDs[i], bookID)) {
                // Book found, now check if it's available
                if (compareStrings(statuses[i], "Available")) {
                    statuses[i] = "Unavailable"; // update book status to unavailable
                    customerRented[i] = customerName; // store customer name

                    // store transaction details
                    transactionBookIDs[transactionCount] = bookID;
                    transactionTitles[transactionCount] = titles[i];
                    transactionStatuses[transactionCount] = "Rented";
                    transactionCustomers[transactionCount] = customerName;
                    transactionCount++;

                    System.out.println("Book rented successfully.\n"); // confirm successful rental
                    return;
                } else {
                    System.out.println("Book is already rented.\n"); // handle already rented book
                    return;
                }
            }
        }
        // If the loop completes without finding the book
        System.out.println("Book not found.\n"); // handle book not found
    }


    // method to return a book
    public void returnBook(String bookID, String customerName) {
        for (int i = 0; i < bookCount; i++) {
            if (compareStrings(bookIDs[i], bookID)) {
                // Book ID matched
                if (compareStrings(statuses[i], "Unavailable") && compareStringsIgnoreCase(customerRented[i], customerName)) {
                    // Book is unavailable and rented by the customer
                    statuses[i] = "Available"; // update book status to available
                    customerRented[i] = ""; // clear customer name

                    // store transaction details
                    transactionBookIDs[transactionCount] = bookID;
                    transactionTitles[transactionCount] = titles[i];
                    transactionStatuses[transactionCount] = "Returned";
                    transactionCustomers[transactionCount] = customerName;
                    transactionCount++;

                    System.out.println("Book returned successfully.\n"); // confirm successful return
                    return;
                } else {
                    System.out.println("Book was not rented by " + customerName + ".\n"); // handle mismatch in customer name
                    return;
                }
            }
        }
        // If the loop completes without finding the book
        System.out.println("Record not found.\n"); // handle book not found
    }


    // method to view transaction history
    public void viewTransactionHistory() {
        int idWidth = 0; // store width of book ID column
        int titleWidth = 0; // store width of book ID column
        int statusWidth = 0; // store width of book ID column
        int customerWidth = 0; // store width of customer name column

        for (int i = 0; i < transactionCount; i++) {
            if (transactionBookIDs[i].length() > idWidth) {
                idWidth = transactionBookIDs[i].length() + 2; // calculate book ID column width
            }
            if (transactionTitles[i].length() > titleWidth) {
                titleWidth = transactionTitles[i].length() + 2; // calculate title column width
            }
            if (transactionStatuses[i].length() > statusWidth) {
                statusWidth = transactionStatuses[i].length() + 2; // calculate status column width
            } 
            if (transactionCustomers[i].length() > customerWidth) {
                customerWidth = transactionCustomers[i].length() + 2 ; // calculate customer name column width
            }
        }

        int totalWidth = idWidth + titleWidth +  statusWidth + customerWidth + 11; // calculate total table width
        String title = "Transaction History"; // title of the table 
        //creates table borders
        String headerBorder = "+";
        for (int i = 0; i < totalWidth; i++) {
            headerBorder += "-";
        }
        headerBorder += "+";
        System.out.println(headerBorder); // top border
        System.out.printf("|%s|%n", centerTextTransaction(title, totalWidth)); // display centered title
        
        System.out.println(headerBorder); // display border below title
        String format = "| %-"+idWidth+"s | %-"+titleWidth+"s | %-"+statusWidth+"s | %-"+customerWidth+"s |%n"; // set format for each row
        System.out.printf(format, "Book ID", "Title", "Status", "Name"); // display column headers
        System.out.println(headerBorder); // display header border

        for (int i = 0; i < transactionCount; i++) {
            System.out.printf(format, transactionBookIDs[i], transactionTitles[i], transactionStatuses[i], transactionCustomers[i]); // display transaction details
        }
        System.out.println(headerBorder); // Top Border
        System.out.println();
    }

    // method to center text in transaction history
    public String centerTextTransaction(String text, int width) {
        int padding = (width - text.length()) / 2; // calculate padding on each side
        String format = "%" + padding + "s%s%" + (padding + (width - text.length()) % 2) + "s"; // set format for centered text
        return String.format(format, "", text, ""); // return centered text
    }
    
    // method to replace a book
    public void replaceBook(String oldBookID, String newBookID, String newTitle, String newAuthor, double newPrice) {
        for (int i = 0; i < bookCount; i++) {
            if (compareStrings(bookIDs[i], oldBookID)) {
                // Book ID matched
                if (compareStrings(statuses[i], "Available")) {
                    // Book is available for replacement
                    bookIDs[i] = newBookID; // update book ID
                    titles[i] = newTitle; // update title
                    authors[i] = newAuthor; // update author
                    rentalPrices[i] = newPrice; // update rental price
                    statuses[i] = "Available"; // set status to available
                    return; // Exit the method once the book is replaced
                } 
            }
        }
       
        System.out.println();
    }


    // method to handle adding a new book
    public void handleAddBook(Scanner scan) {
        if (confirmAction(scan, "Do you want to add a book?")) {
            if (bookCount >= 10) {
                System.out.println("Book list is full. Please replace a book."); // handle full book list
                handleReplaceBook(scan); // prompt to replace a book
            } else {
                System.out.print("Enter Book ID: ");
                String bookID = scan.nextLine(); // get book ID
                System.out.print("Enter Title: ");
                String title = scan.nextLine(); // get title
                System.out.print("Enter Author: ");
                String author = scan.nextLine(); // get author
                System.out.print("Enter Rental Price: ");
                double price = scan.nextDouble(); // get rental price
                scan.nextLine(); // consume newline

                if (confirmAction(scan, "Add book " + title + " by " + author + " for PHP" + String.format("%.2f", price) + "?")) {
                    if (!checkBookID(bookID)) {
                    	addBook(bookID, title, author, price, "Available"); // add book if ID is not taken
                        System.out.println("Book added successfully.\n"); // confirm successful addition
                        displayBooks(); // display updated book list
                    } else {
                    	System.out.println("Book ID " + bookID + " already exists. Please use a different ID.\n"); // handle duplicate book ID
                    }
                }
            }
        }
    }

    // handle duplicate book ID
    public void handleRentBook(Scanner scan) {
        System.out.print("Enter Book ID to rent: "); 
        String rentID = scan.nextLine(); // get book ID to rent
        System.out.print("Enter your name: ");
        String customerName = scan.nextLine(); // get customer name

        if (confirmAction(scan, "Rent book ID " + rentID + " to " + customerName + "?")) {
            rentBook(rentID, customerName); // rent the book
        }
    }

    // method to handle returning a book
    public void handleReturnBook(Scanner scan) {
        System.out.print("Enter Book ID to return: ");
        String returnID = scan.nextLine(); // get book ID to return
        System.out.print("Enter your name: ");
        String customerName = scan.nextLine(); // get customer name

        if (confirmAction(scan, "Return book ID " + returnID + " from " + customerName + "?")) {
            returnBook(returnID, customerName); // return the book
        }
    }

    // method to handle replacing a book
    public void handleReplaceBook(Scanner scan) {
        System.out.print("Enter Book ID to replace: ");
        String oldBookID = scan.nextLine(); // get old book ID
        
        if (checkBookID(oldBookID)) {
        	if (compareStrings(statuses[findBookIndex(oldBookID)], "Available")) {
        		System.out.print("Enter New Book ID: ");
                String newBookID = scan.nextLine(); // get new book ID
                System.out.print("Enter New Title: ");
                String newTitle = scan.nextLine(); // get new title
                System.out.print("Enter New Author: ");
                String newAuthor = scan.nextLine(); // get new author
                System.out.print("Enter New Rental Price: ");
                double newPrice = scan.nextDouble(); // get new rental price
                scan.nextLine(); // consume newline
                
                if (confirmAction(scan, "Replace book ID " + oldBookID + " with new book ID " + newBookID + "?")) {
                	if (!checkBookID(newBookID)) {
                		replaceBook(oldBookID, newBookID, newTitle, newAuthor, newPrice); // replace the book
                		System.out.println("Book replaced successfully.\n"); // confirm successful replacement
                		displayBooks(); // display updated book list
                    } else {
                    	System.out.println("Book ID " + newBookID + " already exists. Please use a different ID.\n"); // handle duplicate new book ID
                    }	
                }
        	} else {
        		System.out.println("Book ID " + oldBookID + " is currently rented. Cannot replace.\n");
        	}
        } else {
            System.out.println("Book ID " + oldBookID + " not found\n"); // handle book not found or rented
        }
    }

    // method to confirm user actions
    public boolean confirmAction(Scanner scan, String message) {
        System.out.print(message + " [Y/N]: ");
        String confirmation = scan.nextLine(); // get confirmation
        return compareStringsIgnoreCase(confirmation, "Y"); // return true if confirmed
    }
       
}