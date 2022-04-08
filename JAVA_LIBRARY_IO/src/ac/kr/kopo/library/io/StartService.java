package ac.kr.kopo.library.io;

import java.util.ArrayList;
import java.util.Scanner;

public class StartService {

	private Scanner sc = new Scanner(System.in);

	Register rg = new Register();
	Login lg = new Login();
	UserView uv = new UserView();

	public void startService(String loginID, ArrayList<UserRent> userRentList) {

		while (true) {
			System.out.println("----------- 코포 도서관에 오신걸 환영합니다 ------------");
			System.out.println("1. 회원가입 2. 로그인 3. 종료");
			String num = sc.nextLine();
			switch (num) {
			case "1":
				rg.processRegister();
				break;
			case "2":
				lg.loginStart();
				uv.printView(loginID, userRentList);
				break;
			case "3":
				System.exit(0);
			default:
				break;
			}

		}
	}
}
