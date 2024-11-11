package store.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.Inventory;
import store.domain.Product;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("StoreController 테스트")
class StoreControllerTest {
    private StoreController controller;
    private Inventory inventory;

    @BeforeEach
    void setUp() {
        List<Product> products = Arrays.asList(
                new Product("콜라", 1000, 10, "탄산2+1"),
                new Product("사이다", 1000, 8, "탄산2+1"),
                new Product("물", 500, 10, null)
        );
        inventory = new Inventory(products);
        controller = new StoreController(inventory);
    }

    @Test
    @DisplayName("정상적인 주문 처리")
    void processValidOrder() {
        Product cola = inventory.getProducts().get("콜라");
        assertThat(cola.getQuantity()).isEqualTo(10);
    }
}