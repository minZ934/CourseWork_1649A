package bookstore.data;

public class CustomQueue<T> {
    private T[] queue;
    private int front, rear, size, capacity;

    @SuppressWarnings("unchecked")
    public CustomQueue(int capacity) {
        this.capacity = capacity;
        queue = (T[]) new Object[capacity];
        front = 0;
        rear = -1;
        size = 0;
    }

    public void enqueue(T item) {
        if (size == capacity) {
            System.out.println("Queue is full!");
            return;
        }
        rear = (rear + 1) % capacity;
        queue[rear] = item;
        size++;
    }

    public T dequeue() {
        if (isEmpty()) return null;
        T item = queue[front];
        front = (front + 1) % capacity;
        size--;
        return item;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }
}
