package store.domain;

public class Product {
    private final String name;
    private final int price;

    public Product(String name, int price, int quantity, String promotionName) {
        validatePrice(price);
        this.name = name;
        this.price = price;
    }

    private void validatePrice(int price) {
        if (price <= 0) {
            throw new IllegalArgumentException("[ERROR] 가격은 0 보다 커야 합니다.");
        }
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}