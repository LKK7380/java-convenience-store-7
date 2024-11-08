package store.service.discount;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MembershipDiscountTest {
    @Test
    @DisplayName("멤버십 할인 금액을 계산한다")
    void calculateMembershipDiscount() {
        MembershipDiscount membershipDiscount = new MembershipDiscount();

        assertThat(membershipDiscount.calculate(10000)).isEqualTo(3000);
    }

    @Test
    @DisplayName("멤버십 할인은 최대 8000원까지만 적용된다")
    void calculateMaxDiscount() {
        MembershipDiscount membershipDiscount = new MembershipDiscount();

        assertThat(membershipDiscount.calculate(30000)).isEqualTo(8000);
    }
}