package store.domain;

public class OrderCalculator {
    public int calculateTotalPrice(Order order, Product product) {
        return product.getPrice() * order.getQuantity();
    }
}