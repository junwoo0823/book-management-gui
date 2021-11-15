package com.book.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.book.domain.BookVO;

public class BookDAO {

	/**
	 * DB 접속
	 **/

	private final String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private final String user = "myuser";
	private final String passwd = "1234";

	private Connection getConnection() throws ClassNotFoundException, SQLException {
		Connection con = null;

		// JDBC 드라이버 로딩
		Class.forName("oracle.jdbc.driver.OracleDriver");
		// DB연결
		con = DriverManager.getConnection(url, user, passwd);

		return con;
	} // getConnection

	private void close(Connection con, PreparedStatement pstmt) {
		close(con, pstmt, null);
	} // close(con, pstmt)

	private void close(Connection con, PreparedStatement pstmt, ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			if (pstmt != null) {
				pstmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	} // close(con, pstmt, rs)

	/**
	 * JUint 테스트
	 **/

	public int getCountAll() {
		int count = 0;

		Connection con = null; // 접속
		PreparedStatement pstmt = null; // sql문장객체 타입
		ResultSet rs = null;

		try {
			con = getConnection();

			String sql = "SELECT count(*) AS cnt FROM book";

			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				count = rs.getInt(1); // rs.getInt("cnt")
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(con, pstmt, rs);
		}
		return count;
	} // getCountAll

	public int deleteAll() {
		int count = 0;

		// JDBC
		Connection con = null; // 접속
		PreparedStatement pstmt = null; // sql문장객체 타입

		try {
			con = getConnection();

			String sql = "DELETE FROM book";

			pstmt = con.prepareStatement(sql);
			count = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(con, pstmt);
		}
		return count;
	} // deleteAll

	public BookVO getBookByNum(int num) {
		BookVO bookVO = new BookVO();

		Connection con = null; // 접속
		PreparedStatement pstmt = null; // sql문장객체 타입
		ResultSet rs = null;

		try {
			con = getConnection();

			String sql = "SELECT * FROM book WHERE num = ?";

			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				bookVO.setNum(rs.getInt("num"));
				bookVO.setTitle(rs.getString("title"));
				bookVO.setAuthor(rs.getString("author"));
				bookVO.setPublisher(rs.getString("publisher"));
				bookVO.setBookDate(rs.getString("book_date"));
				bookVO.setIsbn(rs.getString("isbn"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(con, pstmt, rs);
		}
		return bookVO;
	} // getBookByNum

	public int num() {
		int num = 0;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = getConnection();

			String sql = "SELECT SEQ_BOOK.NEXTVAL FROM DUAL";

			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				num = rs.getInt("NEXTVAL") - 1;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(con, pstmt);
		}
		return num;
	} // num

	/**
	 * 프로그램 실행
	 **/

	public List<BookVO> getBooks() {
		List<BookVO> list = new ArrayList<>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = getConnection();

			String sql = "SELECT * FROM book ORDER BY num ";

			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				BookVO bookVO = new BookVO();
				bookVO.setNum(rs.getInt("num"));
				bookVO.setTitle(rs.getString("title"));
				bookVO.setAuthor(rs.getString("author"));
				bookVO.setPublisher(rs.getString("publisher"));
				bookVO.setBookDate(rs.getString("book_date"));
				bookVO.setIsbn(rs.getString("isbn"));

				list.add(bookVO);
			} // while

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(con, pstmt, rs);
		}
		return list;
	} // getBooks

	public List<BookVO> search(String field, String word) {
		List<BookVO> list = new ArrayList<>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = getConnection();

			String sql = "";
			sql += "SELECT * FROM book ";
			sql += "WHERE " + field + " LIKE ? ";

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + word + "%");

			rs = pstmt.executeQuery();

			while (rs.next()) {
				BookVO bookVO = new BookVO();
				bookVO.setNum(rs.getInt("num"));
				bookVO.setTitle(rs.getString("title"));
				bookVO.setAuthor(rs.getString("author"));
				bookVO.setPublisher(rs.getString("publisher"));
				bookVO.setBookDate(rs.getString("book_date"));
				bookVO.setIsbn(rs.getString("isbn"));

				list.add(bookVO);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(con, pstmt, rs);
		}
		return list;
	} // search

	public void insertBook(BookVO bookVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = getConnection();

			String sql = "";
			sql += "INSERT INTO book (num, title, author, publisher, book_date, isbn) ";
			sql += "VALUES (seq_book.nextval, ?, ?, ?, ?, ?) ";

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, bookVO.getTitle());
			pstmt.setString(2, bookVO.getAuthor());
			pstmt.setString(3, bookVO.getPublisher());
			pstmt.setString(4, bookVO.getBookDate());
			pstmt.setString(5, bookVO.getIsbn());

			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(con, pstmt);
		}
	} // insertBook

	public void updateBook(BookVO bookVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = getConnection();

			String sql = "";
			sql += "UPDATE book ";
			sql += "SET title = ?, author = ?, publisher = ?, book_date = ?, isbn = ? ";
			sql += "WHERE num = ? ";

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, bookVO.getTitle());
			pstmt.setString(2, bookVO.getAuthor());
			pstmt.setString(3, bookVO.getPublisher());
			pstmt.setString(4, bookVO.getBookDate());
			pstmt.setString(5, bookVO.getIsbn());
			pstmt.setInt(6, bookVO.getNum());

			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(con, pstmt);
		}
	} // updateBook

	public int removeBook(int num) {
		int count = 0;

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = getConnection();

			String sql = "DELETE FROM book WHERE num = ? ";

			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);

			count = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(con, pstmt);
		}
		return count;
	} // removeBook

} // BookDAO
