package bookstore;

import java.util.Scanner;
import bookstore.models.Book;
import bookstore.models.Order;
import bookstore.data.CustomArrayList;
import bookstore.data.CustomQueue;
import bookstore.data.CustomStack;
import bookstore.data.StorageBook;
import bookstore.algorithms.SortingAlgorithms;

public class OnlineBookstore {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        CustomArrayList<Book> bookInventory = StorageBook.generateBooks();
        CustomQueue<Order> orderQueue = new CustomQueue<>();
        CustomStack<Book> browsingHistory = new CustomStack<>(Book.class, 10);
        CustomArrayList<Order> orderHistory = new CustomArrayList<>(10);

        while (true) {
            System.out.println("\n1. View Books\n2. Browse Book\n3. Add Book\n4. Place Order\n5. Process Order\n6. History Order\n7. Search Order\n8. Sort Book\n9. Exit");
            System.out.print("Enter choice: ");

            int choice = -1;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number from the menu.");
                continue;
            }

            switch (choice) {
                case 1:
                    bookInventory.displayBooks();
                    break;

                case 2:
                    System.out.println("\nSearch Book by:");
                    System.out.println("1. Book ID");
                    System.out.println("2. Book Title");
                    System.out.println("Current Available Books: " + bookInventory.size());
                    System.out.print("Enter your choice: ");
                    int searchChoice = scanner.nextInt();
                    scanner.nextLine();

                    if (searchChoice == 1) {
                        System.out.print("\nEnter Book ID: ");
                        int bookId = -1;
                        while (true) {
                            try {
                                bookId = Integer.parseInt(scanner.nextLine());
                                break;
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
                        System.out.print("\nEnter Book Title: ");
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

                case 3:
                    // Get Book Title
                    String title;
                    while (true) {
                        System.out.print("Enter Book Title: ");
                        title = scanner.nextLine();
                        if (title.matches("[a-zA-Z0-9\\s]+")) {
                            break;
                        } else {
                            System.out.println("Invalid title. Only letters, numbers and spaces allowed.");
                        }
                    }

                    // Get Author Name
                    String author;
                    while (true) {
                        System.out.print("Enter Author: ");
                        author = scanner.nextLine();
                        if (author.matches("[a-zA-Z\\s]+")) {
                            break;
                        } else {
                            System.out.println("Invalid author name. Author name must not contain numbers.");
                        }
                    }

                    // Get Price
                    double price = -1;
                    while (price < 0) {
                        System.out.print("Enter Price: ");
                        try {
                            price = Double.parseDouble(scanner.nextLine());
                            if (price < 0) System.out.println("Price must be positive.");
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter a valid number.");
                        }
                    }

                    // Get Stock
                    int stock = -1;
                    while (stock < 0) {
                        System.out.print("Enter Stock Quantity: ");
                        try {
                            stock = Integer.parseInt(scanner.nextLine());
                            if (stock < 0) System.out.println("Stock must be a non-negative integer.");
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter an integer.");
                        }
                    }

                    // Add and Save new book into file
                    Book newBook = new Book(title, author, price, stock);
                    bookInventory.add(newBook);
                    System.out.println("Book added to inventory successfully!");
                    break;

                case 4:
                    // Check if this is the first order
                    boolean isFirstOrder = orderQueue.isEmpty();

                    Order orderToUse = null;

                    if (!isFirstOrder) {
                        // Ask for Order ID only on second or later usage
                        System.out.print("Enter Order ID: ");
                        int enteredOrderId = -1;
                        try {
                            enteredOrderId = Integer.parseInt(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Returning to main menu.");
                            break;
                        }

                        boolean found = false;
                        for (int i = 0; i < orderQueue.size(); i++) {
                            Order o = orderQueue.get(i);
                            if (o.getOrderId() == enteredOrderId) {
                                found = true;
                                System.out.print("Do you want to continue this order? (yes/no): ");
                                String confirm = scanner.nextLine().trim().toLowerCase();
                                if (confirm.equals("yes") || confirm.equals("y")) {
                                    orderToUse = o;
                                    System.out.println("Continuing order for:");
                                    System.out.println("Customer: " + o.getCustomerName());
                                    System.out.println("Address: " + o.getAddress());
                                    System.out.println("Order ID: " + o.getOrderId());
                                    break;
                                }
                            }
                        }

                        if (!found || orderToUse == null) {
                            System.out.println("No matching unprocessed order found or user declined to continue. Creating new order...");
                        }
                    }

                    // If not continuing an old order, create a new one
                    if (orderToUse == null) {
                        String name;
                        String address;

                        while (true) {
                            System.out.print("\nEnter Customer Name: ");
                            name = scanner.nextLine();
                            if (name.matches("[a-zA-Z ]+")) {
                                break;
                            } else {
                                System.out.println("Invalid name. Customer name should contain only letters and spaces.");
                            }
                        }

                        while (true) {
                            System.out.print("Enter Address: ");
                            address = scanner.nextLine();
                            if (address.matches(".*\\d.*") && address.matches(".*[a-zA-Z]+.*")) {
                                break;
                            } else {
                                System.out.println("Invalid address. Address should contain a number and a road name.");
                            }
                        }

                        orderToUse = new Order(name, address);
                    }

                    System.out.println("\nAvailable Books:");
                    bookInventory.displayBooks();

                    System.out.println("\nEnter book number and quantity | Press '0' to finish order:");

                    while (true) {
                        System.out.print("Enter book and quantity: ");
                        String input = scanner.nextLine().trim();

                        if (input.equals("0")) break;

                        String[] parts = input.split(" ");
                        if (parts.length != 2) {
                            System.out.println("Invalid input. Use format: <book_number> <quantity>");
                            continue;
                        }

                        try {
                            int bookIndex = Integer.parseInt(parts[0]) - 1;
                            int quantity = Integer.parseInt(parts[1]);

                            if (bookIndex < 0 || bookIndex >= bookInventory.size() || quantity <= 0) {
                                System.out.println("Invalid book selection or quantity.");
                                continue;
                            }

                            Book selectedBook = bookInventory.get(bookIndex);

                            if (selectedBook.getStock() >= quantity) {
                                orderToUse.addBook(selectedBook, quantity);
                                selectedBook.reduceStock(quantity);
                                System.out.println("Added " + quantity + " x " + selectedBook.getTitle() + " to order.");
                            } else {
                                System.out.println("Not enough stock available.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter numbers.");
                        }
                    }

                    if (!orderToUse.isEmpty()) {
                        if (!orderQueue.contains(orderToUse)) {
                            orderQueue.enqueue(orderToUse);
                        }
                        System.out.println("Order saved successfully!");
                        System.out.println("Customer: " + orderToUse.getCustomerName());
                        System.out.println("Address: " + orderToUse.getAddress());
                        System.out.println("Order ID: " + orderToUse.getOrderId());
                    } else {
                        System.out.println("No books added. Order canceled.");
                    }
                    break;

                case 5:
                    if (orderQueue.isEmpty()) {
                        System.out.println("No orders in the queue.");
                    } else {
                        System.out.println("Processing next order...");

                        Order order = orderQueue.dequeue();

                        if (order != null) {
                            System.out.println(order);  // Show order info

                            double total = 0;
                            for (int j = 0; j < order.getBooks().size(); j++) {
                                Book book = order.getBooks().get(j);
                                int qty = order.getQuantities().get(j);
                                total += book.getPrice() * qty;
                            }

                            System.out.printf("Total Price: $%.2f\n", total);
                            System.out.println("--------------------------");

                            orderHistory.add(order);  // Save to history after processing
                        }
                    }
                    break;

                case 6:
                    if (orderHistory.size() == 0) {
                        System.out.println("No processed orders in history.");
                    } else {
                        System.out.println("\nOrder History:");
                        for (int i = 0; i < orderHistory.size(); i++) {
                            Order order = orderHistory.get(i);
                            System.out.println(order);
                            System.out.printf("Total Price: $%.2f\n", order.getTotalPrice());
                            System.out.println("--------------------------");
                        }
                    }
                    break;

                case 7:
                    if (orderHistory.size() == 0) {
                        System.out.println("\nNo orders have been placed yet.");
                        break;
                    }

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
                            System.out.printf("Total Price: $%.2f\n", order.getTotalPrice());
                            orderFound = true;
                            break;
                        }
                    }

                    if (!orderFound) {
                        System.out.println("Order not found.");
                    }
                    break;

                case 8:
                    System.out.println("\nSort Books:");
                    System.out.println("1. By Title (A-Z)");
                    System.out.println("2. By Price (Low to High)");
                    System.out.println("3. By Price (High to Low)");
                    System.out.print("Enter your choice: ");

                    int sortChoice = -1;
                    try {
                        sortChoice = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input.");
                        break;
                    }

                    switch (sortChoice) {
                        case 1:
                            SortingAlgorithms.sortByTitleAZ(bookInventory);
                            System.out.println("Books sorted by Title (A-Z):");
                            bookInventory.displayBooks();
                            break;
                        case 2:
                            SortingAlgorithms.sortByPriceLowToHigh(bookInventory);
                            System.out.println("Books sorted by Price (Low → High):");
                            bookInventory.displayBooks();
                            break;
                        case 3:
                            SortingAlgorithms.sortByPriceHighToLow(bookInventory);
                            System.out.println("Books sorted by Price (High → Low):");
                            bookInventory.displayBooks();
                            break;
                        default:
                            System.out.println("Invalid sort option.");
                    }
                    break;

                case 9:
                    System.out.println("Exiting system...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
