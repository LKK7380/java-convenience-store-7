package store.domain.type;

import java.util.Arrays;

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

    public static PromotionDiscountPolicy of(String name) {
        return Arrays.stream(values())
                .filter(policy -> policy.name.equals(name))
                .findFirst()
                .orElse(NONE);
    }
}