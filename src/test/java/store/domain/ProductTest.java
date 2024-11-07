package store.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class ProductTest {
    @Test
    @DisplayName("상품 생성")
    void createProduct() {
        String name = "콜라";
        int price = 1000;
        int quantity = 10;
        String promotionName = "탄산2+1";

        Product product = new Product(name, price, quantity, promotionName);

        assertThat(product).isNotNull();
        assertThat(product.getName()).isEqualTo(name);
        assertThat(product.getPrice()).isEqualTo(price);
        assertThat(product.getQuantity()).isEqualTo(quantity);
        assertThat(product.getPromotionName()).isEqualTo(promotionName);
    }

    @Test
    @DisplayName("상품 가격이 0 이하이면 예외가 발생한다")
    void validatePrice() {
        assertThatThrownBy(() -> new Product("콜라", 0, 10, "탄산2+1"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 가격은 0 보다 커야 합니다.");
    }

    @Test
    @DisplayName("상품 수량이 음수이면 예외가 발생한다")
    void validateQuantity() {
        assertThatThrownBy(() -> new Product("콜라", 1000, -1, "탄산2+1"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 수량은 0보다 작을 수 없습니다.");
    }

    @Test
    @DisplayName("프로모션명이 null이면 빈 문자열로 초기화한다")
    void createProductWithNullPromotion() {
        Product product = new Product("콜라", 1000, 10, null);

        assertThat(product.getPromotionName()).isEmpty();
    }
}