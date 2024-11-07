package store.domain;

public class Product {
    private final String name;

    public Product(String name, int price, int quantity, String promotionName) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}