package ac.kr.kopo.library.io;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class libraryMain {
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		Register rg = new Register();
		Login lg = new Login();
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
				break;
			case "3":
				System.exit(0);
			default:
				break;
			}

		}
	}

}