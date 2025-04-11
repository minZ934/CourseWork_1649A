package bookstore.data;

import bookstore.models.Book;

public class StorageBook {

    // Generate sample book list
    public static CustomArrayList<Book> generateBooks() {
        CustomArrayList<Book> bookList = new CustomArrayList<>(20);

        bookList.add(new Book("Java Basics", "John Doe", 39.99, 50));
        bookList.add(new Book("Data Structures", "Alice Smith", 29.99, 30));
        bookList.add(new Book("Algorithms", "Robert Martin", 49.99, 20));

        return bookList;
    }
}
