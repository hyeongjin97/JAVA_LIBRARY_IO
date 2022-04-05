package ac.kr.kopo.library.io;

public class userRent {
	
	private String userID;
	private String bookID;
	private String bookName;
	private String bookWriter;
	private String rentDate;
	private String returnDate;
	
	public userRent() {
		
	}
	
	public userRent(String userID, String bookID, String bookName, String bookWriter, String rentDate,
			String returnDate) {
		super();
		this.userID = userID;
		this.bookID = bookID;
		this.bookName = bookName;
		this.bookWriter = bookWriter;
		this.rentDate = rentDate;
		this.returnDate = returnDate;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getBookID() {
		return bookID;
	}

	public void setBookID(String bookID) {
		this.bookID = bookID;
	}

	@Override
	public String toString() {
		return "userRent [userID=" + userID + ", bookID=" + bookID + ", bookName=" + bookName + ", bookWriter="
				+ bookWriter + ", rentDate=" + rentDate + ", returnDate=" + returnDate + "]";
	}
	
	
	
}
