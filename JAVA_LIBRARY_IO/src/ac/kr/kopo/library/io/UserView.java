package ac.kr.kopo.library.io;

//// 반납기능 수정 필요
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

	public void printView(String str, ArrayList<UserRent> userRentList) {

		if (str.equals("admin")) {
			while (true) {
				System.out.println("원하시는 메뉴를 선택하세요 : ");
				System.out.println("1. 책 추가하기 2. 책 정보 수정하기 3. 책 목록 보기 4. 로그아웃");
				String num = sc.nextLine();
				switch (num) {
				case "1":
					adminAddBook();
					printView(str, userRentList);
					break;
				case "2":
					adminUpdateBook();
					printView(str, userRentList);
					break;
				case "3":
					showbookList();
					printView(str, userRentList);
				case "4":
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
				System.out.println("책 ID: " + str1[0] + ", 책 이름:" + str1[1] + ", 작가: " + str1[2] + ", 잔여수" + str1[3]);
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
				System.out.println("책 ID: " + str1[0] + ", 책 이름:" + str1[1] + ", 작가: " + str1[2] + ", 잔여수" + str1[3]);
			}

			System.out.print("대여하고 싶은 책 ID를 입력하세요 : ");
			String bookID = sc.nextLine();

			String fileName2 = "librarydata/UserRentList.txt";
			FileInputStream fis2 = new FileInputStream(fileName2);
			BufferedInputStream bis2 = new BufferedInputStream(fis2);
			ObjectInputStream in2 = new ObjectInputStream(bis2);

			List<UserRent> userRentList = (ArrayList<UserRent>) in2.readObject();
			Object[] arr1 = userRentList.toArray();
			for (int i = 0; i < arr1.length; i++) {
				if (arr1[i] != null) {

					String[] str = arr1[i].toString().split(",");
					if (str3.equals(str[0]) &&str[1].equals(bookID)) {
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

					
					Calendar cal = Calendar.getInstance();
					Calendar cal2 = Calendar.getInstance();
					Date rentDate = new Date();
					Date returnDate = new Date();
					
					SimpleDateFormat sdf;
					
					sdf = new SimpleDateFormat("yyyy-MM-dd");
					cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
					cal2.set(cal2.get(Calendar.YEAR), cal2.get(Calendar.MONTH), cal2.get(Calendar.DATE));
					cal2.add(Calendar.DATE, 7);
					rentDate = new Date(cal.getTimeInMillis());
					returnDate = new Date(cal2.getTimeInMillis());
					
					userRentList.add(new UserRent(str3, str[0], str[1], str[2], sdf.format(rentDate), sdf.format(returnDate)));

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
			int cnt = 0;
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

			if (cnt == 0) {
				System.out.println("대여중인 책이 없습니다.");
				printView(str, getUserRentList());
			}

			System.out.println("--------------------------------------------------------------------------");

			String fileName = "librarydata/bookInfo.txt";
			FileInputStream fis2 = new FileInputStream(fileName);
			BufferedInputStream bis2 = new BufferedInputStream(fis2);
			ObjectInputStream in2 = new ObjectInputStream(bis2);
			Map<String, Book> bookMap = (HashMap<String, Book>) in2.readObject();
			System.out.println("반납하실 책 아이디를 입력해주세요 : ");
			String bookID = sc.nextLine();
			int cnt1 = 0;
			for (int i = 0; i < arr.length; i++) {

				//////

				if (arr[i] != null) {

					String[] str1 = arr[i].toString().split(",");
					if (str1[0].equals(str) &&userRentList.get(i).getBookID().equals(bookID)) {
						
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
							cnt ++;
							FileOutputStream fos = new FileOutputStream(fileName);
							BufferedOutputStream bos = new BufferedOutputStream(fos);

							ObjectOutputStream out = new ObjectOutputStream(bos);

							out.writeObject(bookMap);
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

							out1.writeObject(userRentList);
							out1.close();
							printView(str,getUserRentList());
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

				}else {
				
				}

			}
			if (cnt1 == 0) {
				System.out.println(" 입력하신 ID를 가진 책을 대여중이지 않습니다."); 
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
	
	public void adminAddBook() {
		
		try {
			
			String fileName = "librarydata/bookInfo.txt";
			FileInputStream fis = new FileInputStream(fileName);
			BufferedInputStream bis = new BufferedInputStream(fis);
			ObjectInputStream in = new ObjectInputStream(bis);

			Map<String, Book> bookMap = (HashMap<String, Book>) in.readObject();
			
			
			
			System.out.println("-------------------- 책 추가하기---------------");
			System.out.print("책 아이디 : ");
			String bookID = sc.nextLine();
			if( bookMap.containsKey(bookID)) {
				System.out.println("해당 아이디는 이미 존재합니다.");
				adminAddBook();
			}else {
				
				System.out.print("책 이름 : ");
				String bookName = sc.nextLine();
				System.out.print("작가 : ");
				String bookWriter = sc.nextLine();
				System.out.print("수량 : ");
				String bookCount = sc.nextLine();
				
				bookMap.put(bookID, new Book(bookID, bookName, bookWriter, bookCount));
				
				FileOutputStream fos = new FileOutputStream(fileName);
				BufferedOutputStream bos = new BufferedOutputStream(fos);

				ObjectOutputStream out = new ObjectOutputStream(bos);

				out.writeObject(bookMap);
				out.close();
				System.out.println("추가되었습니다.");
				
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		
		
		
	}
	
	public void adminUpdateBook() {
		System.out.println("----------------- 책 정보 수정------------------");
	}

}
