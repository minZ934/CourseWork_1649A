package bookstore.data;

public class CustomStack<T> {
    private T[] stack;
    private int top, capacity;

    @SuppressWarnings("unchecked")
    public CustomStack(Class<T> clazz, int capacity) {
        this.capacity = capacity;
        stack = (T[]) new Object[capacity];
        top = -1;
    }

    public void push(T item) {
        if (top == capacity - 1) {
            System.out.println("Stack is full!");
            return;
        }
        stack[++top] = item;
    }

    public T pop() {
        return (top == -1) ? null : stack[top--];
    }

    public boolean isEmpty() {
        return top == -1;
    }
}
