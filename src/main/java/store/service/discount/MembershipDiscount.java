package store.service.discount;

public class MembershipDiscount {
    public int calculate(int price) {
        int discountAmount = (int) (price * 0.3);
        return Math.min(discountAmount, 8000);
    }
}