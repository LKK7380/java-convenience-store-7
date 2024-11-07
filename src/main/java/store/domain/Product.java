package store.domain;

public class Product {
    private final String name;
    private final int price;
    private int quantity;
    private final String promotionName;

    public Product(String name, int price, int quantity, String promotionName) {
        validatePrice(price);
        validateQuantity(quantity);
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotionName = promotionName == null ? "" : promotionName;
    }

    private void validatePrice(int price) {
        if (price <= 0) {
            throw new IllegalArgumentException("[ERROR] 가격은 0 보다 커야 합니다.");
        }
    }

    private void validateQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("[ERROR] 수량은 0보다 작을 수 없습니다.");
        }
    }

    public void decreaseQuantity(int amount) {
        if (quantity < amount) {
            throw new IllegalArgumentException("[ERROR] 재고가 부족합니다.");
        }
        this.quantity -= amount;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getPromotionName() {
        return promotionName;
    }
}