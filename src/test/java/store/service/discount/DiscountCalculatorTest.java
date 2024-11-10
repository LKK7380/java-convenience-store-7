package store.service.discount;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.Order;
import store.domain.Product;
import store.domain.Promotion;
import store.service.OrderCalculator;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class DiscountCalculatorTest {
    @Test
    @DisplayName("프로모션이 적용된 상품은 멤버십 할인이 적용되지 않는다")
    void calculatePromotionDiscount() {
        Product cola = new Product("콜라", 1000, 10, "탄산2+1");
        cola.setPromotionQuantity(10);
        Promotion promotion = new Promotion("탄산2+1",
                LocalDateTime.of(2024, 1, 1, 0, 0),
                LocalDateTime.of(2024, 12, 31, 23, 59),
                2);
        Order order = new Order("콜라", 6);
        DiscountCalculator calculator = new DiscountCalculator(new OrderCalculator());
        int finalPrice = calculator.calculateFinalPrice(order, cola, promotion, true);
        assertThat(finalPrice).isEqualTo(4000);
    }

    @Test
    @DisplayName("프로모션이 없는 상품은 멤버십으로 30% 할인받는다")
    void calculateMembershipDiscount() {
        Product cola = new Product("콜라", 1000, 10, null);
        Promotion noPromotion = new Promotion("없음",
                LocalDateTime.of(2024, 1, 1, 0, 0),
                LocalDateTime.of(2024, 12, 31, 23, 59),
                1);
        Order order = new Order("콜라", 6);
        DiscountCalculator calculator = new DiscountCalculator(new OrderCalculator());
        int finalPrice = calculator.calculateFinalPrice(order, cola, noPromotion, true);
        assertThat(finalPrice).isEqualTo(4200);
    }

    @Test
    @DisplayName("멤버십 할인은 최대 8000원까지만 적용된다")
    void calculateMaxMembershipDiscount() {
        Product lunchBox = new Product("정식도시락", 6400, 8, null);
        Promotion noPromotion = new Promotion("없음",
                LocalDateTime.of(2024, 1, 1, 0, 0),
                LocalDateTime.of(2024, 12, 31, 23, 59),
                1);
        Order order = new Order("정식도시락", 5);
        DiscountCalculator calculator = new DiscountCalculator(new OrderCalculator());
        int finalPrice = calculator.calculateFinalPrice(order, lunchBox, noPromotion, true);
        assertThat(finalPrice).isEqualTo(24000);
    }
}