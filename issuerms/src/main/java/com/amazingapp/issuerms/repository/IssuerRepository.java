package com.amazingapp.issuerms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.amazingapp.issuerms.entity.IssuerEntity;
import com.amazingapp.issuerms.model.Issuer;

public interface IssuerRepository extends JpaRepository<IssuerEntity, Long> {
	public List<IssuerEntity> findByCustId(@Param("custId") Long custId);

	@Query(value="select bookId from IssuerEntity where custId =:custId")
	public List<Long> getBookIdByCustId(@Param("custId") Long custId);
	
	public Long countByCustIdAndBookIdAndStatus(@Param("custId") Long custId,@Param("bookId") Long bookId,@Param("status") String status);
	
	@Query(value="select max(issueId) from IssuerEntity where custId =:custId and bookId=:bookId")
	public Long findIssueIdByCustIdAndBookId(@Param("custId") Long custId,@Param("bookId") Long bookId);
}
