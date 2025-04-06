package bookstore.models;

public class Book {
    private static int nextId = 1;
    private int id;
    private String title;
    private String author;
    private double price;
    private int stock;

    public Book(String title, String author, double price, int stock) {
        this.id = nextId++;
        this.title = title;
        this.author = author;
        this.price = price;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void reduceStock(int quantity) {
        if (stock >= quantity) {
            stock -= quantity;
        }
    }

    @Override
    public String toString() {
        if (stock == 0) {
            return title + " by " + author + " ($" + price + ") - Out of Stock";
        } else {
            return title + " by " + author + " ($" + price + ") - Stock: " + stock;
        }
    }
}
