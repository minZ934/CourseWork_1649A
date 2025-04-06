package bookstore.data;

import bookstore.models.Book;

public class StorageBook {
    public static CustomArrayList<Book> generateBooks() {
        CustomArrayList<Book> bookList = new CustomArrayList<>(20);

        bookList.add(new Book("Java Basics", "John Doe", 39.99, 50));
        bookList.add(new Book("Data Structures", "Alice Smith", 29.99, 30));
        bookList.add(new Book("Algorithms", "Robert Martin", 49.99, 20));
        bookList.add(new Book("Clean Code", "Robert C. Martin", 42.99, 40));
        bookList.add(new Book("Design Patterns", "Erich Gamma", 35.50, 60));
        bookList.add(new Book("Refactoring", "Martin Fowler", 45.00, 30));
        bookList.add(new Book("The Pragmatic Programmer", "Andrew Hunt", 41.99, 50));
        bookList.add(new Book("Effective Java", "Joshua Bloch", 39.00, 70));
        bookList.add(new Book("Code Complete", "Steve McConnell", 50.99, 20));
        bookList.add(new Book("Soft Skills", "John Sonmez", 30.99, 80));
        bookList.add(new Book("Eloquent JavaScript", "Marijn Haverbeke", 25.99, 90));
        bookList.add(new Book("You Don't Know JS", "Kyle Simpson", 27.99, 40));
        bookList.add(new Book("Introduction to Algorithms", "Thomas H. Cormen", 55.99, 30));
        bookList.add(new Book("Python Crash Course", "Eric Matthes", 29.99, 60));
        bookList.add(new Book("The Mythical Man-Month", "Frederick P. Brooks Jr.", 36.99, 50));
        bookList.add(new Book("Working Effectively with Legacy Code", "Michael Feathers", 48.50, 30));
        bookList.add(new Book("C Programming Language", "Brian W. Kernighan", 20.99, 70));
        bookList.add(new Book("JavaScript: The Good Parts", "Douglas Crockford", 22.99, 40));
        bookList.add(new Book("Computer Networking", "James F. Kurose", 60.99, 30));
        bookList.add(new Book("Artificial Intelligence", "Stuart Russell", 55.50, 40));

        return bookList;
    }
}
