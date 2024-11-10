package store.service.discount;

import store.domain.Order;
import store.domain.Product;
import store.domain.Promotion;
import store.service.OrderCalculator;

public class DiscountCalculator {
    public DiscountCalculator(OrderCalculator orderCalculator) {
    }

    public int calculateFinalPrice(Order order, Product product, Promotion promotion, boolean b) {
    }
}
