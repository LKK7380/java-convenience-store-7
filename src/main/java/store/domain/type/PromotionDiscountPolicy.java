package store.domain.type;

import store.domain.Promotion;
import java.time.LocalDateTime;

public enum PromotionDiscountPolicy {
    TWO_PLUS_ONE("탄산2+1", 2, 1),
    MD_RECOMMENDED("MD추천상품", 1, 1),
    FLASH_SALE("반짝할인", 1, 1),
    NONE("없음", 0, 0);

    private final String name;
    private final int buyQuantity;
    private final int freeQuantity;

    PromotionDiscountPolicy(String name, int buyQuantity, int freeQuantity) {
        this.name = name;
        this.buyQuantity = buyQuantity;
        this.freeQuantity = freeQuantity;
    }

    public int calculateDiscountQuantity(int quantity) {
        if (this == NONE) {
            return 0;
        }
        return quantity / (buyQuantity + freeQuantity);
    }

    public boolean isValidPeriod(Promotion promotion) {
        LocalDateTime now = LocalDateTime.now();
        return promotion.getStartDate().isBefore(now) &&
                promotion.getEndDate().isAfter(now);
    }

    public static PromotionDiscountPolicy of(String name) {
        return java.util.Arrays.stream(values())
                .filter(policy -> policy.name.equals(name))
                .findFirst()
                .orElse(NONE);
    }
}