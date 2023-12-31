package service.admin;

import common.UserAccount;
import common.master.model.dto.product.ProductCreateRequestDto;
import common.master.model.dto.product.ProductQueryResponseDto;
import common.master.model.dto.sale.SaleHeaderList;
import service.http.HttpRequester;

import java.util.Scanner;

public class AdminService {
    private final Scanner scanner = new Scanner(System.in);
    private final UserAccount userAccount;

    public AdminService(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public void run() {
        while (true) {
            System.out.println("<< 관리자 모드(" + userAccount.name + ") >>");
            System.out.println("1. 제품 등록");
            System.out.println("2. 제품 수정");
            System.out.println("3. 매출 조회");
            System.out.println("4. 메인으로 돌아가기");
            System.out.println("** 작업을 선택하세요 →");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> createProduct();
                case 2 -> updateProduct();
                case 3 -> querySales();
                case 4 -> {
                    return;
                }
                default -> System.out.println("잘못된 입력입니다. 다시 시도해주세요.");
            }
        }
    }

    private void createProduct() {
        System.out.println("<< " + userAccount.name + " 권한 제품 등록 >>");
        System.out.print("제품명: ");
        String name = scanner.next();
        System.out.print("제품명(영문): ");
        String nameEng = scanner.next();
        System.out.print("가격: ");
        int price = scanner.nextInt();

        HttpRequester requester = new HttpRequester();
        ProductCreateRequestDto createRequest = new ProductCreateRequestDto(
                userAccount.id,
                price,
                name,
                nameEng
        );

        boolean response = requester.sendPostRequest("/api/product", createRequest, boolean.class);

        if (!response) {
            System.out.print("제품등록 실패");
        } else {
            System.out.print(name + " 제품등록 성공");
        }
    }


    private void updateProduct() {
        ProductQueryResponseDto managedProduct =
                new HttpRequester().sendGetRequest("/api/product/" + userAccount.id, ProductQueryResponseDto.class);

        if (managedProduct == null || managedProduct.products.isEmpty()) {
            System.out.println("등록된 제품이 없습니다.");
            return;
        }

        while (true) {
            for (ProductQueryResponseDto.Product product : managedProduct.products)
                System.out.println(product.id + ". " + product.name + "(" + product.nameEng + "): " + product.price + "원");

            System.out.println("수정을 원하는 제품의 번호를 입력하세요. (-1 입력시 관리자모드 복귀): ");

            int requestId = scanner.nextInt();

            if(requestId == -1) return;

            ProductQueryResponseDto.Product matchingProduct = managedProduct.products.stream()
                    .filter(product -> product.id == requestId)
                    .findFirst()
                    .orElse(null);

            if (matchingProduct == null) {
                System.out.println("해당 번호의 제품이 없습니다.");
                return;
            }

            System.out.println("<< 제품 수정 >> ");
            System.out.println("1. 미사용 처리");
            System.out.println("2. 가격 변경");
            System.out.println("3. 제품명 변경");
        }
    }

    private void querySales() {
        HttpRequester requester = new HttpRequester();

        SaleHeaderList response = requester.sendGetRequest("/api/payment?managerId=" + userAccount.id, SaleHeaderList.class);

        int totAmount = 0;
        System.out.println("<< 판매내역 조회 >>");
        for (SaleHeaderList.SaleHeader header : response.headers) {
            totAmount += header.totalPrice;
            System.out.println(header.id + ". 판매금액: " + header.totalPrice + "원, " + "판매개수: "+ header.totalQty + " 건, " + " 판매시각: " + header.saleDttm);
        }

        System.out.println("총 판매내역: " + response.headers.size() + "건");
        System.out.println("총 판매금액: " + totAmount + " 원");
    }
}
