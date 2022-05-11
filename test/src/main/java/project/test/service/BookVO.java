package project.test.service;

import lombok.Data;

@Data
public class BookVO {
	private String title;
	private String writer;
	private String content;
	private int rental;
	
	@Override
	public String toString() { //3개의 값을 필요로하는 유일한 연산자 사망연산자 ?
		return "책제목 : " + title + ", 저자명 : " + writer + ", 내용 : " + content + ", 대여여부 : " + ((rental == 1)? "대여중" : "대여가능");
	}
	


}
