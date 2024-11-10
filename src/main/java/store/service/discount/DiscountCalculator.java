package store.service.discount;

import store.domain.Order;
import store.domain.Product;
import store.domain.Promotion;
import store.domain.type.MembershipType;
import store.service.OrderCalculator;

public class DiscountCalculator {
    private final OrderCalculator orderCalculator;

    public DiscountCalculator(OrderCalculator orderCalculator) {
        this.orderCalculator = orderCalculator;
    }

    public int calculateFinalPrice(Order order, Product product, Promotion promotion, boolean hasMembership) {
        int totalPrice = orderCalculator.calculateTotalPrice(order, product);

        boolean isValidPromotion = promotion.getPolicy().isValidPeriod(promotion);
        boolean hasEnoughStock = product.getPromotionQuantity() >= order.getQuantity();
        boolean isPromotionApplied = false;

        if (isValidPromotion && hasEnoughStock) {
            int discountQuantity = promotion.getPolicy().calculateDiscountQuantity(order.getQuantity());
            totalPrice -= (discountQuantity * product.getPrice());
            product.decreasePromotionQuantity(order.getQuantity());
            isPromotionApplied = true;
        }

        if (hasMembership && !isPromotionApplied) {
            MembershipType membershipType = MembershipType.REGULAR;
            int membershipDiscount = membershipType.calculateDiscount(totalPrice);
            totalPrice -= membershipDiscount;
        }

        return totalPrice;
    }
}