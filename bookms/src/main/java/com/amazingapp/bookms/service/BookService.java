package com.amazingapp.bookms.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.amazingapp.bookms.entity.BookEntity;
import com.amazingapp.bookms.model.Book;
import com.amazingapp.bookms.repository.BookRepository;

@Service
public class BookService {

	@Autowired
	BookRepository bookRepository;

	public List<BookEntity> getBooks() {
		return bookRepository.findAll();
	}

	public ResponseEntity getBooksById(Long id) {
		Optional<BookEntity> bookFound = bookRepository.findById(id);
		if (bookFound.isPresent()) {
			return ResponseEntity.ok(bookFound.get());

		}
		return ResponseEntity.notFound().build();
	}

	public ResponseEntity<BookEntity> saveBook(Book book) {

		BookEntity bookSaved = bookRepository.save(convertModelToEntity(book));
		ResponseEntity<BookEntity> re = null;
		try {
			re = ResponseEntity.created(new URI(bookSaved.getId().toString())).body(bookSaved);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return re;
	}

	public ResponseEntity updateBook(Book book, Long id) {
		book.setId(id);
		ResponseEntity<BookEntity> re = null;
		if (bookRepository.existsById(id)) {
			BookEntity bookSaved = bookRepository.save(convertModelToEntity(book));
			try {
				re = ResponseEntity.created(new URI(bookSaved.getId().toString())).body(bookSaved);
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
		else
		{
			Map<String, String> errors = new HashMap<String, String>();
			errors.put("message", "Book is not available.");
			re.status(HttpStatus.OK).body(errors);
		}
		return re;
	}

	public ResponseEntity deleteBook(Long id) {
		Map<String, String> errors = new HashMap<String, String>();
		if (bookRepository.existsById(id)) {
			bookRepository.deleteById(id);
			errors.put("message", "Book is deleted successfully!");
			return ResponseEntity.status(HttpStatus.OK).body(errors);
		} else {
			errors.put("message", "Book is not available, check your 'ID'");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
		}
	}

	private BookEntity convertModelToEntity(Book book) {
		BookEntity bookEntity = new BookEntity();
		bookEntity.setId(book.getId());
		bookEntity.setAuthor(book.getAuthor());
		bookEntity.setIsbn(book.getIsbn());
		bookEntity.setIssuedCopies(book.getIssuedCopies());
		bookEntity.setPublishedDate(book.getPublishedDate());
		bookEntity.setTitle(book.getTitle());
		bookEntity.setTotalCopies(book.getTotalCopies());

		return bookEntity;
	}

	private Book convertEntityToModel(BookEntity bookEntity) {
		Book book = new Book();
		book.setId(bookEntity.getId());
		book.setAuthor(bookEntity.getAuthor());
		book.setIsbn(bookEntity.getIsbn());
		book.setIssuedCopies(bookEntity.getIssuedCopies());
		book.setPublishedDate(bookEntity.getPublishedDate());
		book.setTitle(bookEntity.getTitle());
		book.setTotalCopies(bookEntity.getTotalCopies());

		return book;
	}

	public Map<String, Integer> getNoOfBooks(Long bookId) {
		Map<String, Integer> hm = new HashMap<>();
		List<Long[]> lst = bookRepository.getTotalCopiesAndIssuedCopiesByBookId(bookId);
		for (Long[] longs : lst) {
			hm.put("totalCopies", longs[0].intValue());
			hm.put("issuedCopies", longs[1].intValue());
		}
		return hm;

	}

	public ResponseEntity updateNoOfBooks(Book book) {
		BookEntity bookSaved = bookRepository.save(convertModelToEntity(book));
		ResponseEntity<BookEntity> re = null;
		try {
			re = ResponseEntity.created(new URI(bookSaved.getId().toString())).body(bookSaved);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return re;
	}

	public Book getBookDeailsById(Long id) {
		Optional<BookEntity> bookEntity = bookRepository.findById(id);
		return convertEntityToModel(bookEntity.get());
	}

}
