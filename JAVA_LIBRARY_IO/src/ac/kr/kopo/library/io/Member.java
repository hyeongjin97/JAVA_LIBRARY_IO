package ac.kr.kopo.library.io;

public class Member implements java.io.Serializable {  // 멤버 정보를 설정하는 클래스 

	private String userID;
	private String userPwd;
	private String userName;
	private String userPhoneNum;

	public Member() {

	}

	public Member(String userID, String userPwd, String userName, String userPhoneNum) {
		super();
		this.userID = userID;
		this.userPwd = userPwd;
		this.userName = userName;
		this.userPhoneNum = userPhoneNum;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	@Override
	public String toString() {
		return userID + "," + userPwd + "," + userName + "," + userPhoneNum;
	}

}
