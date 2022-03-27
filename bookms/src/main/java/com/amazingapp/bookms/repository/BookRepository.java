package com.amazingapp.bookms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.amazingapp.bookms.entity.BookEntity;

public interface BookRepository extends JpaRepository<BookEntity, Long>{

	@Query(value="select totalCopies, issuedCopies from BookEntity where id = :bookId")
	public List<Long[]> getTotalCopiesAndIssuedCopiesByBookId(@Param("bookId") Long bookId);
}
