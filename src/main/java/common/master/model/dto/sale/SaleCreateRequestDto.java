package common.master.model.dto.sale;

import java.util.ArrayList;
import java.util.List;

public class SaleCreateRequestDto {
    public int managerId;
    public List<SaleProduct> saleProduct = new ArrayList<>();

    public SaleCreateRequestDto() {
    }

    public static class SaleProduct {
        public int id;
        public int qty;
        public int amount;
        public String name;

        public SaleProduct(int id, int qty, int amount, String name) {
            this.id = id;
            this.qty = qty;
            this.amount = amount;
            this.name = name;
        }
    }

    public void addSaleProduct(SaleProduct product) {
        this.saleProduct.add(product);
    }

    public void clearSales(int managerId) {
        this.saleProduct = new ArrayList<>();
        this.managerId = managerId;
    }

    public void viewBasket() {
        System.out.println("현재 장바구니 내역");

        for (SaleProduct product : saleProduct) {
            System.out.println(product.name + " 제품 " + product.qty + " 개");
        }
    }

    public void confirmSales() {
        int totQty = 0;
        int totAmount = 0;
        for (SaleProduct product : saleProduct) {
            totQty += product.qty;
            totAmount += product.qty * product.amount;
        }

        System.out.println(totQty + "개의 폼목, " + totAmount + "원 결제합니다.");
    }
}
