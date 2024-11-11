package store.domain;

import java.util.List;
import java.util.Map;

public class Receipt {
    private final List<Order> orders;
    private final Map<String, Integer> orderPrices;
    private final Map<String, Integer> freeItems;
    private final int totalPrice;
    private final int promotionDiscount;
    private final int membershipDiscount;

    public Receipt(List<Order> orders, Map<String, Integer> orderPrices,
                   Map<String, Integer> freeItems, int totalPrice,
                   int promotionDiscount, int membershipDiscount) {
        this.orders = orders;
        this.orderPrices = orderPrices;
        this.freeItems = freeItems;
        this.totalPrice = totalPrice;
        this.promotionDiscount = promotionDiscount;
        this.membershipDiscount = membershipDiscount;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public Map<String, Integer> getOrderPrices() {
        return orderPrices;
    }

    public Map<String, Integer> getFreeItems() {
        return freeItems;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public int getPromotionDiscount() {
        return promotionDiscount;
    }

    public int getMembershipDiscount() {
        return membershipDiscount;
    }

    public int getFinalPrice() {
        return totalPrice - promotionDiscount - membershipDiscount;
    }

    public int getTotalQuantity() {
        return orders.stream()
                .mapToInt(Order::getQuantity)
                .sum();
    }
}