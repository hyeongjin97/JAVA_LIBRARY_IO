package ac.kr.kopo.library.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MyPage { // 마이페이지 담당 클래스

	private Scanner sc = new Scanner(System.in);

	public void viewMyPage(String loginID) { // 회원 마이페이지 화면을 출력하는 메소드

		System.out.println("=====================마이페이지=======================");
		System.out.println("1. 내 정보 수정 2. 대여중인 목록 3.메인페이지로 이동 4.회원탈퇴");
		System.out.print("원하는 메뉴를 선택하세요 : ");
		String num = sc.nextLine();
		switch (num) {
		case "1":
			updateMyPage(loginID);
			break;
		case "2":
			showRnetBook(loginID);
			viewMyPage(loginID);
			break;
		case "3":
			break;
		case "4":
			withdraw(loginID);
			LibraryMain.main(null);
		default:
			System.out.println("메뉴에 있는 번호를 입력하세요.");
			viewMyPage(loginID);
		}

	}

	public void updateMyPage(String loginID) { // 정보 수정 페이지를 보여주는 메소드
		showUserInfo(loginID);
		System.out.println("------------내 정보 수정------------");
		System.out.println("1. 이름 수정 2. 비밀번호 수정 3. 전화번호 수정");
		System.out.print("원하는 메뉴를 선택하세요 : ");
		String num = sc.nextLine();
		switch (num) {
		case "1":
			updateName(loginID);
			viewMyPage(loginID);
			break;
		case "2":
			updatePwd(loginID);
			viewMyPage(loginID);
			break;
		case "3":
			updatePhoneNum(loginID);
			viewMyPage(loginID);
			break;
		default:
			System.out.println("메뉴에 있는 번호를 입력하세요.");
			viewMyPage(loginID);
			break;

		}
	}

	public void showUserInfo(String loginID) { // 사용자 회원 정보를 보여주는 메소드
		try {
			Login lg = new Login();
			String fileName = "librarydata/UserInfo.txt";
			FileInputStream fis = new FileInputStream(fileName);
			BufferedInputStream bis = new BufferedInputStream(fis);
			ObjectInputStream in = new ObjectInputStream(bis);
			Map<String, Member> userMap = (HashMap<String, Member>) in.readObject();
			String[] str = userMap.get(loginID).toString().split(",");
			System.out.println("--------------" + str[2] + "님의 정보----------");
			System.out.println("ID : " + str[0] + ", 비밀번호 : " + str[1] + ", 이름 : " + str[2] + ", 전화번호 : " + str[3]);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void updateName(String loginID) { // 사용자 이름을 수정하는 메소드
		try {
			Login lg = new Login();
			String fileName = "librarydata/UserInfo.txt";
			FileInputStream fis = new FileInputStream(fileName);
			BufferedInputStream bis = new BufferedInputStream(fis);
			ObjectInputStream in = new ObjectInputStream(bis);

			Map<String, Member> userMap = (HashMap<String, Member>) in.readObject();
			String[] str = userMap.get(loginID).toString().split(",");

			System.out.println("현재 이름 : " + str[2]);
			System.out.print("바꿀 이름을 입력하세요 :");
			String newName = sc.nextLine();
			userMap.put(loginID, new Member(str[0], str[1], newName, str[3]));

			FileOutputStream fos = new FileOutputStream(fileName);
			BufferedOutputStream bos = new BufferedOutputStream(fos);

			ObjectOutputStream out = new ObjectOutputStream(bos);

			out.writeObject(userMap);
			out.close();

			System.out.println("이름이 " + newName + " 으로 변경되었습니다.");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updatePwd(String loginID) { // 사용자 비밀번호를 수정하는 메소드

		try {
			Login lg = new Login();
			String fileName = "librarydata/UserInfo.txt";
			FileInputStream fis = new FileInputStream(fileName);
			BufferedInputStream bis = new BufferedInputStream(fis);
			ObjectInputStream in = new ObjectInputStream(bis);

			Map<String, Member> userMap = (HashMap<String, Member>) in.readObject();

			String[] str = userMap.get(loginID).toString().split(",");
			System.out.println("현재 비밀번호 : " + str[1]);
			System.out.print("바꿀 비밀번호를 입력하세요 :");
			String newPwd = sc.nextLine();
			System.out.print("한번 더 입력하세요 : ");
			String newPwd2 = sc.nextLine();
			if (newPwd.equals(newPwd2)) {

				userMap.put(loginID, new Member(str[0], newPwd, str[2], str[3]));

				FileOutputStream fos = new FileOutputStream(fileName);
				BufferedOutputStream bos = new BufferedOutputStream(fos);

				ObjectOutputStream out = new ObjectOutputStream(bos);

				out.writeObject(userMap);
				out.close();

				System.out.println("비밀번호가 " + newPwd + " 로 변경되었습니다.");
			} else {
				System.out.println("비밀번호가 일치하지 않습니다.");
				updatePwd(loginID);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void updatePhoneNum(String loginID) { // 사용자 핸드폰 번호를 수정하는 메소드
		try {
			Login lg = new Login();
			String fileName = "librarydata/UserInfo.txt";
			FileInputStream fis = new FileInputStream(fileName);
			BufferedInputStream bis = new BufferedInputStream(fis);
			ObjectInputStream in = new ObjectInputStream(bis);

			Map<String, Member> userMap = (HashMap<String, Member>) in.readObject();

			String[] str = userMap.get(loginID).toString().split(",");
			System.out.println("현재 전화번호 : " + str[3]);
			System.out.print("바꿀 전화번호를 입력하세요 :");
			String newPhoneNum = sc.nextLine();

			userMap.put(loginID, new Member(str[0], str[1], str[2], newPhoneNum));

			FileOutputStream fos = new FileOutputStream(fileName);
			BufferedOutputStream bos = new BufferedOutputStream(fos);

			ObjectOutputStream out = new ObjectOutputStream(bos);

			out.writeObject(userMap);
			out.close();

			System.out.println("전화번호가 " + newPhoneNum + " 로 변경되었습니다.");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showRnetBook(String str) { // 사용자가 대여한 책 목록를 보여주는 메소드
		try {
			String fileName2 = "librarydata/UserRentList.txt";
			FileInputStream fis = new FileInputStream(fileName2);
			BufferedInputStream bis = new BufferedInputStream(fis);
			ObjectInputStream in = new ObjectInputStream(bis);

			List<UserRent> userRentList = (ArrayList<UserRent>) in.readObject();
			Object[] arr = userRentList.toArray();
			int cnt = 0;
			for (int i = 0; i < arr.length; i++) {
				if (arr[i] != null) {
					String[] str1 = arr[i].toString().split(",");
					if (str1[0].equals(str)) {

						System.out.println("대여자 :" + str1[0] + ", 책 아이디: " + str1[1] + ", 책 이름: " + str1[2] + ", 작가: "
								+ str1[3] + ", 대여일자: " + str1[4] + ", 반납일자: " + str1[5]);
						cnt++;
					}
				}
			}
			if (cnt == 0) {
				System.out.println("현재 대여중인 책이 없습니다.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void withdraw(String loginID) { // 회원 탈퇴 기능 메소드
		showUserInfo(loginID);
		System.out.println("탈퇴하시겠습니까?(Y/N)");
		String confirm = sc.nextLine();
		switch (confirm) {
		case "Y":

			try {
				Login lg = new Login();
				String fileName = "librarydata/UserInfo.txt";
				FileInputStream fis = new FileInputStream(fileName);
				BufferedInputStream bis = new BufferedInputStream(fis);
				ObjectInputStream in = new ObjectInputStream(bis);

				Map<String, Member> userMap = (HashMap<String, Member>) in.readObject();
				userMap.remove(loginID);

				FileOutputStream fos = new FileOutputStream(fileName);
				BufferedOutputStream bos = new BufferedOutputStream(fos);

				ObjectOutputStream out = new ObjectOutputStream(bos);

				out.writeObject(userMap);
				out.close();
				System.out.println("탈퇴되었습니다.");

			} catch (Exception e) {
				e.printStackTrace();
			}

			break;
		case "N":
			System.out.println("취소되었습니다.");
			viewMyPage(loginID);
			break;
		default:
			System.out.println("Y 또는 N을 입력해주세요.");
			withdraw(loginID);
		}
	}

}
