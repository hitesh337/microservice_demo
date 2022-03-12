package com.amazingapp.bookms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amazingapp.bookms.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}
