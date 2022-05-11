package project.test.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import project.test.dao.DataSource;

public class BookServiceImpl implements BookService{
	private DataSource dao = DataSource.getInstance();
	
	private Connection conn;
	private PreparedStatement psmt; 
	private ResultSet rs;
	
	@Override
	public List<BookVO> selectAllList() {
		// 전체 책 목록 가져오기

		List<BookVO> books = new ArrayList<BookVO>();
		BookVO vo;
		String sql = "SELECT * FROM BOOK_LIST";
		try {
			conn = dao.getConnection();
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery(); 
			while (rs.next()) {
				vo = new BookVO(); 
				vo.setTitle(rs.getString("title"));
				vo.setWriter(rs.getString("writer"));
				vo.setContent(rs.getString("content"));
				vo.setRental(rs.getInt("rental"));

				books.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return books;
	}

	@Override
	public BookVO selectBook(String name) {
		BookVO vo = new BookVO();
		String sql = "SELECT * FROM BOOK_LIST WHERE TITLE = ?";
		try {
			conn = dao.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, name); // sql문에 ?가 있으면 set을 적어야함
			rs = psmt.executeQuery(); 
			while (rs.next()) {
				vo.setTitle(rs.getString("title"));
				vo.setWriter(rs.getString("writer"));
				vo.setContent(rs.getString("content"));
				vo.setRental(rs.getInt("rental"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return vo;
	}
	
	@Override
	public List<BookVO> selectBookList(String keyword) {
		List<BookVO> books = new ArrayList<BookVO>();
		BookVO vo;
		String sql = "SELECT * FROM BOOK_LIST WHERE CONTENT LIKE %||?||%";
		try {
			conn = dao.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, keyword); //물음표가 1개니깐 1로 적어둔거임
			rs = psmt.executeQuery(); 
			while (rs.next()) {
				vo = new BookVO(); 
				vo.setTitle(rs.getString("title"));
				vo.setWriter(rs.getString("writer"));
				vo.setContent(rs.getString("content"));
				vo.setRental(rs.getInt("rental"));

				books.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return books;
	}

	
	@Override
	public List<BookVO> selectRentalBookList() {
		List<BookVO> books = new ArrayList<BookVO>();
		BookVO vo;
		String sql = "SELECT * FROM BOOK_LIST WHERE RENTAL = 0";
		try {
			conn = dao.getConnection();
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery(); 
			while (rs.next()) {
				vo = new BookVO(); 
				vo.setTitle(rs.getString("title"));
				vo.setWriter(rs.getString("writer"));
				vo.setContent(rs.getString("content"));
				vo.setRental(rs.getInt("rental"));

				books.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return books;
	}

	@Override
	public void rentalBook(String name) {
		try {
			//DB 새로운 연결을 만들고
			conn = dao.getConnection();
			String sql = "UPDATE BOOK_LIST SET RENTAL = 1 WHERE TITLE = ?";
			
			//prepareStatement 는DB에 전달할 SQL문을 만드는것
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, name);
			
			//DB에서 실행된 SQL문의 결과를 반환
			int result = psmt.executeUpdate(); // 몇번 업데이트했는지 확인이니 int
			
			if(result > 0) {
				System.out.println("해당 책이 정상적으로 대여됬습니다.");
			}
		}catch(SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
	}
	
	@Override
	public void returnBook(String name) {
		try {
			//DB 새로운 연결을 만들고
			conn = dao.getConnection();
			String sql = "UPDATE BOOK_LIST SET RENTAL = 0 WHERE TITLE = ?";
			
			//prepareStatement 는DB에 전달할 SQL문을 만드는것
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, name);
			
			//DB에서 실행된 SQL문의 결과를 반환
			int result = psmt.executeUpdate(); // 몇번 업데이트했는지 확인이니 int
			
			if(result > 0) {
				System.out.println("해당 책이 정상적으로 대여됬습니다.");
			}
		}catch(SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
	}
	
	

	@Override
	public void bookInsert(BookVO vo) {
		String sql = "INSERT INTO BOOK VALUES(?, ?, ?, ?)";
		try {
			psmt = conn.prepareCall(sql);
			psmt.setString(1, vo.getTitle());
			psmt.setString(2, vo.getWriter());
			psmt.setString(3, vo.getContent());
			psmt.setInt(4, vo.getRental());
			
			int result = psmt.executeUpdate();
			if(result > 0) {
				System.out.println("정상적으로 등록되었습니다.");
			}
		}catch(SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	
	private void close() {
		try {
			if (rs != null)
				rs.close();
			if (psmt != null)
				psmt.close();
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}



}
