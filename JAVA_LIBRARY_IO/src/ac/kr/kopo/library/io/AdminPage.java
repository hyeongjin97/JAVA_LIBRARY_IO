package ac.kr.kopo.library.io;

import java.util.Scanner;

public class AdminPage extends UserView {

	private Scanner sc = new Scanner(System.in);

	public void adminPage() {
		while (true) {
			System.out.println("원하시는 메뉴를 선택하세요 : ");
			System.out.println("1. 책 추가하기 2. 책 정보 수정하기 3.회원목록 보기 4.로그아웃");
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
	}
}
