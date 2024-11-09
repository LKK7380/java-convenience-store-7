package store.domain.type;

public enum MembershipType {
    REGULAR(30, 8000),
    NONE(0, 0);

    private final int discountRate;
    private final int maxDiscount;

    MembershipType(int discountRate, int maxDiscount) {
        this.discountRate = discountRate;
        this.maxDiscount = maxDiscount;
    }

    public int calculateDiscount(int price) {
        int discount = (price * discountRate) / 100;
        return Math.min(discount, maxDiscount);
    }
}