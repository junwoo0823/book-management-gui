package com.test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.book.domain.BookVO;
import com.book.repository.BookDAO;

// 테스트케이스 클래스
public class BookTest {

	// 픽스처(fixture)
	private BookDAO bookDAO;
	private BookVO bookVO1, bookVO2;

	@Before
	public void setUp() {
		System.out.print("테스트\t");

		bookDAO = new BookDAO();
		bookDAO.deleteAll();

		bookVO1 = new BookVO(bookDAO.num(), "혼자 공부하는 자바", "신용권", "한빛미디어", "2019-06-10", "9791162241875");
		bookVO2 = new BookVO(bookDAO.num(), "Java의 정석", "남궁성", "도우출판", "2016-01-28", "9788994492032");
	} // @Before

	@Test
	public void connectionTest() {
		System.out.print("DB접속?\t");
		// DB접속정보
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "myuser";
		String passwd = "1234";

		try {
			// 1단계. JDBC 드라이버 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 2단계. DB연결
			Connection con = DriverManager.getConnection(url, user, passwd);

			assertNotNull(con);

		} catch (Exception e) {
			e.printStackTrace();
		}
	} // DB접속 테스트 코드

	@Test
	public void insertTest() {
		System.out.print("INSERT?\t");

		bookDAO.insertBook(bookVO1);
		bookDAO.insertBook(bookVO2);

		assertEquals(2, bookDAO.getCountAll());
	} // INSERT 테스트 코드

	@Test
	public void viewTest() {
		System.out.print("SELECT?\t");

		List<BookVO> list = bookDAO.getBooks();
		assertEquals(0, list.size());

		BookVO bookVO = new BookVO();
		bookVO.setNum(bookDAO.num());
		bookVO.setTitle("Java의 정석");
		bookVO.setAuthor("남궁성");
		bookVO.setPublisher("도우출판");
		bookVO.setBookDate("2016-01-28");
		bookVO.setIsbn("9788994492032");

		bookDAO.insertBook(bookVO);

		list = bookDAO.getBooks();
		assertEquals(1, list.size());
	} // SELECT 테스트 코드

	@Test
	public void updateTest() {
		System.out.print("UPDATE?\t");

		bookDAO.insertBook(bookVO1);

		BookVO updateBook = new BookVO();
		updateBook.setNum(bookDAO.num());
		updateBook.setTitle("혼자 공부하는 자바");
		updateBook.setAuthor("신용권");
		updateBook.setPublisher("한빛미디어");
		updateBook.setBookDate("2019-06-10");
		updateBook.setIsbn("9791162241875");

		bookDAO.updateBook(updateBook);

		BookVO dbBook = bookDAO.getBookByNum(updateBook.getNum());

		assertEquals(updateBook.getNum(), dbBook.getNum());
	} // UPDATE 테스트 코드

	@Test
	public void deleteTest() {
		System.out.print("DELETE?\t");

		bookDAO.insertBook(bookVO1);
		assertEquals(1, bookDAO.getCountAll());

		bookDAO.removeBook(bookDAO.num());
		assertEquals(0, bookDAO.getCountAll());
	} // DELETE 테스트 코드

	@After
	public void tearDown() {
		System.out.println("성공!");
	} // @After
}