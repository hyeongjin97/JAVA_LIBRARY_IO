package ac.kr.kopo.library.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Register extends Member {  // 회원가입 클래스 

	private String userID;
	private String userPwd;
	private String userName;
	private String userPhoneNum;

	private Scanner sc = new Scanner(System.in);

	Member m = null;

	public Register() {
		super();
	}

	public Register(String userID, String userPwd, String userName, String userPhoneNum) {
		super(userID, userPwd, userName, userPhoneNum);
	}

	public void processRegister() { // 회원가입을 기능을 담당하는 메소드 

		try {
			String fileName = "librarydata/UserInfo.txt";
			FileInputStream fis = new FileInputStream(fileName);
			BufferedInputStream bis = new BufferedInputStream(fis);

			ObjectInputStream in = new ObjectInputStream(bis);

			Map<String, Member> memberMap = (HashMap<String, Member>) in.readObject();

			System.out.print("ID를 입력하세요 :");
			userID = sc.nextLine();
			if (!memberMap.containsKey(userID)) {
				loop: while (true) {
					System.out.print("비밀번호를 입력하세요 :");
					userPwd = sc.nextLine();
					System.out.print("비밀번호를 한 번더 입력해주세요 : ");
					String sndPwd = sc.nextLine();
					if (userPwd.equals(sndPwd)) {

						System.out.print("이름을 입력하세요 :");
						userName = sc.nextLine();
						System.out.print("전화번호를 입력하세요 :");
						userPhoneNum = sc.nextLine();
						m = new Member(userID, userPwd, userName, userPhoneNum);

						memberMap.put(userID, m);

						memberSerialize(memberMap);
						break loop;
					} else {
						System.out.println("비밀번호가 일치하지 않습니다.");
					}
				}

			} else {
				System.out.println("해당 아이디가 이미 존재합니다.");
				processRegister();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void memberSerialize(Map memberMap) {  // 회원가입한 멤버를 파일에 저장하는 메소드 
		try {
			String fileName = "librarydata/UserInfo.txt";
			FileOutputStream fos = new FileOutputStream(fileName);
			BufferedOutputStream bos = new BufferedOutputStream(fos);

			ObjectOutputStream out = new ObjectOutputStream(bos);

			System.out.println("회원가입이 완료되었습니다!.");
			out.writeObject(memberMap);
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
