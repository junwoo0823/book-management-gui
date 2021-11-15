package com.book.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookVO {

	private int num; // NUM NUMBER
	private String title; // TITLE VARCHAR2 (200 BYTE)
	private String author; // AUTHOR VARCHAR2 (40 BYTE)
	private String publisher; // PUBLISHER VARCHAR2 (40 BYTE)
	private String bookDate; // BOOK_DATE VARCHAR2 (20 BYTE)
	private String isbn; // ISBN VARCHAR2(20 BYTE)

} // BookVO
