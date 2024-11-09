package store.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PromotionTest {
    @Test
    @DisplayName("프로모션을 생성한다")
    void createPromotion() {
        String name = "탄산2+1";
        LocalDateTime startDate = LocalDateTime.of(2024, 1, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 12, 31, 23, 59);
        int buyQuantity = 2;

        Promotion promotion = new Promotion(name, startDate, endDate, buyQuantity);

        assertThat(promotion.getName()).isEqualTo(name);
        assertThat(promotion.getRequiredQuantity()).isEqualTo(buyQuantity);
    }

    @Test
    @DisplayName("프로모션 시작일이 종료일보다 늦으면 예외가 발생한다")
    void validateDate() {
        LocalDateTime startDate = LocalDateTime.of(2024, 12, 31, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 1, 1, 0, 0);

        assertThatThrownBy(() -> new Promotion("탄산2+1", startDate, endDate, 2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 시작일이 종료일보다 늦을 수 없습니다.");
    }

    @Test
    @DisplayName("구매 필요 수량이 1 미만이면 예외가 발생한다")
    void validateBuyQuantity() {
        LocalDateTime startDate = LocalDateTime.of(2024, 1, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 12, 31, 23, 59);

        assertThatThrownBy(() -> new Promotion("탄산2+1", startDate, endDate, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 구매 필요 수량은 1 이상이어야 합니다.");
    }

    @Test
    @DisplayName("프로모션 이름이 null이면 예외가 발생한다")
    void validateNullName() {
        LocalDateTime startDate = LocalDateTime.of(2024, 1, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 12, 31, 23, 59);

        assertThatThrownBy(() -> new Promotion(null, startDate, endDate, 2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 프로모션 이름은 null일 수 없습니다.");
    }

    @Test
    @DisplayName("프로모션 이름이 빈 문자열이면 예외가 발생한다")
    void validateEmptyName() {
        LocalDateTime startDate = LocalDateTime.of(2024, 1, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 12, 31, 23, 59);

        assertThatThrownBy(() -> new Promotion("", startDate, endDate, 2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 프로모션 이름은 빈 값일 수 없습니다.");
    }

    @Test
    @DisplayName("시작일이 null이면 예외가 발생한다")
    void validateNullStartDate() {
        LocalDateTime endDate = LocalDateTime.of(2024, 12, 31, 23, 59);

        assertThatThrownBy(() -> new Promotion("탄산2+1", null, endDate, 2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 시작일은 null일 수 없습니다.");
    }

    @Test
    @DisplayName("종료일이 null이면 예외가 발생한다")
    void validateNullEndDate() {
        LocalDateTime startDate = LocalDateTime.of(2024, 1, 1, 0, 0);

        assertThatThrownBy(() -> new Promotion("탄산2+1", startDate, null, 2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 종료일은 null일 수 없습니다.");
    }

    @Test
    @DisplayName("프로모션 정책에 따라 할인 수량을 계산한다")
    void calculateDiscountQuantity() {
        LocalDateTime startDate = LocalDateTime.of(2024, 1, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 12, 31, 23, 59);
        Promotion promotion = new Promotion("탄산2+1", startDate, endDate, 2);

        assertThat(promotion.calculateDiscountQuantity(6)).isEqualTo(2);
    }
}