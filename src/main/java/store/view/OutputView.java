package store.view;

import store.domain.Product;
import store.domain.Order;
import store.domain.Receipt;

import java.util.List;
import java.util.Map;

public class OutputView {
    public void printWelcomeMessage() {
        System.out.println("안녕하세요. W편의점입니다.");
        System.out.println("현재 보유하고 있는 상품입니다.\n");
    }

    public void printProducts(Map<String, Product> products) {
        for (Product product : products.values()) {
            printProduct(product);
        }
        System.out.println();
    }

    private void printProduct(Product product) {
        String promotionInfo = product.getPromotionName() == null ? "" : " " + product.getPromotionName();
        if (product.getQuantity() == 0) {
            System.out.printf("- %s %,d원 재고 없음%s\n",
                    product.getName(), product.getPrice(), promotionInfo);
            return;
        }
        System.out.printf("- %s %,d원 %d개%s\n",
                product.getName(), product.getPrice(), product.getQuantity(), promotionInfo);
    }

    public void printReceipt(Receipt receipt) {
        System.out.println("==============W 편의점================");
        System.out.println("상품명\t\t수량\t금액");

        for (Order order : receipt.getOrders()) {
            System.out.printf("%s\t\t%d\t%,d\n",
                    order.getProductName(),
                    order.getQuantity(),
                    receipt.getOrderPrices().get(order.getProductName()));
        }

        if (!receipt.getFreeItems().isEmpty()) {
            System.out.println("=============증\t정===============");
            for (Map.Entry<String, Integer> entry : receipt.getFreeItems().entrySet()) {
                System.out.printf("%s\t\t%d\n", entry.getKey(), entry.getValue());
            }
        }

        System.out.println("====================================");
        System.out.printf("총구매액\t\t%d\t%,d\n", receipt.getTotalQuantity(), receipt.getTotalPrice());
        System.out.printf("행사할인\t\t\t-%,d\n", receipt.getPromotionDiscount());
        System.out.printf("멤버십할인\t\t\t-%,d\n", receipt.getMembershipDiscount());
        System.out.printf("내실돈\t\t\t%,d\n", receipt.getFinalPrice());
    }

    public void printPromotionAddMessage(String productName) {
        System.out.printf("현재 %s은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)\n",
                productName);
    }

    public void printPromotionInsufficientMessage(String productName, int quantity) {
        System.out.printf("현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)\n",
                productName, quantity);
    }
}