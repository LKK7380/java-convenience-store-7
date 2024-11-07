package store.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {
    @Test
    @DisplayName("상품을 생성한다")
    void createProduct() {
        String name = "콜라";
        int price = 1000;
        int quantity = 10;
        String promotionName = "탄산2+1";

        Product product = new Product(name, price, quantity, promotionName);

        assertThat(product).isNotNull();
        assertThat(product.getName()).isEqualTo(name);
    }
}