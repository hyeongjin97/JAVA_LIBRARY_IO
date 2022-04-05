package ac.kr.kopo.library.io;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Login extends Register{

	 /**
	 * 
	 */
	private String loginID;
	private String loginPwd;
	private Scanner sc = new Scanner(System.in);
	

	public Login() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Login(String userID, String userPwd, String userName, String userPhoneNum) {
		super(userID, userPwd, userName, userPhoneNum);
		// TODO Auto-generated constructor stub
	}

	public void loginStart() {

		try {
			
			String fileName = "librarydata/UserInfo.txt";
			FileInputStream fis = new FileInputStream(fileName);
			BufferedInputStream bis = new BufferedInputStream(fis);
			ObjectInputStream in = new ObjectInputStream(bis);
			
			Map<String, Member> memberMap = (HashMap<String, Member>) in.readObject();

			System.out.print("ID를 입력해주세요 : ");
			loginID = sc.nextLine();
			if(memberMap.containsKey(loginID)) {
				String[] str = memberMap.get(loginID).toString().split(",");
				System.out.print("비밀번호를 입력해주세요 : ");
				loginPwd = sc.nextLine();
				String str1 = " userPwd="+loginPwd;
				System.out.println(str1+str[1]);
				if(str[1].equals(str1)) {
					System.out.println("로그인 성공");
				}
				
			}else {
				System.out.println("입력하신 아이디가 존재하지 않습니다.");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getLoginID() {
		return loginID;
	}

	public void setLoginID(String loginID) {
		this.loginID = loginID;
	}

}
