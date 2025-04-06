package bookstore.algorithms;

import bookstore.data.CustomQueue;
import bookstore.models.Order;

public class SearchAlgorithms {
    public static Order linearSearch(CustomQueue<Order> queue, int orderId) {
        CustomQueue<Order> tempQueue = new CustomQueue<>(queue.size());
        Order foundOrder = null;

        while (!queue.isEmpty()) {
            Order order = queue.dequeue();
            if (order.getOrderId() == orderId) {
                foundOrder = order;
            }
            tempQueue.enqueue(order);
        }

        while (!tempQueue.isEmpty()) {
            queue.enqueue(tempQueue.dequeue());
        }

        return foundOrder;
    }
}
