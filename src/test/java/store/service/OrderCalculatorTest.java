package store.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.Order;
import store.domain.Product;

import static org.assertj.core.api.Assertions.assertThat;

class OrderCalculatorTest {
    @Test
    @DisplayName("주문 금액을 계산한다")
    void calculateTotalPrice() {
        Product product = new Product("콜라", 1000, 10, "탄산2+1");
        Order order = new Order("콜라", 3);
        OrderCalculator calculator = new OrderCalculator();

        assertThat(calculator.calculateTotalPrice(order, product)).isEqualTo(3000);
    }
}