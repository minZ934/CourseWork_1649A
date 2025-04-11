package bookstore.algorithms;

import bookstore.data.CustomQueue;
import bookstore.models.Order;

public class SearchAlgorithms {
    public static Order linearSearch(CustomQueue<Order> queue, int orderId) {
        CustomQueue<Order> tempQueue = new CustomQueue<>(); 

        Order foundOrder = null;

        // Search through the queue
        while (!queue.isEmpty()) {
            Order order = queue.dequeue();
            if (order.getOrderId() == orderId) {
                foundOrder = order;
            }
            tempQueue.enqueue(order); // Save for restoring
        }

        // Restore the original queue
        while (!tempQueue.isEmpty()) {
            queue.enqueue(tempQueue.dequeue());
        }

        return foundOrder;
    }
}
