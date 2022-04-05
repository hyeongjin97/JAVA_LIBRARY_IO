package ac.kr.kopo.library.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Register extends Member {

	
	private String userID;
	private String userPwd;
	private String userName;
	private String userPhoneNum;

	private Scanner sc = new Scanner(System.in);


	Member m = null;

	public Register() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Register(String userID, String userPwd, String userName, String userPhoneNum) {
		super(userID, userPwd, userName, userPhoneNum);
		// TODO Auto-generated constructor stub
	}

	public void processRegister() {
		
		try {
			String fileName = "librarydata/UserInfo.txt";
			FileInputStream fis = new FileInputStream(fileName);
			BufferedInputStream bis = new BufferedInputStream(fis);
			
			ObjectInputStream in = new ObjectInputStream(bis);
			

			//ArrayList memberList = (ArrayList)in.readObject();
			Map<String,Member> memberMap = (HashMap<String,Member>)in.readObject();
		//	Map<String,Member> memberMap = new HashMap<>();
			
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

						Object[] arr = memberMap.values().toArray();
						for (Object arr1 : arr) {
							System.out.println(arr1.toString());
						}
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

	public void memberSerialize(Map memberMap) {
		try {
			String fileName = "librarydata/UserInfo.txt";
			FileOutputStream fos = new FileOutputStream(fileName);
			BufferedOutputStream bos = new BufferedOutputStream(fos);

			ObjectOutputStream out = new ObjectOutputStream(bos);

			System.out.println("회원정보가 저장되었습니다.");
			out.writeObject(memberMap);
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
//	public  void  memberReSerialize() {
//		try {
//			String fileName = "librarydata/UserInfo.txt";
//			FileInputStream fis = new FileInputStream(fileName);
//			BufferedInputStream bis = new BufferedInputStream(fis);
//			
//			ObjectInputStream in = new ObjectInputStream(bis);
//			
//
//			ArrayList memberList = (ArrayList)in.readObject();
//			
//			Map<String,Member> memberMap = new HashMap<>();
//			memberList.g
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		
//		
//	}

}


