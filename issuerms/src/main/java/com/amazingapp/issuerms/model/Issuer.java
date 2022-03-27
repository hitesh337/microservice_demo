package com.amazingapp.issuerms.model;


public class Issuer {

	private Long issueId;
	private Long custId;
	private int noOfCopies;
	private Long bookId;
	private String status;
	public Long getIssueId() {
		return issueId;
	}
	public void setIssueId(Long issueId) {
		this.issueId = issueId;
	}
	public Long getCustId() {
		return custId;
	}
	public void setCustId(Long custId) {
		this.custId = custId;
	}
	public int getNoOfCopies() {
		return noOfCopies;
	}
	public void setNoOfCopies(int noOfCopies) {
		this.noOfCopies = noOfCopies;
	}
	public Long getBookId() {
		return bookId;
	}
	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Issuer [issueId=" + issueId + ", custId=" + custId + ", noOfCopies=" + noOfCopies + ", bookId=" + bookId
				+ "]";
	}
	
	
	
	
	
}
