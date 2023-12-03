package service.order;

import common.UserAccount;
import common.master.model.dto.product.ProductQueryResponseDto;
import service.http.HttpRequester;

import java.util.Scanner;

public class OrderService {
    private final Scanner scanner = new Scanner(System.in);
    private final UserAccount userAccount;

    public OrderService(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public void run() {
        while (true) {
            if(!userAccount.isUserSet()) {
                System.out.println("KIOSK의 판매자 정보가 확인되지않았습니다. 관리자에게 문의바랍니다.");
                return;
            }


            System.out.println("<< 주문페이지(" + userAccount.name + ")>>");
            System.out.println("1. 제품 조회");
            System.out.println("2. 현재 장바구니 조회");
            System.out.println("3. 구매 확정");
            System.out.println("4. 메인 메뉴로 돌아가기");
            System.out.println("** 작업을 선택하세요 →");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> {
                    viewProduct();
                }
                case 2 -> {
                    viewBasket();
                }
                case 3 -> {
                    paymentConfirm();
                    return;
                }
                case 4 -> {
                    // 메인메뉴로 돌아가기
                    return;
                }
                default -> System.out.println("잘못된 입력입니다. 다시 시도해주세요.");
            }
        }
    }

    private void viewProduct() {
        ProductQueryResponseDto managedProduct =
                new HttpRequester().sendGetRequest("/api/product/" + userAccount.id, ProductQueryResponseDto.class);

        if (managedProduct == null || managedProduct.products.isEmpty()) {
            System.out.println("등록된 제품이 없습니다.");
            return;
        }

        for (ProductQueryResponseDto.Product product : managedProduct.products)
            System.out.println(product.id + ". " + product.name + "(" + product.nameEng + "): " + product.price + "원");

        System.out.println("구매를 원하는 품목의 번호를 입력하세요.");


    }

    private void viewBasket() {

    }

    private void paymentConfirm() {

    }
}

