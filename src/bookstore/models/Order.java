package bookstore.models;

import bookstore.data.CustomArrayList;

public class Order {
    private static int nextOrderId = 1;
    private int orderId;
    private String customerName;
    private String address;
    private CustomArrayList<Book> books;
    private CustomArrayList<Integer> quantities;

    public Order(String customerName, String address) {
        this.orderId = nextOrderId++;
        this.customerName = customerName;
        this.address = address;
        this.books = new CustomArrayList<>(10);
        this.quantities = new CustomArrayList<>(10);
    }

    public int getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getAddress() {
        return address;
    }

    public void addBook(Book book, int quantity) {
        books.add(book);
        quantities.add(quantity);
    }

    public void sortBooks() {
        for (int i = 1; i < books.size(); i++) {
            Book keyBook = books.get(i);
            int keyQty = quantities.get(i);
            int j = i - 1;

            while (j >= 0 && books.get(j).getTitle().compareTo(keyBook.getTitle()) > 0) {
                books.set(j + 1, books.get(j));
                quantities.set(j + 1, quantities.get(j));
                j--;
            }
            books.set(j + 1, keyBook);
            quantities.set(j + 1, keyQty);
        }
    }

    public boolean isEmpty() {
        return books.size() == 0;
    }

    @Override
    public String toString() {
        StringBuilder orderDetails = new StringBuilder();
        orderDetails.append("Order ID: #").append(orderId)
                .append("\nCustomer: ").append(customerName)
                .append("\nAddress: ").append(address)
                .append("\nBooks:\n");

        for (int i = 0; i < books.size(); i++) {
            orderDetails.append("  - ").append(books.get(i).getTitle())
                    .append(" (x").append(quantities.get(i)).append(")\n");
        }

        return orderDetails.toString();
    }

    public CustomArrayList<Book> getBooks() {
        return books;
    }

    public CustomArrayList<Integer> getQuantities() {
        return quantities;
    }
    public double getTotalPrice() {
        double total = 0;
        for (int i = 0; i < books.size(); i++) {
            total += books.get(i).getPrice() * quantities.get(i);
        }
        return total;
    }
}
