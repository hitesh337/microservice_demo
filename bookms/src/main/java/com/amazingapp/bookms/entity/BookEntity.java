package com.amazingapp.bookms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "book_tbl")
public class BookEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "book_id")
	private Long id;
	@Column(name = "isbn")
	private String isbn;
	@Column(name = "title")
	private String title;
	@Column(name = "published_Date")
	private String publishedDate;
	@Column(name = "total_Copies")
	private int totalCopies;
	@Column(name = "issued_Copies")
	private int issuedCopies;
	@Column(name = "author")
	private String author;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(String publishedDate) {
		this.publishedDate = publishedDate;
	}

	public int getTotalCopies() {
		return totalCopies;
	}

	public void setTotalCopies(int totalCopies) {
		this.totalCopies = totalCopies;
	}

	public int getIssuedCopies() {
		return issuedCopies;
	}

	public void setIssuedCopies(int issuedCopies) {
		this.issuedCopies = issuedCopies;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

}
