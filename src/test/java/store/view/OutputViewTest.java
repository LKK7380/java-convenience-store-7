package store.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.Order;
import store.domain.Product;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("OutputView 테스트")
class OutputViewTest {
    private OutputView outputView;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        outputView = new OutputView();
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    @DisplayName("상품 목록 출력 테스트")
    void printProductsTest() {
        Map<String, Product> products = new LinkedHashMap<>();
        products.put("콜라", new Product("콜라", 1000, 10, "탄산2+1"));
        products.put("사이다", new Product("사이다", 1000, 8, "탄산2+1"));
        products.put("물", new Product("물", 500, 0, null));

        outputView.printProducts(products);

        String output = outputStream.toString();
        assertThat(output)
                .contains("- 콜라 1,000원 10개 탄산2+1")
                .contains("- 사이다 1,000원 8개 탄산2+1")
                .contains("- 물 500원 재고 없음");
    }

    @Test
    @DisplayName("영수증 출력 테스트")
    void printReceiptTest() {
        List<Order> orders = Arrays.asList(
                new Order("콜라", 3),
                new Order("사이다", 2)
        );
        Map<String, Integer> orderPrices = new HashMap<>();
        orderPrices.put("콜라", 3000);
        orderPrices.put("사이다", 2000);

        Map<String, Integer> freeItems = new HashMap<>();
        freeItems.put("콜라", 1);

        outputView.printReceipt(orders, orderPrices, freeItems, 5000, 1000, 800);

        String output = outputStream.toString();
        assertThat(output)
                .contains("==============W 편의점================")
                .contains("상품명\t\t수량\t금액")
                .contains("콜라\t\t3\t3,000")
                .contains("사이다\t\t2\t2,000")
                .contains("=============증\t정===============")
                .contains("콜라\t\t1")
                .contains("총구매액\t\t5\t5,000")
                .contains("행사할인\t\t\t-1,000")
                .contains("멤버십할인\t\t\t-800")
                .contains("내실돈\t\t\t3,200");
    }

    @Test
    @DisplayName("시작 메시지 출력 테스트")
    void printWelcomeMessageTest() {
        outputView.printWelcomeMessage();

        String output = outputStream.toString();
        assertThat(output)
                .contains("안녕하세요. W편의점입니다.")
                .contains("현재 보유하고 있는 상품입니다.");
    }
}