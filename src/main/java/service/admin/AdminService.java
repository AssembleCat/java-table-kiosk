package service.admin;

import common.UserAccount;
import common.master.User;
import service.http.HttpRequester;

import java.util.Scanner;

public class AdminService {
    private final Scanner scanner = new Scanner(System.in);
    private final UserAccount userAccount = new UserAccount();

    public void run() {
        while (true) {
            if (!login()) {
                System.out.println("관리자 로그인 실패");
                return;
            }

            System.out.println("<< 관리자 모드(" + userAccount.name + ") >>");
            System.out.println("1. 제품 등록");
            System.out.println("2. 제품 삭제");
            System.out.println("3. 제품 수정");
            System.out.println("4. 매출 조회");
            System.out.println("5. 메인으로 돌아가기");
            System.out.println("** 작업을 선택하세요 →");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> {
                    // 제품 등록 로직 구현
                }
                case 2 -> {
                    // 제품 삭제 로직 구현
                }
                case 3 -> {
                    // 제품 수정 로직 구현
                }
                case 4 -> {
                    // 매출 조회 로직 구현
                }
                case 5 -> {
                    return;
                }
                default -> System.out.println("잘못된 입력입니다. 다시 시도해주세요.");
            }
        }
    }

    public Boolean login() {
        System.out.println("<< 관리자 로그인 >>");
        System.out.print("아이디: ");
        String id = scanner.next();
        System.out.print("비밀번호: ");
        String password = scanner.next();

        HttpRequester requester = new HttpRequester();

        User response = requester.sendGetRequest("/api/user?email=" + id + "&password=" + password, User.class);

        if (response == null) return false;

        userAccount.setUserAccountFromUser(response);

        return true;
    }
}
