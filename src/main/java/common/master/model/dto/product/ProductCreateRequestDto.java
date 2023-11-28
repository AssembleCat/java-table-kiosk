package common.master.model.dto.product;

public class ProductCreateRequestDto {
    private int managerId;
    private int price;
    private String name;
    private String nameEng;

    public ProductCreateRequestDto(int managerId, int price, String name, String nameEng) {
        this.managerId = managerId;
        this.price = price;
        this.name = name;
        this.nameEng = nameEng;
    }
}
