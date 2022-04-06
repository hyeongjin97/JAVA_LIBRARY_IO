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

public class UserView implements java.io.Serializable {

	private Scanner sc = new Scanner(System.in);
	Login lg = new Login();
	ArrayList<UserRent> userRentList = new ArrayList<>();
	MyPage mp = new MyPage();

	
	public UserView() {
		
	}



	public void printView(String str, ArrayList<UserRent> userRentList) {

		if (str.equals("admin")) {
			while (true) {
				System.out.println("원하시는 메뉴를 선택하세요 : ");
				System.out.println("1. 책 추가하기 2. 책 정보 수정하기 3.회원관리");
				String num = sc.nextLine();
				switch (num) {
				case "1":
					break;
				case "2":
					break;
				case "3":
					break;
				}
			}

		} else {
			
				System.out.println("원하시는 메뉴를 선택하세요 : ");
				System.out.println("1. 책 목록보기 2. 책 대여하기 3. 책 반납하기 4. 마이페이지 5. 로그아웃 ");
				String num = sc.nextLine();
				switch (num) {
				case "1":
					showbookList();
					printView(str, userRentList);
					break;
				case "2":
					rentBook(str);
					printView(str, userRentList);
					break;
				case "3":
					returnBook(str);
					printView(str, userRentList);
					break;
				case "4":
					mp.viewMyPage(str);
					printView(str,userRentList);
					break;
				case "5":
					return;
				default:
					System.out.println("메뉴에 있는 번호를 선택하세요.");
				}
			}

		}

	

	public UserView(Scanner sc, Login lg, ArrayList<UserRent> userRentList, MyPage mp) {
		super();
		this.sc = sc;
		this.lg = lg;
		this.userRentList = userRentList;
		this.mp = mp;
	}



	public ArrayList<UserRent> getUserRentList() {
		return userRentList;
	}

	public void setUserRentList(ArrayList<UserRent> userRentList) {
		this.userRentList = userRentList;
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
				String[] str1 = ar.toString().split(",");
				System.out.println("책 ID: " +str1[0]+", 책 이름:"+str1[1]+", 작가: "+str1[2]+", 잔여수"+str1[3]);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void rentBook(String str3) {

		try {
			String fileName = "librarydata/bookInfo.txt";
			FileInputStream fis = new FileInputStream(fileName);
			BufferedInputStream bis = new BufferedInputStream(fis);
			ObjectInputStream in = new ObjectInputStream(bis);

			Map<String, Book> bookMap = (HashMap<String, Book>) in.readObject();

			Object[] arr = bookMap.values().toArray();
			for (Object ar : arr) {
				String[] str1 = ar.toString().split(",");
				System.out.println("책 ID: " +str1[0]+", 책 이름:"+str1[1]+", 작가: "+str1[2]+", 잔여수"+str1[3]);
			}

			System.out.print("대여하고 싶은 책 ID를 입력하세요 : ");
			String bookID = sc.nextLine();

			String[] str1 = null;

			if (!bookMap.containsKey(bookID)) {
				System.out.println("---------------------");
				System.out.println("목록에 있는 ID를 입력해주세요.");
				System.out.println("---------------------");
				rentBook(str3);
			} else {
				str1 = bookMap.get(bookID).toString().split(",");
			}

			if (bookMap.containsKey(bookID) && Integer.parseInt(str1[3]) > 0) {

				System.out.println(
						"책 ID : " + str1[0] + ", 책 이름 : " + str1[1] + ", 작가 : " + str1[2] + ", 수량 : " + str1[3]);
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

//					List<UserRent> defaultList = new ArrayList<>();
//					defaultList.add(null);

					String fileName2 = "librarydata/UserRentList.txt";
//					FileOutputStream fos2 = new FileOutputStream(fileName2);
//					BufferedOutputStream bos2 = new BufferedOutputStream(fos2);
//					ObjectOutputStream out2 = new ObjectOutputStream(bos2);
//
//					out2.writeObject(defaultList);

					FileInputStream fis2 = new FileInputStream(fileName2);
					BufferedInputStream bis2 = new BufferedInputStream(fis2);
					ObjectInputStream in2 = new ObjectInputStream(bis2);

					List<UserRent> userRentList = (ArrayList<UserRent>) in2.readObject();

//					FileOutputStream fos3 = new FileOutputStream(fileName2);
//					BufferedOutputStream bos3 = new BufferedOutputStream(fos3);
//					ObjectOutputStream out3 = new ObjectOutputStream(bos3);
					userRentList.add(new UserRent(str3, str[0], str[1], str[2], "a", "b"));

					FileOutputStream fos2 = new FileOutputStream(fileName2);
					BufferedOutputStream bos2 = new BufferedOutputStream(fos2);
					ObjectOutputStream out2 = new ObjectOutputStream(bos2);

					out2.writeObject(userRentList);
					out2.close();

					break;
				case "N":
					System.out.println("대여가 취소되었습니다.");
					break;
				default:
					System.out.println("Y 또는 N 키를 입력해주세요");
					rentBook(str3);
					break;

				}

			} else if (bookMap.containsKey(bookID) && Integer.parseInt(str1[3]) <= 0) {
				System.out.println("---------------------");
				System.out.println("해당 책의 수량이 없습니다.");
				System.out.println("---------------------");
				rentBook(str3);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void returnBook(String str) {
		try {
			String fileName2 = "librarydata/UserRentList.txt";
			FileInputStream fis = new FileInputStream(fileName2);
			BufferedInputStream bis = new BufferedInputStream(fis);
			ObjectInputStream in = new ObjectInputStream(bis);

			List<UserRent> userRentList = (ArrayList<UserRent>) in.readObject();
			Object[] arr = userRentList.toArray();
			for (int i = 0; i < arr.length; i++) {
				if (arr[i] != null) {
					String[] str1 = arr[i].toString().split(",");
					if (str1[0].equals(str)) {
						
						System.out.println("대여자 :"+str1[0]+", 책 아이디: "+str1[1]+", 책 이름: "+str1[2]+", 작가: "+str1[3]+", 대여일자: "+str1[4]+", 반납일자: "+str1[5]);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void defaultBookUtil() {
		try {
			String fileName2 = "librarydata/UserRentList.txt";

			FileInputStream fis2 = new FileInputStream(fileName2);
			BufferedInputStream bis2 = new BufferedInputStream(fis2);
			ObjectInputStream in2 = new ObjectInputStream(bis2);

			List<UserRent> userRentList = (ArrayList<UserRent>) in2.readObject();

			userRentList.add(null);

			FileOutputStream fos2 = new FileOutputStream(fileName2);
			BufferedOutputStream bos2 = new BufferedOutputStream(fos2);
			ObjectOutputStream out2 = new ObjectOutputStream(bos2);

			out2.writeObject(userRentList);
			out2.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
