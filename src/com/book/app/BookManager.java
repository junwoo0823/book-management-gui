package com.book.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.book.domain.BookVO;
import com.book.repository.BookDAO;

public class BookManager extends JFrame {

	private BookDAO bookDAO = new BookDAO();

	private Vector<String> columnNames = new Vector<>();

	private JTabbedPane tabbedPane;
	private JPanel pnSearch;
	private JPanel pnInput;
	private JLabel bookImage;
	private JTextField tfSearch;
	private JSplitPane splitPane;
	private JPanel panelLeft;
	private JLabel lblTitle;
	private JTextField tfTitle;
	private JLabel lblAuthor;
	private JTextField tfAuthor;
	private JLabel lblPublisher;
	private JTextField tfPublisher;
	private JLabel lblBookDate;
	private JTextField tfBookDate;
	private JLabel lblIsbn;
	private JTextField tfIsbn;
	private JButton btnAdd;
	private JButton btnRemove;
	private JButton btnModify;
	private JLabel lblNum;
	private JTextField tfNum;
	private JLabel informImage;
	private JLabel informText;
	private JLabel lblNewLabel;
	private JButton btnTotal;
	private JPanel panelRight;
	private JScrollPane scrollPane;
	private JTable table;
	private JComboBox comboBox;

	public static void main(String[] args) {
		new BookManager();
	} // main

	public BookManager() {
		super("도서 관리 프로그램");
		getContentPane().setFont(new Font("맑은 고딕", Font.PLAIN, 16));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setForeground(Color.WHITE);
		setBackground(Color.DARK_GRAY);
		setFont(new Font("맑은 고딕", Font.PLAIN, 16));
		setLocationByPlatform(true);
		setSize(610, 450);
		setIconImage(Toolkit.getDefaultToolkit().getImage(BookManager.class.getResource("/images/bookIcon.png")));
		getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
		getContentPane().add(getTabbedPane());

		// 목록 이름
		columnNames.add("번호");
		columnNames.add("제목");
		columnNames.add("저자");
		columnNames.add("출판사");
		columnNames.add("출판일");
		columnNames.add("ISBN");

		setVisible(true);
	}

	private JTabbedPane getTabbedPane() {
		if (tabbedPane == null) {
			tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
			tabbedPane.setFont(new Font("맑은 고딕", Font.BOLD, 14));
			tabbedPane.setForeground(Color.WHITE);
			tabbedPane.setBackground(Color.BLACK);
			tabbedPane.addTab("도서 검색", null, getPnSearch(), null);
			tabbedPane.addTab("도서 등록", null, getPnInput(), null);
		}
		return tabbedPane;
	}

	private JPanel getPnSearch() {
		if (pnSearch == null) {
			pnSearch = new JPanel();
			pnSearch.setBackground(SystemColor.activeCaptionBorder);
			pnSearch.setLayout(null);
			pnSearch.add(getComboBox());
			pnSearch.add(getTfSearch());
			pnSearch.add(getBookImage());
			pnSearch.add(getInformImage());
			pnSearch.add(getInformText());
			pnSearch.add(getLblNewLabel());
		}
		return pnSearch;
	}

	private JComboBox getComboBox() {
		if (comboBox == null) {
			comboBox = new JComboBox();
			comboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			comboBox.setFont(new Font("맑은 고딕", Font.BOLD, 13));
			comboBox.setModel(new DefaultComboBoxModel(new String[] { "<선택>", "제목", "저자", "출판사" }));
			comboBox.setForeground(Color.WHITE);
			comboBox.setBackground(Color.BLACK);
			comboBox.setBounds(222, 84, 70, 30);
		}
		return comboBox;
	}

	private JLabel getBookImage() {
		if (bookImage == null) {
			bookImage = new JLabel("");
			bookImage.setBounds(48, 12, 185, 245);
			bookImage.setIcon(new ImageIcon(BookManager.class.getResource("/images/books.png")));
		}
		return bookImage;
	}

