package service;

import common.UserAccount;
import common.master.User;
import service.admin.AdminService;
import service.http.HttpRequester;
import service.order.OrderService;

import java.util.Scanner;

public class MenuService {
    private final Scanner scanner = new Scanner(System.in);
    private final UserAccount account = new UserAccount();
    private final AdminService adminService = new AdminService(account);
    private final OrderService orderService = new OrderService(account);

    public void run() {
        while (true) {
            System.out.println("<< 테이블 키오스크 >>");
            System.out.println("1. 주문하기");
            System.out.println("2. 관리자 모드");
            System.out.println("3. 종료");
            System.out.println("** 작업을 선택하세요 →");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> orderService.run();
                case 2 -> {
                    if(!login()) {
                        System.out.println("관리자 로그인 실패");
                        return;
                    }
                    adminService.run();
                }
                case 3 -> {
                    System.out.println("프로그램을 종료합니다.");
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

        User response = requester.sendGetRequest("/api/manager/login?email=" + id + "&password=" + password, User.class);

        if (response == null) return false;

        account.setUserAccountFromUser(response);

        return true;
    }
}


