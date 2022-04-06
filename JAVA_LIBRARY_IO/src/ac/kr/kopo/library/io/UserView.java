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

public class UserView {

	private Scanner sc = new Scanner(System.in);

	public void printView() {
		System.out.println("원하시는 메뉴를 선택하세요 : ");
		System.out.println("1. 책 목록보기 2. 책 대여하기 3. 책 반납하기 4. 마이페이지 5. 로그아웃 ");
		String num = sc.nextLine();
		switch (num) {
		case "1":
			showbookList();
			break;
		case "2":
			rentBook();
			break;
		case "3":
			break;
		case "4":
			break;
		case "5":
			break;
		default:
			System.out.println("메뉴에 있는 번호를 선택하세요.");
		}
	}

	public void showbookList() {
		try {

			String fileName = "librarydata/bookInfo.txt";
			FileInputStream fis = new FileInputStream(fileName);
			BufferedInputStream bis = new BufferedInputStream(fis);
			ObjectInputStream in = new ObjectInputStream(bis);

			Map<String, Book> bookMap = (HashMap<String, Book>) in.readObject();
			
			

			
			
			Object[] arr = bookMap.values().toArray();

			for (Object ar : arr) {
				System.out.println(ar.toString());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void rentBook() {
		
		try {
			Login lg = new Login();
			String fileName = "librarydata/bookInfo.txt";
			FileInputStream fis = new FileInputStream(fileName);
			BufferedInputStream bis = new BufferedInputStream(fis);
			ObjectInputStream in = new ObjectInputStream(bis);

			Map<String, Book> bookMap = (HashMap<String, Book>) in.readObject();

			Object[] arr = bookMap.values().toArray();
			for (Object ar : arr) {
				System.out.println(ar.toString());
			}

			System.out.print("대여하고 싶은 책 ID를 입력하세요 : ");
			String bookID = sc.nextLine();
			if (bookMap.containsKey(bookID)) {

				String[] str1 = bookMap.get(bookID).toString().split(",");
				System.out.println("책 ID : " + str1[0] + ", 책 이름 : " + str1[1] + ", 작가 : " + str1[2] + ", 수량 : " + str1[3]);
				System.out.println("해당 정보의 책을 빌리시겠습니까?(Y/N)");
				String confirm = sc.nextLine();
				switch (confirm) {
				case "Y":
					String[] str = bookMap.get(bookID).toString().split(",");
					Integer count = Integer.parseInt(str[3]) - 1;
					bookMap.put(bookID, new Book(str[0], str[1], str[2], count.toString()));
					System.out.println("대여가 완료되었습니다.");

					FileOutputStream fos = new FileOutputStream(fileName);
					BufferedOutputStream bos = new BufferedOutputStream(fos);

					ObjectOutputStream out = new ObjectOutputStream(bos);

					out.writeObject(bookMap);
					out.close();
					// 유저 렌트 맵에 추가
					UserRent ur = new UserRent(lg.getLoginID(),str[0],str[1],str[2],"a","b");
					Map<String,UserRent> userRentMap = new HashMap<>();
					String fileName1 = "librarydata/UserRentBook.txt";
					
					
					
					printView();
					break;
				case "N":
					System.out.println("대여가 취소되었습니다.");
					printView();
					break;
				default:
					System.out.println("Y 또는 N 키를 입력해주세요");
					rentBook();
					break;

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void returnBook() {
		try {

			String fileName = "librarydata/UserRentBook.txt";
			FileInputStream fis = new FileInputStream(fileName);
			BufferedInputStream bis = new BufferedInputStream(fis);
			ObjectInputStream in = new ObjectInputStream(bis);

			Map<String, Book> bookMap = (HashMap<String, Book>) in.readObject();

			Object[] arr = bookMap.values().toArray();
			for (Object ar : arr) {
				System.out.println(ar.toString());
			}
			
			

			System.out.print("반납하고 싶은 책 ID를 입력하세요 : ");
			String bookID = sc.nextLine();
			if (bookMap.containsKey(bookID)) {

				String[] str1 = bookMap.get(bookID).toString().split(",");
				System.out.println("책 ID : " + str1[0] + ", 책 이름 : " + str1[1] + ", 작가 : " + str1[2] + ", 수량 : " + str1[3]);
				System.out.println("해당 정보의 책을 빌리시겠습니까?(Y/N)");
				String confirm = sc.nextLine();
				switch (confirm) {
				case "Y":
					String[] str = bookMap.get(bookID).toString().split(",");
					Integer count = Integer.parseInt(str[3]) - 1;
					bookMap.put(bookID, new Book(str[0], str[1], str[2], count.toString()));
					System.out.println("대여가 완료되었습니다.");

					FileOutputStream fos = new FileOutputStream(fileName);
					BufferedOutputStream bos = new BufferedOutputStream(fos);

					ObjectOutputStream out = new ObjectOutputStream(bos);

					out.writeObject(bookMap);
					out.close();
					printView();
					break;
				case "N":
					System.out.println("대여가 취소되었습니다.");
					printView();
					break;
				default:
					System.out.println("Y 또는 N 키를 입력해주세요");
					rentBook();
					break;

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
