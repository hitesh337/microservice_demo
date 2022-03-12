package com.amazingapp.bookms.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.amazingapp.bookms.model.Book;
import com.amazingapp.bookms.repository.BookRepository;


@RestController
public class BookController {

	@Autowired
	private BookRepository bookRepository;
	
	@GetMapping("/books")
	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	@GetMapping("/books/{id}")
	public ResponseEntity<Object> getbookById(@PathVariable Long id) {
		Optional<Book> bookFound = bookRepository.findById(id);
		if (bookFound.isPresent()) {
			return ResponseEntity.ok(bookFound.get());
		}

		return ResponseEntity.notFound().build();

	}

	@PostMapping("/books")
	public ResponseEntity<Object> saveBook(@RequestBody Book Book) throws URISyntaxException {
		Book bookSaved = bookRepository.save(Book);
		return ResponseEntity.created(new URI(bookSaved.getId().toString())).body(bookSaved);

	}

	@PutMapping("/books/{id}")
	public ResponseEntity<Object> updateBook(@RequestBody Book Book, @PathVariable Long id) {
		Optional<Book> bookFound = bookRepository.findById(id);
		Map<String, String> err = new HashMap<String, String>();
		if (bookFound.isPresent()) {
			Book oldBook = (Book) bookFound.get();
			oldBook.setAuthor(Book.getAuthor());
			oldBook.setIsbn(Book.getIsbn());
			oldBook.setIssuedCopies(Book.getIssuedCopies());
			oldBook.setPublishedDate(Book.getPublishedDate());
			oldBook.setTitle(Book.getTitle());
			oldBook.setTotalCopies(Book.getTotalCopies());
			
			Book bookSaved = bookRepository.save(oldBook);

			ResponseEntity<Object> re = null;
			try {
				re = ResponseEntity.created(new URI(bookSaved.getId().toString())).body(bookSaved);
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
			return re;
		} else {
			err.put("message", "Book is not available");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err.toString());
		}

	}

	@DeleteMapping("/books")
	public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
		Map<String, String> err = new HashMap<String, String>();
		if (bookRepository.existsById(id)) {
			bookRepository.deleteById(id);
			err.put("message", "Book is deleted successfully!");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err.toString());
		} else {
			err.put("message", "Book is not avaible.Check your id!");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err.toString());
		}

	}

}
