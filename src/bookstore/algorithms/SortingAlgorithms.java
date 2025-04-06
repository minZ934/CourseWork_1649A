package bookstore.algorithms;

import bookstore.data.CustomArrayList;
import bookstore.models.Book;

public class SortingAlgorithms {
    public static void insertionSort(CustomArrayList<Book> bookList) {
        int n = bookList.size();
        for (int i = 1; i < n; i++) {
            Book key = bookList.get(i);
            int j = i - 1;

            while (j >= 0 && bookList.get(j).getTitle().compareTo(key.getTitle()) > 0) {
                bookList.set(j + 1, bookList.get(j));
                j--;
            }
            bookList.set(j + 1, key);
        }
    }
}
