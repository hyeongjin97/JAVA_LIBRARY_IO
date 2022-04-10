package ac.kr.kopo.library.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

	public void printView(String str, ArrayList<UserRent> userRentList) { // 로그인 성공시 보여지는 메인 페이지(관리자와 일반 유저로 나뉘어저 있음)

		if (str.equals("admin")) {
			while (true) {
				System.out.println("원하시는 메뉴를 선택하세요 : ");
				System.out.println("1. 책 추가하기 2. 책 정보 수정하기 3. 책 정보 삭제하기 4. 책 목록 보기 5. 로그아웃");
				String num = sc.nextLine();
				switch (num) {
				case "1":
					adminAddBook(str, userRentList);
					printView(str, userRentList);
					break;
				case "2":
					adminUpdateBook(str, userRentList);
					printView(str, userRentList);
					break;
				case "3":
					adminDelBook(str, userRentList);
					printView(str, userRentList);
				case "4":
					showbookList();
					printView(str, userRentList);
				case "5":
					LibraryMain.main(null);
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
				printView(str, userRentList);
				break;
			case "5":
				LibraryMain.main(null);
				break;
			default:
				System.out.println("메뉴에 있는 번호를 선택하세요.");
				printView(str, userRentList);
			}
		}

	}

	public void showbookList() { // 도서관에 등록된 책 정보를 보여주는 메소드
		try {

			String fileName = "librarydata/bookInfo.txt";
			FileInputStream fis = new FileInputStream(fileName);
			BufferedInputStream bis = new BufferedInputStream(fis);
			ObjectInputStream in = new ObjectInputStream(bis);

			Map<String, Book> bookMap = (HashMap<String, Book>) in.readObject();

			Object[] arr = bookMap.values().toArray();
			System.out.println("=====================책 목록======================");
			for (Object ar : arr) {
				String[] str1 = ar.toString().split(",");
				System.out.println("책 ID: " + str1[0] + ", 책 이름:" + str1[1] + ", 작가: " + str1[2] + ", 잔여수: " + str1[3]);
			}
			System.out.println("=================================================");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void rentBook(String str3) { // 책 대여 기능을 담당하는 메소드

		try {
			String fileName = "librarydata/bookInfo.txt";
			FileInputStream fis = new FileInputStream(fileName);
			BufferedInputStream bis = new BufferedInputStream(fis);
			ObjectInputStream in = new ObjectInputStream(bis);

			Map<String, Book> bookMap = (HashMap<String, Book>) in.readObject(); // 책 정보를 갖고 있는 파일을 가져옴

			Object[] arr = bookMap.values().toArray();
			System.out.println("=====================책 목록======================");
			for (Object ar : arr) {
				String[] str1 = ar.toString().split(",");
				System.out.println("책 ID: " + str1[0] + ", 책 이름:" + str1[1] + ", 작가: " + str1[2] + ", 잔여수" + str1[3]);
			}
			System.out.println("=================================================");
			System.out.print("대여하고 싶은 책 ID를 입력하세요 : ");
			String bookID = sc.nextLine();

			String fileName2 = "librarydata/UserRentList.txt";
			FileInputStream fis2 = new FileInputStream(fileName2);
			BufferedInputStream bis2 = new BufferedInputStream(fis2);
			ObjectInputStream in2 = new ObjectInputStream(bis2); // 유저가 대여중인 책 리스트 파일을 가져옴

			List<UserRent> userRentList = (ArrayList<UserRent>) in2.readObject();
			Object[] arr1 = userRentList.toArray();
			for (int i = 0; i < arr1.length; i++) {
				if (arr1[i] != null) {

					String[] str = arr1[i].toString().split(",");
					if (str3.equals(str[0]) && str[1].equals(bookID)) { // 대여중인 책 중복체크
						System.out.println("-----------------------------");
						System.out.println("입력하신 ID의 책은 현재 대여중입니다.");
						System.out.println("-----------------------------");
						printView(str3, getUserRentList());
					}
				}
			}

			String[] str1 = null;

			if (!bookMap.containsKey(bookID)) {
				System.out.println("---------------------");
				System.out.println("목록에 있는 ID를 입력해주세요.");
				System.out.println("---------------------");
				rentBook(str3);
			} else {
				str1 = bookMap.get(bookID).toString().split(",");
			}

			if (bookMap.containsKey(bookID) && Integer.parseInt(str1[3]) > 0) {// 현재 등록된 책수량 체크 0 이하일 경우 대여가 불가

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

					out.writeObject(bookMap); // 수정된 책 정보를 책 정보를 담고 있는 파일에 추가
					out.close();

					Calendar cal = Calendar.getInstance();
					Calendar cal2 = Calendar.getInstance();
					Date rentDate = new Date();
					Date returnDate = new Date();

					SimpleDateFormat sdf;

					sdf = new SimpleDateFormat("yyyy-MM-dd");
					cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE)); // 현재 대여날짜는 오늘로 설정
					cal2.set(cal2.get(Calendar.YEAR), cal2.get(Calendar.MONTH), cal2.get(Calendar.DATE)); // 반납 날짜 설정
					cal2.add(Calendar.DATE, 7); // 반납날짜는 대여 날짜로부터 7일 뒤
					rentDate = new Date(cal.getTimeInMillis());
					returnDate = new Date(cal2.getTimeInMillis());

					userRentList.add(
							new UserRent(str3, str[0], str[1], str[2], sdf.format(rentDate), sdf.format(returnDate)));

					FileOutputStream fos2 = new FileOutputStream(fileName2);
					BufferedOutputStream bos2 = new BufferedOutputStream(fos2);
					ObjectOutputStream out2 = new ObjectOutputStream(bos2);

					out2.writeObject(userRentList); // 유저 대여 목록을 저장한는 파일에 변경된 사항을 저장
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

	public void returnBook(String str) { // 책 반납기능을 담당하는 메소드
		try {
			String fileName2 = "librarydata/UserRentList.txt";
			FileInputStream fis = new FileInputStream(fileName2);
			BufferedInputStream bis = new BufferedInputStream(fis);
			ObjectInputStream in = new ObjectInputStream(bis); // 유저가 대여중인 책 목록을 가져옴

			List<UserRent> userRentList = (ArrayList<UserRent>) in.readObject();
			Object[] arr = userRentList.toArray();
			int cnt = 0;
			System.out.println("================================현재 대여중인  목록======================================");
			for (int i = 0; i < arr.length; i++) {
				if (arr[i] != null) {
					String[] str1 = arr[i].toString().split(",");
					if (str1[0].equals(str)) {
						cnt++;
						System.out.println("대여자 :" + str1[0] + ", 책 아이디: " + str1[1] + ", 책 이름: " + str1[2] + ", 작가: "
								+ str1[3] + ", 대여일자: " + str1[4] + ", 반납일자: " + str1[5]);
					}
				}
			}
			System.out.println("===================================================================================");
			if (cnt == 0) {
				System.out.println("대여중인 책이 없습니다.");
				printView(str, getUserRentList());
			}

			String fileName = "librarydata/bookInfo.txt";
			FileInputStream fis2 = new FileInputStream(fileName);
			BufferedInputStream bis2 = new BufferedInputStream(fis2);
			ObjectInputStream in2 = new ObjectInputStream(bis2); // 현재 등록된 책정보를 갖고 있는 파일을 가져옴
			Map<String, Book> bookMap = (HashMap<String, Book>) in2.readObject();
			System.out.println("반납하실 책 아이디를 입력해주세요 : ");
			String bookID = sc.nextLine();
			int cnt1 = 0;
			for (int i = 0; i < arr.length; i++) { // 전체 유저가 대여중인 책 목록을 순회하면서 반납 기능을 수행 arr : 전체 유저가 대여한 리스트를 오브젝틑 배열에
													// 넣음

				if (arr[i] != null) {

					String[] str1 = arr[i].toString().split(",");
					if (str1[0].equals(str) && userRentList.get(i).getBookID().equals(bookID)) { // str : 현재 로그인 중인 아이디
																									// , str1[0] :
																									// 현재대여중인 책을 빌린 아이

						String[] bookInfo = bookMap.get(bookID).toString().split(",");

						System.out
								.println("책 아이디 : " + bookInfo[0] + ", 책 이름 : " + bookInfo[1] + "작가 : " + bookInfo[2]);
						System.out.println("해당 내용의 책을 반납하시겠습니까 ? (Y/N) ");
						String confirm = sc.nextLine();
						switch (confirm) {
						case "Y":
							Integer num = Integer.parseInt(bookInfo[3]) + 1;
							bookMap.put(bookID, new Book(bookInfo[0], bookInfo[1], bookInfo[2], num.toString()));
							System.out.println("반납이 완료되었습니다.");
							cnt++;
							FileOutputStream fos = new FileOutputStream(fileName);
							BufferedOutputStream bos = new BufferedOutputStream(fos);

							ObjectOutputStream out = new ObjectOutputStream(bos);

							out.writeObject(bookMap); // 반납이 완료될 경우 책 정보를 담고 있는 파일에 변경사항을 저장
							out.close();

							Object[] arr1 = userRentList.toArray();
							for (int j = 0; j < arr1.length; j++) {
								if (arr1[j] != null) {
									String[] str2 = arr[j].toString().split(",");
									if (str2[0].equals(str) && str2[1].equals(bookID)) {

										userRentList.remove(arr1[j]);

									}
								}

							}

							FileOutputStream fos1 = new FileOutputStream(fileName2);
							BufferedOutputStream bos1 = new BufferedOutputStream(fos1);

							ObjectOutputStream out1 = new ObjectOutputStream(bos1);

							out1.writeObject(userRentList); // 반납이 완료될 경우 유저가 대여한 리스트에서 삭제한 사항을 저장
							out1.close();
							printView(str, getUserRentList());
							break;
						case "N":
							System.out.println("취소되었습니다.");
							printView(str, getUserRentList());
							break;
						default:
							System.out.println("Y 또는 N 키를 눌러주세요.");
							returnBook(str);
						}

					}

				} else {

				}

			}
			if (cnt1 == 0) {
				System.out.println(" 입력하신 ID를 가진 책을 대여중이지 않습니다.");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void defaultBookUtil() { // 초기에 유저가 대여한 리스트 파일에 널 값을 넣어줌
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

	public void adminAddBook(String str3, ArrayList<UserRent> userRentList) { // 관리자 책 추가 메소드

		try {

			String fileName = "librarydata/bookInfo.txt";
			FileInputStream fis = new FileInputStream(fileName);
			BufferedInputStream bis = new BufferedInputStream(fis);
			ObjectInputStream in = new ObjectInputStream(bis);

			Map<String, Book> bookMap = (HashMap<String, Book>) in.readObject();

			System.out.println("-------------------- 책 추가하기---------------");
			System.out.print("책 ID : ");
			String bookID = sc.nextLine();
			if (bookMap.containsKey(bookID)) {
				System.out.println("해당 아이디는 이미 존재합니다.");
				adminAddBook(str3, userRentList);
			} else {

				System.out.print("책 이름 : ");
				String bookName = sc.nextLine();
				System.out.print("작가 : ");
				String bookWriter = sc.nextLine();
				System.out.print("수량 : ");
				String bookCount = sc.nextLine();

				bookMap.put(bookID, new Book(bookID, bookName, bookWriter, bookCount));

				System.out.println(
						"책 ID : " + bookID + ", 책 이름 : " + bookName + ", 작가 : " + bookWriter + ", 수량 " + bookCount);
				System.out.println("해당 정보의 책을 추가하시겠습니까?(Y/N)");
				String confirm = sc.nextLine();
				if (confirm.equals("Y")) {
					System.out.println("추가되었습니다.");
				} else if (confirm.equals("N")) {
					System.out.println("취소되었습니다.");
					printView(str3, userRentList);
				} else {
					System.out.println(" Y 또는 N을 입력해주세요.");
					printView(str3, userRentList);
				}

				FileOutputStream fos = new FileOutputStream(fileName);
				BufferedOutputStream bos = new BufferedOutputStream(fos);

				ObjectOutputStream out = new ObjectOutputStream(bos);

				out.writeObject(bookMap);
				out.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void adminUpdateBook(String str3, ArrayList<UserRent> userRentList) { // 관리자 책 정보 수정 메소드
		try {
			String fileName = "librarydata/bookInfo.txt";
			FileInputStream fis = new FileInputStream(fileName);
			BufferedInputStream bis = new BufferedInputStream(fis);
			ObjectInputStream in = new ObjectInputStream(bis);

			Map<String, Book> bookMap = (HashMap<String, Book>) in.readObject();

			System.out.println("----------------- 책 정보 수정------------------");
			showbookList();
			System.out.println("수정하고 싶은 책 ID를 입력하세요.");
			String bookID = sc.nextLine();
			if (bookMap.containsKey(bookID)) {

				String[] str = bookMap.get(bookID).toString().split(",");

				System.out.println("-----------------기존 정보--------------------");
				System.out.println("책 ID : " + bookID + ", 책 이름 : " + str[1] + ", 작가 : " + str[2] + ", 수량 " + str[3]);

				System.out.println("------------ 수정할 사항을 입력해주세요.-----------");
				System.out.print("책 이름 : ");
				String bookName = sc.nextLine();
				System.out.print("작가 : ");
				String bookWriter = sc.nextLine();
				System.out.print("수량 : ");
				String bookCount = sc.nextLine();
				System.out.println(
						"책 ID : " + bookID + ", 책 이름 : " + bookName + ", 작가 : " + bookWriter + ", 수량: " + bookCount);
				System.out.println("해당 정보로 수정하시겠습니까? (Y/N)");
				String confirm = sc.nextLine();
				if (confirm.equals("Y")) {
					bookMap.put(bookID, new Book(bookID, bookName, bookWriter, bookCount));
					System.out.println("수정되었습니다.");
				} else if (confirm.equals("N")) {
					System.out.println("취소되었습니다.");
					printView(str3, userRentList);
				} else {
					System.out.println(" Y 또는 N을 입력해주세요.");
					printView(str3, userRentList);
				}

			} else {
				System.out.println("해당 아이디를 가진 책 정보는 존재하지 않습니다.");
				adminUpdateBook(str3, userRentList);
			}

			FileOutputStream fos = new FileOutputStream(fileName);
			BufferedOutputStream bos = new BufferedOutputStream(fos);

			ObjectOutputStream out = new ObjectOutputStream(bos);

			out.writeObject(bookMap);
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void adminDelBook(String str3, ArrayList<UserRent> userRentList) {// 관리자 책 삭제 메소드
		try {
			String fileName = "librarydata/bookInfo.txt";
			FileInputStream fis = new FileInputStream(fileName);
			BufferedInputStream bis = new BufferedInputStream(fis);
			ObjectInputStream in = new ObjectInputStream(bis);

			Map<String, Book> bookMap = (HashMap<String, Book>) in.readObject();

			showbookList();
			System.out.println("수정하고 싶은 책 ID를 입력하세요.");
			String bookID = sc.nextLine();
			if (bookMap.containsKey(bookID)) {
				String[] str = bookMap.get(bookID).toString().split(",");
				System.out.println("책 ID : " + bookID + ", 책 이름 : " + str[1] + ", 작가 : " + str[2] + ", 수량: " + str[3]);
				System.out.println("해당 정보의 책을 삭제하시겠습니까? (Y/N)");
				String confirm = sc.nextLine();
				if (confirm.equals("Y")) {
					bookMap.remove(bookID);
					System.out.println("삭제되었습니다.");
				} else if (confirm.equals("N")) {
					System.out.println("취소되었습니다.");
					printView(str3, userRentList);
				} else {
					System.out.println(" Y 또는 N을 입력해주세요.");
					printView(str3, userRentList);
				}

			} else {
				System.out.println("해당 아이디를 가진 책 정보는 존재하지 않습니다.");
				printView(str3, userRentList);

			}

			FileOutputStream fos = new FileOutputStream(fileName);
			BufferedOutputStream bos = new BufferedOutputStream(fos);

			ObjectOutputStream out = new ObjectOutputStream(bos);

			out.writeObject(bookMap);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
