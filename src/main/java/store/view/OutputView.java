package store.view;

import java.util.ArrayList;
import store.domain.Product;
import store.domain.Order;
import java.util.List;
import java.util.Map;

public class OutputView {
    public void printWelcomeMessage() {
        System.out.println("안녕하세요. W편의점입니다.");
        System.out.println("현재 보유하고 있는 상품입니다.\n");
    }

    public void printProducts(Map<String, Product> products) {
        List<Product> sortedProducts = new ArrayList<>(products.values());
        for (Product product : sortedProducts) {
            printProduct(product);
        }
        System.out.println();
    }

    private void printProduct(Product product) {
        String promotionInfo = product.getPromotionName() == null ? "" : " " + product.getPromotionName();
        if (product.getQuantity() == 0) {
            System.out.printf("- %s %,d원 재고 없음%s%n",
                    product.getName(), product.getPrice(), promotionInfo);
            return;
        }
        System.out.printf("- %s %,d원 %d개%s%n",
                product.getName(), product.getPrice(), product.getQuantity(), promotionInfo);
    }

    public void printReceipt(List<Order> orders, Map<String, Integer> orderPrices,
                             Map<String, Integer> freeItems,
                             int totalPrice, int promotionDiscount, int membershipDiscount) {
        System.out.println("==============W 편의점================");
        System.out.println("상품명\t\t수량\t금액");

        int totalQuantity = printOrderDetails(orders, orderPrices);

        if (!freeItems.isEmpty()) {
            System.out.println("=============증\t정===============");
            printFreeItems(freeItems);
        }

        System.out.println("====================================");
        printPriceDetails(totalQuantity, totalPrice, promotionDiscount, membershipDiscount);
    }

    private int printOrderDetails(List<Order> orders, Map<String, Integer> orderPrices) {
        int totalQuantity = 0;
        for (Order order : orders) {
            System.out.printf("%s\t\t%d\t%,d%n",
                    order.getProductName(),
                    order.getQuantity(),
                    orderPrices.get(order.getProductName()));
            totalQuantity += order.getQuantity();
        }
        return totalQuantity;
    }

    private void printFreeItems(Map<String, Integer> freeItems) {
        for (Map.Entry<String, Integer> entry : freeItems.entrySet()) {
            System.out.printf("%s\t\t%d%n", entry.getKey(), entry.getValue());
        }
    }

    private void printPriceDetails(int totalQuantity, int totalPrice, int promotionDiscount, int membershipDiscount) {
        System.out.printf("총구매액\t\t%d\t%,d%n", totalQuantity, totalPrice);
        System.out.printf("행사할인\t\t\t-%,d%n", promotionDiscount);
        System.out.printf("멤버십할인\t\t\t-%,d%n", membershipDiscount);
        int finalPrice = totalPrice - promotionDiscount - membershipDiscount;
        System.out.printf("내실돈\t\t\t%,d%n", finalPrice);
    }
}