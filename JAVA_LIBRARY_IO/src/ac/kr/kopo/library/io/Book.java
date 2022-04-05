package ac.kr.kopo.library.io;

public class Book {
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

	
	@Override
	public String toString() {
		return "Book [bookID=" + bookID + ", bookName=" + bookName + ", bookWirter=" + bookWirter + ", bookCount="
				+ bookCount + "]";
	}
	
}
