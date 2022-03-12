package com.amazingapp.issuerms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.amazingapp.issuerms.model.Issuer;

public interface IssuerRepository extends JpaRepository<Issuer, Long>{

	public List<Issuer> findByCustId(@Param("custId") Long custId);
	
	@Query(value = "select bookId from Issuer where custId = custId")
	public List<Long> getBookIdByCustId(@Param("custId") Long custId);
	
	public Long countByCustIdAndBookId(@Param("custId") Long custId,@Param("bookId") Long bookId);
}
