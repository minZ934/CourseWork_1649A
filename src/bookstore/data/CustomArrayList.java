package bookstore.data;

import java.util.Arrays;

public class CustomArrayList<T> {
    private Object[] elements;
    private int size;

    public CustomArrayList(int capacity) {
        elements = new Object[capacity];
        size = 0;
    }

    public void add(T item) {
        if (size == elements.length) {
            resize();
        }
        elements[size++] = item;
    }

    public T get(int index) {
        if (index >= 0 && index < size) {
            return (T) elements[index];
        }
        throw new IndexOutOfBoundsException("Invalid index");
    }

    public void set(int index, T item) {
        if (index >= 0 && index < size) {
            elements[index] = item;
        } else {
            throw new IndexOutOfBoundsException("Invalid index");
        }
    }

    public int size() {
        return size;
    }

    public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        for (int i = index; i < size - 1; i++) {
            elements[i] = elements[i + 1];
        }
        elements[--size] = null; // Remove reference to allow garbage collection
    }

    private void resize() {
        elements = Arrays.copyOf(elements, elements.length * 2);
    }

    public void displayBooks() {
        if (size == 0) {
            System.out.println("No books available.");
            return;
        }
        System.out.println("Book Inventory:");
        for (int i = 0; i < size; i++) {
            System.out.println((i + 1) + ". " + elements[i]);
        }
    }

    @Override
    public String toString() {
        return Arrays.toString(Arrays.copyOf(elements, size));
    }
}
