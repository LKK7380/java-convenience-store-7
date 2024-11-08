package store.service.discount;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.Order;
import store.domain.Product;
import store.domain.Promotion;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class PromotionDiscountTest {
    @Test
    @DisplayName("2+1 프로모션이 적용된 할인 금액을 계산한다")
    void calculatePromotionDiscount() {
        LocalDateTime startDate = LocalDateTime.of(2024, 1, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 12, 31, 23, 59);
        Product product = new Product("콜라", 1000, 10, "탄산2+1");
        Promotion promotion = new Promotion("탄산2+1", startDate, endDate, 2);
        Order order = new Order("콜라", 6);

        PromotionDiscount promotionDiscount = new PromotionDiscount();

        assertThat(promotionDiscount.calculate(order, product, promotion)).isEqualTo(2000);
    }
}