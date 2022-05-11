package project.test.service;

import java.util.List;


public interface BookService {
	List<BookVO> selectAllList(); // 전체조회
	
	BookVO selectBook(String name); // 단건조회
	
	List<BookVO> selectBookList(String keyword); // 내용조회
	
	List<BookVO> selectRentalBookList(); // 대여가능 조회
	
	void rentalBook(String name);	//책 대여
	
	void returnBook(String name); // 책반납
	
	void bookInsert(BookVO vo); // 책등록
	
}
