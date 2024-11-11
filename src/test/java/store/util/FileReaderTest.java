package store.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.Product;
import store.domain.Promotion;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("파일 읽기 테스트")
class FileReaderTest {
    private FileReader fileReader;
    private static final String TEST_PRODUCTS_PATH = "src/main/resources/products.md";
    private static final String TEST_PROMOTIONS_PATH = "src/main/resources/promotions.md";

    @BeforeEach
    void setUp() {
        fileReader = new FileReader();
    }

    @Test
    @DisplayName("상품 파일 읽기 테스트")
    void readProductsTest() {
        List<Product> products = fileReader.readProducts(TEST_PRODUCTS_PATH);

        assertThat(products).hasSize(16);
        Product cola = products.get(0);
        assertThat(cola.getName()).isEqualTo("콜라");
        assertThat(cola.getPrice()).isEqualTo(1000);
        assertThat(cola.getQuantity()).isEqualTo(10);
        assertThat(cola.getPromotionName()).isEqualTo("탄산2+1");
    }

    @Test
    @DisplayName("프로모션 파일 읽기 테스트")
    void readPromotionsTest() {
        List<Promotion> promotions = fileReader.readPromotions(TEST_PROMOTIONS_PATH);

        assertThat(promotions).hasSize(3);
        Promotion carbonPromo = promotions.get(0);
        assertThat(carbonPromo.getName()).isEqualTo("탄산2+1");
        assertThat(carbonPromo.getRequiredQuantity()).isEqualTo(2);
    }
}