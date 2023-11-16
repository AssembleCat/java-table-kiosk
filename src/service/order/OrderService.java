package service.order;

import java.util.Scanner;

public class OrderService {
    private final Scanner scanner = new Scanner(System.in);

    public void run() {
        while (true) {
            System.out.println("<< 주문페이지 >>");
            System.out.println("1. 제품 조회");
            System.out.println("2. 제품 구매");
            System.out.println("3. 현재 장바구니 조회");
            System.out.println("4. 구매 확정");
            System.out.println("5. 메인 메뉴로 돌아가기");
            System.out.println("** 작업을 선택하세요 →");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> {
                    // 제품 조회 로직 구현
                }
                case 2 -> {
                    // 제품 구매 로직 구현
                    System.out.println("구매하실 제품명을 입력하세요.");
                    String productName = scanner.next();
                }
                case 3 -> {
                    // 현재 장바구니 조회 로직 구현
                }
                case 4 -> {
                    // 구매 확정 로직 구현
                }
                case 5 -> {
                    return;
                }
                default -> System.out.println("잘못된 입력입니다. 다시 시도해주세요.");
            }
        }
    }
}

