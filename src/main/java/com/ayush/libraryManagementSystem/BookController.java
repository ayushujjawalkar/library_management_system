package com.ayush.libraryManagementSystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "*")
public class BookController
{
    @Autowired
    private BookService bookService;

//    @GetMapping
//    List<Book> getAllBooks ()
//    {
//        return bookService.findAll();
//    }

    @GetMapping("/all")
    public List<Book> getAllBooks() {
        return bookService.findAll();
    }

    @GetMapping("/{id}")
    public Book getBook(@PathVariable Long id)
    {
        return bookService.findById(id);
    }
//    @PostMapping
//    public Book addBook(@RequestBody Book book)
//    {
//        return bookService.save(book);
//    }

    @PostMapping("/addBook")
    public Book addBook(@RequestBody Book book) {
        return bookService.save(book);
    }

    @PutMapping("/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody Book book)
    {
        // Additional logic to ensure you're updating the correct book
        return bookService.save(book);
    }
//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
//        try {
//            System.out.println("Received DELETE request for book ID: " + id + " (Type: " + id.getClass() + ")");
//            bookService.deleteById(id);
//            return ResponseEntity.ok("Book deleted successfully");
//        } catch (IllegalArgumentException e) {
//            System.out.println("Delete failed: " + e.getMessage());
//            return ResponseEntity.badRequest().body(e.getMessage());
//        } catch (Exception e) {
//            System.out.println("Unexpected error: " + e.getMessage());
//            return ResponseEntity.status(500).body("Internal server error");
//        }
//    }
     //this is for search a book by title or author
    @GetMapping("/search")
    public List<Book> searchBooks(@RequestParam("keyword") String keyword) {
        return bookService.searchBooks(keyword);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        try {
            bookService.deleteById(id);
            return ResponseEntity.ok("Book deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid book ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting book: " + e.getMessage());
        }
    }

    @PostMapping("/{bookId}/borrow/{userId}")
    public ResponseEntity<Book> borrowBook(@PathVariable Long bookId, @PathVariable Long userId)
    {
        Book borrowedBook = bookService.borrowBook(bookId, userId);
        if (borrowedBook != null) {
            return ResponseEntity.ok(borrowedBook);
        } else {
            return ResponseEntity.badRequest().build(); // or a more descriptive error response
        }
    }
    @PostMapping("/{bookId}/return")
    public ResponseEntity<Book> returnBook(@PathVariable Long bookId)
    {
        Book returnedBook = bookService.returnBook(bookId);
        if (returnedBook != null) {
            return ResponseEntity.ok(returnedBook);
        } else {
            return ResponseEntity.badRequest().build(); // or a more descriptive error response
        }
    }

    //filter book  by status
    @GetMapping("/filter")
    public List<Book> filterBooks(@RequestParam String status) {
        List<Book> allBooks = bookService.findAll();

        if ("borrowed".equalsIgnoreCase(status)) {
            return allBooks.stream()
                    .filter(Book::isBorrowed)
                    .toList();
        } else if ("available".equalsIgnoreCase(status)) {
            return allBooks.stream()
                    .filter(book -> !book.isBorrowed())
                    .toList();
        }

        return allBooks;
    }
}
