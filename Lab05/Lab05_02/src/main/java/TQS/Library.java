package TQS;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Library {
    public Library() {
    }

    public List<Book> store = new ArrayList<Book>();

    public void addBook(Book book) {
        store.add(book);
    }

    public List<Book> findBooksByAuthor(String author){
        List<Book> books = new ArrayList<Book>();
        for (Book book : store) {
            if (book.getAuthor().equals(author)) {
                books.add(book);
            }
        }
        return books;
    }

    public List<Book> findBooks(Date from, Date to){
        List<Book> books = new ArrayList<Book>();
        for (Book book : store) {
            if (book.getPublished().after(from) && book.getPublished().before(to)) {
                books.add(book);
            }
        }
        return books;
    }

    public Book findBookByTitle(String title) {
        for (Book book : store) {
            if (book.getTitle().equals(title)) {
                return book;
            }
        }
        return null;
    }
}
