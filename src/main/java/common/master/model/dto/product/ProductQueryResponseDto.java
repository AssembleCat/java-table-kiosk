package common.master.model.dto.product;

import java.util.List;

public class ProductQueryResponseDto {
    public List<Product> products;

    public static class Product {
        public int id;
        public int price;
        public String name;
        public String nameEng;
        public void printProduct() {
            System.out.println(id + ". " + "이름: " + name + "(" + price + "원)");
        }
    }
}
