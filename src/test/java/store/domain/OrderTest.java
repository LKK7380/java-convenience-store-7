package store.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderTest {
    @Test
    @DisplayName("주문을 생성한다")
    void createOrder() {
        String productName = "콜라";
        int quantity = 3;

        Order order = new Order(productName, quantity);

        assertThat(order.getProductName()).isEqualTo(productName);
        assertThat(order.getQuantity()).isEqualTo(quantity);
    }

    @Test
    @DisplayName("주문 수량이 0 이하면 예외가 발생한다")
    void validateQuantity() {
        assertThatThrownBy(() -> new Order("콜라", 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 주문 수량은 1개 이상이어야 합니다.");
    }

    @Test
    @DisplayName("상품명이 null이거나 빈 값이면 예외가 발생한다")
    void validateName() {
        assertThatThrownBy(() -> new Order(null, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 상품명은 null일 수 없습니다.");

        assertThatThrownBy(() -> new Order("", 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 상품명은 빈 값일 수 없습니다.");
    }
}