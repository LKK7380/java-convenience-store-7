package store.domain;

public class Order {
    private final String productName;
    private final int quantity;

    public Order(String productName, int quantity) {
        validateName(productName);
        validateQuantity(quantity);
        this.productName = productName;
        this.quantity = quantity;
    }

    private void validateName(String productName) {
        if (productName == null) {
            throw new IllegalArgumentException("[ERROR] 상품명은 null일 수 없습니다.");
        }
        if (productName.isBlank()) {
            throw new IllegalArgumentException("[ERROR] 상품명은 빈 값일 수 없습니다.");
        }
    }

    private void validateQuantity(int quantity) {
        if (quantity < 1) {
            throw new IllegalArgumentException("[ERROR] 주문 수량은 1개 이상이어야 합니다.");
        }
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }
}