	private JLabel getInformImage() {
		if (informImage == null) {
			informImage = new JLabel("");
			informImage.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
			informImage.setIcon(new ImageIcon(BookManager.class.getResource("/images/rightImage.png")));
			informImage.setBounds(308, 64, 269, 144);
		}
		return informImage;
	}

	private JLabel getInformText() {
		if (informText == null) {
			informText = new JLabel(
					"<HTML><body style='text-align:center;'>반가워요!<br>도서 관리<br>프로그램입니다.<br>어떤 책을<br>찾으시나요?</body></HTML>");
			informText.setHorizontalAlignment(SwingConstants.CENTER);
			informText.setFont(new Font("굴림체", Font.BOLD, 13));
			informText.setBounds(308, 62, 138, 142);
		}
		return informText;
	}

	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel(
					"<HTML><body><b>《사용법》</b><br>1. 찾고 있는 것을 선택 박스에서 클릭하세요.<br>2. 찾고 있는 일부 단어를 위에 있는 책의 제목 부분에 입력하세요.<br>3. 단어를 입력했다면 [ENTER]키를 눌러주세요.<br>4. 만약 입력되어 있지 않은 책이라면 직접 책을 등록해주세요.</body></HTML>");
			lblNewLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
			lblNewLabel.setBounds(60, 267, 450, 100);
		}
		return lblNewLabel;
	}

	private JPanel getPnInput() {
		if (pnInput == null) {
			pnInput = new JPanel();
			pnInput.setLayout(new BorderLayout(0, 0));
			pnInput.add(getSplitPane(), BorderLayout.CENTER);
		}
		return pnInput;
	}

	private JTextField getTfSearch() {
		if (tfSearch == null) {
			tfSearch = new JTextField();
			tfSearch.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					int key = e.getKeyCode();

					if (key == KeyEvent.VK_ENTER) {

						int selectedIndex = comboBox.getSelectedIndex();
						if (selectedIndex == 0) {
							JOptionPane.showMessageDialog(BookManager.this, "검색항목을 선택하세요", "검색 에러",
									JOptionPane.ERROR_MESSAGE);
							return;
						}

						String field = "";
						switch (selectedIndex) {
						case 1:
							field = "title";
							break;
						case 2:
							field = "author";
							break;
						case 3:
							field = "publisher";
							break;
						}

						String word = tfSearch.getText();
						if (word.trim().equals("")) {
							JOptionPane.showMessageDialog(BookManager.this, "검색어를 입력하세요", "검색 에러",
									JOptionPane.ERROR_MESSAGE);
							return;
						}

						List<BookVO> list = bookDAO.search(field, word); //

						if (list.size() > 0) {
							JOptionPane.showMessageDialog(BookManager.this, "검색에 성공했습니다!\n검색 결과를 보러 가겠습니까?", "검색 성공",
									JOptionPane.INFORMATION_MESSAGE);

							Vector<Vector<Object>> vector = getVectorFromList(list);

							table = new JTable(vector, columnNames);
							scrollPane.setViewportView(table);
							setVisible(true);

							tabbedPane.setSelectedIndex(1);

						} else {
							JOptionPane.showMessageDialog(BookManager.this,
									"검색에 실패했습니다…\n정보를 직접 등록해주세요.\n단! 번호는 입력하지 마세요.", "검색 실패",
									JOptionPane.ERROR_MESSAGE);

							tabbedPane.setSelectedIndex(1);
						}

					}

				}
			});
			tfSearch.setFont(new Font("굴림체", Font.BOLD, 16));
			tfSearch.setOpaque(false);
			tfSearch.setBounds(60, 80, 150, 40);
			tfSearch.setHorizontalAlignment(SwingConstants.CENTER);
			tfSearch.setForeground(Color.WHITE);
			tfSearch.setColumns(10);
		}
		return tfSearch;
	}

	private JSplitPane getSplitPane() {
		if (splitPane == null) {
			splitPane = new JSplitPane();
			splitPane.setDividerSize(0);
			splitPane.setEnabled(false);
			splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			splitPane.setLeftComponent(getPanelLeft());
			splitPane.setRightComponent(getPanelRight());
			splitPane.setDividerLocation(135);
		}
		return splitPane;
	}

	private JPanel getPanelLeft() {
		if (panelLeft == null) {
			panelLeft = new JPanel();
			panelLeft.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			panelLeft.setBackground(SystemColor.activeCaptionBorder);
			panelLeft.setLayout(null);
			panelLeft.add(getLblTitle());
			panelLeft.add(getTfTitle());
			panelLeft.add(getLblAuthor());
			panelLeft.add(getTfAuthor());
			panelLeft.add(getLblPublisher());
			panelLeft.add(getTfPublisher());
			panelLeft.add(getLblBookDate());
			panelLeft.add(getTfBookDate());
			panelLeft.add(getLblIsbn());
			panelLeft.add(getTfIsbn());
			panelLeft.add(getBtnAdd());
			panelLeft.add(getBtnRemove());
			panelLeft.add(getBtnModify());
			panelLeft.add(getLblNum());
			panelLeft.add(getTfNum());
			panelLeft.add(getBtnTotal());
		}
		return panelLeft;
	}

	private JLabel getLblTitle() {
		if (lblTitle == null) {
			lblTitle = new JLabel("제목");
			lblTitle.setBounds(12, 9, 40, 24);
			lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
			lblTitle.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		}
		return lblTitle;
	}

	private JTextField getTfTitle() {
		if (tfTitle == null) {
			tfTitle = new JTextField();
			tfTitle.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
			tfTitle.setBounds(57, 12, 310, 21);
			tfTitle.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
			tfTitle.setColumns(10);
		}
		return tfTitle;
	}

	private JLabel getLblAuthor() {
		if (lblAuthor == null) {
			lblAuthor = new JLabel("저자");
			lblAuthor.setBounds(395, 9, 40, 24);
			lblAuthor.setHorizontalAlignment(SwingConstants.CENTER);
			lblAuthor.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		}
		return lblAuthor;
	}

	private JTextField getTfAuthor() {
		if (tfAuthor == null) {
			tfAuthor = new JTextField();
			tfAuthor.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
			tfAuthor.setBounds(440, 12, 120, 21);
			tfAuthor.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
			tfAuthor.setColumns(10);
		}
		return tfAuthor;
	}

	private JLabel getLblPublisher() {
		if (lblPublisher == null) {
			lblPublisher = new JLabel("출판사");
			lblPublisher.setBounds(12, 44, 41, 24);
			lblPublisher.setHorizontalAlignment(SwingConstants.CENTER);
			lblPublisher.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		}
		return lblPublisher;
	}

	private JTextField getTfPublisher() {
		if (tfPublisher == null) {
			tfPublisher = new JTextField();
			tfPublisher.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
			tfPublisher.setBounds(57, 47, 100, 21);
			tfPublisher.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
			tfPublisher.setColumns(10);
		}
		return tfPublisher;
	}

	private JLabel getLblBookDate() {
		if (lblBookDate == null) {
			lblBookDate = new JLabel("출판일");
			lblBookDate.setHorizontalAlignment(SwingConstants.CENTER);
			lblBookDate.setFont(new Font("맑은 고딕", Font.BOLD, 12));
			lblBookDate.setBounds(175, 44, 40, 24);
		}
		return lblBookDate;
	}

	private JTextField getTfBookDate() {
		if (tfBookDate == null) {
			tfBookDate = new JTextField();
			tfBookDate.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
			tfBookDate.setColumns(10);
			tfBookDate.setBounds(220, 47, 100, 21);
		}
		return tfBookDate;
	}

	private JLabel getLblIsbn() {
		if (lblIsbn == null) {
			lblIsbn = new JLabel("ISBN");
			lblIsbn.setBounds(335, 44, 40, 24);
			lblIsbn.setHorizontalAlignment(SwingConstants.CENTER);
			lblIsbn.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		}
		return lblIsbn;
	}

	private JTextField getTfIsbn() {
		if (tfIsbn == null) {
			tfIsbn = new JTextField();
			tfIsbn.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
			tfIsbn.setBounds(380, 47, 100, 21);
			tfIsbn.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
			tfIsbn.setColumns(10);
		}
		return tfIsbn;
	}

	private JButton getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new JButton("등록");
			btnAdd.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			btnAdd.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String title = tfTitle.getText();
					String author = tfAuthor.getText();
					String publisher = tfPublisher.getText();
					String bookDate = tfBookDate.getText();
					String isbn = tfIsbn.getText();
					if (title.length() == 0 || author.length() == 0 || publisher.length() == 0 || bookDate.length() == 0
							|| isbn.length() == 0) {
						JOptionPane.showMessageDialog(BookManager.this, "입력하지 않는 곳이 있습니다.\n다시 확인 후 버튼을 눌러주세요.", "입력 에러",
								JOptionPane.ERROR_MESSAGE);

						tfTitle.requestFocus();
						return;
					}

					BookVO bookVO = new BookVO();
					bookVO.setTitle(tfTitle.getText());
					bookVO.setAuthor(tfAuthor.getText());
					bookVO.setPublisher(tfPublisher.getText());
					bookVO.setBookDate(tfBookDate.getText());
					bookVO.setIsbn(tfIsbn.getText());

					bookDAO.insertBook(bookVO);

					JOptionPane.showMessageDialog(BookManager.this, "등록되었습니다.", "등록 성공",
							JOptionPane.INFORMATION_MESSAGE);

					tfTitle.setText("");
					tfAuthor.setText("");
					tfPublisher.setText("");
					tfBookDate.setText("");
					tfIsbn.setText("");
					tfNum.setText("");

					getBtnTotal().doClick();
				}
			});

			btnAdd.setBounds(27, 80, 150, 24);
			btnAdd.setForeground(Color.WHITE);
			btnAdd.setBackground(Color.DARK_GRAY);
			btnAdd.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		}
		return btnAdd;
	}

	private JButton getBtnModify() {
		if (btnModify == null) {
			btnModify = new JButton("수정");
			btnModify.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			new Cursor(Cursor.HAND_CURSOR);
			btnModify.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {

					if (tfNum.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "수정할 번호를 입력해주세요.");
						return;
					}
					String title = tfTitle.getText();
					String author = tfAuthor.getText();
					String publisher = tfPublisher.getText();
					String bookDate = tfBookDate.getText();
					String isbn = tfIsbn.getText();
					if (title.length() == 0 || author.length() == 0 || publisher.length() == 0 || bookDate.length() == 0
							|| isbn.length() == 0) {
						JOptionPane.showMessageDialog(BookManager.this, "입력하지 않는 곳이 있습니다.\n다시 확인 후 버튼을 눌러주세요.", "입력 에러",
								JOptionPane.ERROR_MESSAGE);

						tfTitle.requestFocus();
						return;
					}

					int num = Integer.parseInt(tfNum.getText());
					int result = JOptionPane.showConfirmDialog(btnModify, "No." + num + "을 수정하겠습니까?", "수정",
							JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

					switch (result) {
					case JOptionPane.CLOSED_OPTION:
					case JOptionPane.NO_OPTION:
						return;
					}

					BookVO bookVO = new BookVO();
					bookVO.setNum(num);
					bookVO.setTitle(tfTitle.getText());
					bookVO.setAuthor(tfAuthor.getText());
					bookVO.setPublisher(tfPublisher.getText());
					bookVO.setBookDate(tfBookDate.getText());
					bookVO.setIsbn(tfIsbn.getText());

					bookDAO.updateBook(bookVO);

					JOptionPane.showMessageDialog(BookManager.this, "수정되었습니다.", "수정 성공",
							JOptionPane.INFORMATION_MESSAGE);

					tfTitle.setText("");
					tfAuthor.setText("");
					tfPublisher.setText("");
					tfBookDate.setText("");
					tfIsbn.setText("");
					tfNum.setText("");

					getBtnTotal().doClick();
				}
			});

			btnModify.setBounds(220, 80, 150, 24);
			btnModify.setForeground(Color.WHITE);
			btnModify.setBackground(Color.DARK_GRAY);
			btnModify.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		}
		return btnModify;
	}

	private JButton getBtnRemove() {
		if (btnRemove == null) {
			btnRemove = new JButton("삭제");
			btnRemove.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			btnRemove.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {

					if (tfNum.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "삭제할 번호를 입력해주세요.");
						return;
					}

					int num = Integer.parseInt(tfNum.getText());
					int result = JOptionPane.showConfirmDialog(btnRemove, "No." + num + "을 삭제하겠습니까?", "삭제",
							JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

					switch (result) {
					case JOptionPane.CLOSED_OPTION:
					case JOptionPane.NO_OPTION:
						return;
					}

					bookDAO.removeBook(num);

					JOptionPane.showMessageDialog(BookManager.this, "삭제되었습니다.", "삭제 성공",
							JOptionPane.INFORMATION_MESSAGE);

					tfTitle.setText("");
					tfAuthor.setText("");
					tfPublisher.setText("");
					tfBookDate.setText("");
					tfIsbn.setText("");
					tfNum.setText("");

					getBtnTotal().doClick();
				}
			});

			btnRemove.setBounds(410, 80, 150, 24);
			btnRemove.setForeground(Color.WHITE);
			btnRemove.setBackground(Color.DARK_GRAY);
			btnRemove.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		}
		return btnRemove;
	}

	private JLabel getLblNum() {
		if (lblNum == null) {
			lblNum = new JLabel("번호");
			lblNum.setHorizontalAlignment(SwingConstants.CENTER);
			lblNum.setFont(new Font("맑은 고딕", Font.BOLD, 12));
			lblNum.setBounds(495, 44, 30, 24);
		}
		return lblNum;
	}

	private JTextField getTfNum() {
		if (tfNum == null) {
			tfNum = new JTextField();
			tfNum.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
			tfNum.setHorizontalAlignment(SwingConstants.CENTER);
			tfNum.setBackground(SystemColor.activeCaptionBorder);
			tfNum.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
			tfNum.setColumns(10);
			tfNum.setBounds(530, 47, 30, 21);
		}
		return tfNum;
	}

	private JButton getBtnTotal() {
		if (btnTotal == null) {
			btnTotal = new JButton("목록 보기");
			btnTotal.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			btnTotal.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					List<BookVO> list = bookDAO.getBooks();

					Vector<Vector<Object>> vector = getVectorFromList(list);

					table = new JTable(vector, columnNames);

					scrollPane.setViewportView(getTable());

					setVisible(true);
				}
			});
			btnTotal.setFont(new Font("맑은 고딕", Font.BOLD, 13));
			btnTotal.setForeground(Color.WHITE);
			btnTotal.setBackground(Color.DARK_GRAY);
			btnTotal.setBounds(0, 110, 587, 24);
		}
		return btnTotal;
	}

	private Vector<Vector<Object>> getVectorFromList(List<BookVO> list) {

		Vector<Vector<Object>> vector = new Vector<>();

		for (BookVO book : list) {
			Vector<Object> rowVector = new Vector<>();
			rowVector.add(book.getNum());
			rowVector.add(book.getTitle());
			rowVector.add(book.getAuthor());
			rowVector.add(book.getPublisher());
			rowVector.add(book.getBookDate());
			rowVector.add(book.getIsbn());

			vector.add(rowVector);
		}

		return vector;
	}

	private JPanel getPanelRight() {
		if (panelRight == null) {
			panelRight = new JPanel();
			panelRight.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
			panelRight.setLayout(new BorderLayout(0, 0));
			panelRight.add(getScrollPane(), BorderLayout.CENTER);
		}
		return panelRight;
	}

	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
			scrollPane.setViewportView(getTable());
		}
		return scrollPane;
	}

	private JTable getTable() {
		if (table == null) {
			table = new JTable();
			table.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		}
		return table;
	}

}
