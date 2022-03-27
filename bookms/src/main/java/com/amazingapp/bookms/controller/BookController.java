package com.amazingapp.bookms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.amazingapp.bookms.entity.BookEntity;
import com.amazingapp.bookms.model.Book;
import com.amazingapp.bookms.service.BookService;


@RestController
public class BookController {

	@Autowired
	private BookService bookService;

	@GetMapping(path="/books",produces = MediaType.APPLICATION_JSON_VALUE)
	public List<BookEntity> getBooks() {
		return bookService.getBooks();
	}
	
	@GetMapping(path="/books/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BookEntity> getBooksById(@PathVariable Long id ) {
		return bookService.getBooksById(id);

	}
	
	@PostMapping(path="/books",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BookEntity> saveBook(@RequestBody Book book) {
		System.out.println(book.toString());
		return  bookService.saveBook(book);
		
	}
	
	@PutMapping("/books/{id}")
	public ResponseEntity<Object> updateBook(@RequestBody Book book, @PathVariable("id") Long id) {
		return bookService.updateBook(book, id);
	}

	@DeleteMapping("/books/{id}")
	public ResponseEntity<String> deleteBook(@PathVariable("id") Long id) {
		return bookService.deleteBook(id);
	}
	
	@GetMapping(path="/book/{bookId}",produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Integer> getNoOfBooks(@PathVariable Long bookId ) {
		return bookService.getNoOfBooks(bookId);

	}

	@PutMapping(path="/book/{id}/{issuedCopies}" ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity updateNoOfBooks(@PathVariable("id") Long id , @PathVariable("issuedCopies") Integer issuedCopies) {
		Book book = new Book();
		book = bookService.getBookDeailsById(id);
		book.setId(id);
		book.setIssuedCopies(issuedCopies);
		 
		return bookService.updateNoOfBooks(book);

	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(
	  MethodArgumentNotValidException ex) {
	    Map<String, String> errors = new HashMap<>();
	    ex.getBindingResult().getAllErrors().forEach((error) -> {
	        String fieldName = ((FieldError) error).getField();
	        String errorMessage = error.getDefaultMessage();
	        errors.put(fieldName, errorMessage);
	    });
	    
	    System.out.println(errors);
	    return errors;
	}
}
