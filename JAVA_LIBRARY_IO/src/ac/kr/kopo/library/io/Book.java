package ac.kr.kopo.library.io;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class Book implements java.io.Serializable {
	private String bookID;
	private String bookName;
	private String bookWirter;
	private String bookCount;

	public Book() {

	}

	public Book(String bookID, String bookName, String bookWirter, String bookCount) {
		super();
		this.bookID = bookID;
		this.bookName = bookName;
		this.bookWirter = bookWirter;
		this.bookCount = bookCount;
	}

	public String getBookID() {
		return bookID;
	}

	public void setBookID(String bookID) {
		this.bookID = bookID;
	}

	public void defaultBookInfo() {

		Map<String, Book> bookMap = new HashMap<>();

		bookMap.put("1", new Book("1", "자바의 정석", "남궁 성 ", "3"));
		bookMap.put("2", new Book("2", "이것이 자바다", "신용권", "1"));
		bookMap.put("3", new Book("3", "너의 췌장을 먹고 싶어", "스미노 요루", "4"));
		bookMap.put("4", new Book("4", "총 균 쇠", "재레드 다이아몬드", "0"));

		try {
			String fileName = "librarydata/bookInfo.txt";
			FileOutputStream fos = new FileOutputStream(fileName);
			BufferedOutputStream bos = new BufferedOutputStream(fos);

			ObjectOutputStream out = new ObjectOutputStream(bos);

			out.writeObject(bookMap);
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return bookID + "," + bookName + "," + bookWirter + "," + bookCount;
	}

}
