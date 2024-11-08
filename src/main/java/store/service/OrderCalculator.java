package store.service;

import store.domain.Order;
import store.domain.Product;

public class OrderCalculator {
    public int calculateTotalPrice(Order order, Product product) {
        return product.getPrice() * order.getQuantity();
    }
}