package bookstore;

import java.util.Scanner;
import bookstore.models.Book;
import bookstore.models.Order;
import bookstore.data.CustomArrayList;
import bookstore.data.CustomQueue;
import bookstore.data.CustomStack;
import bookstore.data.StorageBook;

public class OnlineBookstore {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        CustomArrayList<Book> bookInventory = StorageBook.generateBooks();
        CustomQueue<Order> orderQueue = new CustomQueue<>(10);
        CustomStack<Book> browsingHistory = new CustomStack<>(Book.class, 10);
        CustomArrayList<Order> orderHistory = new CustomArrayList<>(10);

        while (true) {
            System.out.println("\n1. View Books\n2. Browse Book\n3. Place Order\n4. Process Order\n5. Search Order\n6. Exit");
            System.out.print("Enter choice: ");

            int choice = -1;
            try {
                choice = Integer.parseInt(scanner.nextLine());  // Read input as string and try to convert to int
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number from the menu.");
                continue;  // Skip to the next iteration if input is invalid
            }

            switch (choice) {
                case 1:
                    bookInventory.displayBooks();
                    break;

                case 2:
                    System.out.println("\nSearch Book by:");
                    System.out.println("1. Book ID");
                    System.out.println("2. Book Title");
                    System.out.print("Enter your choice: ");
                    int searchChoice = scanner.nextInt();
                    scanner.nextLine();

                    if (searchChoice == 1) {
                        System.out.print("\nEnter Book ID: ");
                        int bookId = -1;

                        // Validate input for Book ID
                        while (true) {
                            try {
                                bookId = Integer.parseInt(scanner.nextLine());
                                break; // Break out of the loop if the input is valid
                            } catch (NumberFormatException e) {
                                System.out.print("Invalid input. Please enter a valid Book ID: ");
                            }
                        }

                        boolean bookFound = false;
                        for (int i = 0; i < bookInventory.size(); i++) {
                            Book book = bookInventory.get(i);
                            if (book.getId() == bookId) {
                                System.out.println("Book found: " + book);
                                browsingHistory.push(book);
                                bookFound = true;
                                break;
                            }
                        }
                        if (!bookFound) {
                            System.out.println("No book found with ID " + bookId);
                        }
                    } else if (searchChoice == 2) {
                        System.out.print("\nEnter Book Title (partial or full): ");
                        String titleQuery = scanner.nextLine().toLowerCase();

                        boolean found = false;
                        for (int i = 0; i < bookInventory.size(); i++) {
                            Book book = bookInventory.get(i);
                            if (book.getTitle().toLowerCase().contains(titleQuery)) {
                                System.out.println("Book found: " + book);
                                browsingHistory.push(book);
                                found = true;
                            }
                        }
                        if (!found) {
                            System.out.println("No book found matching your search.");
                        }
                    } else {
                        System.out.println("Invalid choice.");
                    }
                    break;

                case 3: // Allow multiple book selections with quantity
                    String name;
                    String address;
                    // Ensure valid customer name (no numbers)
                    while (true) {
                        System.out.print("\nEnter Customer Name: ");
                        name = scanner.nextLine();
                        if (name.matches("[a-zA-Z ]+")) {  // Check for letters and spaces only
                            break;
                        } else {
                            System.out.println("Invalid name. Customer name should contain only letters and spaces.");
                        }
                    }

                    // Ensure valid address (must have number and road name)
                    while (true) {
                        System.out.print("Enter Address: ");
                        address = scanner.nextLine();
                        if (address.matches(".*\\d.*") && address.matches(".*[a-zA-Z]+.*")) {  // Check for number and letters
                            break;
                        } else {
                            System.out.println("Invalid address. Address should contain a number and a road name.");
                        }
                    }

                    Order newOrder = new Order(name, address);

                    // Show available books with stock
                    System.out.println("\nAvailable Books:");
                    bookInventory.displayBooks();

                    System.out.println("\nEnter book number and quantity | Press '0' to finish order:");

                    while (true) {
                        System.out.print("Enter book and quantity: ");
                        String input = scanner.nextLine().trim();

                        if (input.equals("0")) break; // Stop ordering

                        String[] parts = input.split(" ");
                        if (parts.length != 2) {
                            System.out.println("Invalid input. Use format: <book_number> <quantity>");
                            continue;
                        }

                        try {
                            int bookIndex = Integer.parseInt(parts[0]) - 1; // Convert to 0-based index
                            int quantity = Integer.parseInt(parts[1]);

                            if (bookIndex < 0 || bookIndex >= bookInventory.size() || quantity <= 0) {
                                System.out.println("Invalid book selection or quantity.");
                                continue;
                            }

                            Book selectedBook = bookInventory.get(bookIndex);

                            if (selectedBook.getStock() >= quantity) {
                                newOrder.addBook(selectedBook, quantity);
                                selectedBook.reduceStock(quantity); // Deduct stock
                                System.out.println("Added " + quantity + " x " + selectedBook.getTitle() + " to order.");
                            } else {
                                System.out.println("Not enough stock available.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter numbers.");
                        }
                    }

                    if (!newOrder.isEmpty()) {
                        orderQueue.enqueue(newOrder);
                        System.out.println("Order placed successfully!");
                    } else {
                        System.out.println("No books added. Order canceled.");
                    }
                    break;

                case 4: // Process Order
                    if (!orderQueue.isEmpty()) {
                        Order processedOrder = orderQueue.dequeue();
                        processedOrder.sortBooks();
                        orderHistory.add(processedOrder); // Store in history
                        System.out.println("Processing order:\n" + processedOrder);
                    } else {
                        System.out.println("No orders to process.");
                    }
                    break;

                case 5: // Search Order
                    System.out.print("\nEnter Order ID to search: ");
                    int searchOrderId = -1;
                    try {
                        searchOrderId = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid Order ID.");
                        break;
                    }

                    boolean orderFound = false;
                    for (int i = 0; i < orderHistory.size(); i++) {
                        Order order = orderHistory.get(i);
                        if (order.getOrderId() == searchOrderId) {
                            System.out.println("\nOrder Found:\n" + order);
                            orderFound = true;
                            break;
                        }
                    }

                    if (!orderFound) {
                        System.out.println("Order not found.");
                    }
                    break;

                case 6:
                    System.out.println("Exiting system...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
