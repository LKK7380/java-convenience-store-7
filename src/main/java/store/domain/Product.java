package store.domain;

public class Product {
    private final String name;
    private final int price;
    private int quantity;
    private final String promotionName;
    private int promotionQuantity;

    public Product(String name, int price, int quantity, String promotionName) {
        validatePrice(price);
        validateQuantity(quantity);
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotionName = promotionName == null ? "" : promotionName;
        this.promotionQuantity = 0;
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

    public void setPromotionQuantity(int amount) {
        validateQuantity(amount);
        this.promotionQuantity = amount;
    }

    public void decreasePromotionQuantity(int amount) {
        if (promotionQuantity < amount) {
            throw new IllegalArgumentException("[ERROR] 프로모션 재고가 부족합니다.");
        }
        this.promotionQuantity -= amount;
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

    public int getPromotionQuantity() {
        return promotionQuantity;
    }
}