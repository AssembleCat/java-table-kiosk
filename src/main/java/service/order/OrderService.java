package service.order;

import common.UserAccount;
import common.master.model.dto.product.ProductQueryResponseDto;
import common.master.model.dto.sale.SaleCreateRequestDto;
import service.http.HttpRequester;

import java.util.Scanner;

public class OrderService {
    private final Scanner scanner = new Scanner(System.in);
    private final UserAccount userAccount;
    private final SaleCreateRequestDto sales = new SaleCreateRequestDto();

    public OrderService(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public void run() {
        while (true) {
            if (!userAccount.isUserSet()) {
                System.out.println("KIOSK의 판매자 정보가 확인되지않았습니다. 관리자에게 문의바랍니다.");
                return;
            }

            //장바구니 초기화
            sales.managerId = userAccount.id;

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
                    sales.viewBasket();
                }
                case 3 -> {
                    paymentConfirm();
                    return;
                }
                case 4 -> {
                    sales.clearSales(userAccount.id);
                    return;
                }
                default -> System.out.println("잘못된 입력입니다. 다시 시도해주세요. →");
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

        System.out.println("구매를 원하는 품목의 번호를 입력하세요.(구매입력 종료 -1)");

        while (true) {
            int productId = scanner.nextInt();

            if(productId == -1) break;

            ProductQueryResponseDto.Product selectedProduct = null;

            for (ProductQueryResponseDto.Product product : managedProduct.products) {
                if (product.id == productId) {
                    selectedProduct = product;
                    break;
                }
            }

            if (selectedProduct == null) {
                System.out.println("선택하신 품목이 존재하지 않습니다. 다시 선택해주세요.");
            } else {
                System.out.println(selectedProduct.name + "을(를) 장바구니에 추가했습니다.");
                sales.addSaleProduct(new SaleCreateRequestDto.SaleProduct(selectedProduct.id, 1, selectedProduct.price, selectedProduct.name));
            }
        }
    }

    private void paymentConfirm() {
        sales.confirmSales();

        HttpRequester requester = new HttpRequester();
        boolean response = requester.sendPostRequest("/api/payment", sales, boolean.class);

        if (!response) {
            System.out.print("결제 실패");
        } else {
            System.out.print("결제가 성공적으로 마무리되었습니다.");
        }
    }
}

