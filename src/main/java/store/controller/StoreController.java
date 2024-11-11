package store.controller;

import camp.nextstep.edu.missionutils.Console;
import store.domain.*;
import store.view.InputView;
import store.view.OutputView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StoreController {
    private final InputView inputView;
    private final OutputView outputView;
    private final Inventory inventory;

    public StoreController(Inventory inventory) {
        this.inventory = inventory;
        this.inputView = new InputView();
        this.outputView = new OutputView();
    }

    public void run() {
        outputView.printWelcomeMessage();
        outputView.printProducts(inventory.getProducts());

        while(true) {
            try {
                processOrder();
                if (!askForContinueShopping()) {
                    break;
                }
                outputView.printProducts(inventory.getProducts());
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void processOrder() {
        Map<String, Integer> orderItems = getOrderInput();
        validateOrderQuantities(orderItems);
        boolean hasMembership = getMembershipInput();

        List<Order> orders = new ArrayList<>();
        Map<String, Integer> orderPrices = new HashMap<>();
        Map<String, Integer> freeItems = new HashMap<>();
        int totalPrice = 0;
        int promotionDiscount = 0;

        for (Map.Entry<String, Integer> entry : orderItems.entrySet()) {
            Order order = new Order(entry.getKey(), entry.getValue());
            Product product = inventory.getProducts().get(order.getProductName());

            int orderPrice = calculateOrderPrice(product, order.getQuantity());
            orders.add(order);
            orderPrices.put(order.getProductName(), orderPrice);
            totalPrice += orderPrice;

            processPromotionItems(order, product, freeItems);
            promotionDiscount = calculatePromotionDiscount(product, freeItems);

            inventory.decreaseStock(order.getProductName(), order.getQuantity());
        }

        int membershipDiscount = calculateMembershipDiscount(totalPrice - promotionDiscount, hasMembership);

        Receipt receipt = new Receipt(
                orders,
                orderPrices,
                freeItems,
                totalPrice,
                promotionDiscount,
                membershipDiscount
        );

        outputView.printReceipt(receipt);
    }

    private void validateOrderQuantities(Map<String, Integer> orderItems) {
        for (Map.Entry<String, Integer> entry : orderItems.entrySet()) {
            if (!inventory.hasEnoughStock(entry.getKey(), entry.getValue())) {
                throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
            }
        }
    }

    private Map<String, Integer> getOrderInput() {
        while (true) {
            try {
                String input = inputView.readItem();
                return inputView.parseOrderInput(input);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private boolean getMembershipInput() {
        while (true) {
            try {
                System.out.println("멤버십 할인을 받으시겠습니까? (Y/N)");
                return inputView.parseYesNoInput(Console.readLine());
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private int calculateOrderPrice(Product product, int quantity) {
        return product.getPrice() * quantity;
    }

    private void processPromotionItems(Order order, Product product, Map<String, Integer> freeItems) {
        if (product.getPromotionQuantity() > 0) {
            int freeQuantity = order.getQuantity() / 3;
            if (freeQuantity > 0) {
                freeItems.put(product.getName(), freeQuantity);
            }
        }
    }

    private int calculatePromotionDiscount(Product product, Map<String, Integer> freeItems) {
        Integer freeQuantity = freeItems.get(product.getName());
        if (freeQuantity != null) {
            return product.getPrice() * freeQuantity;
        }
        return 0;
    }

    private int calculateMembershipDiscount(int price, boolean hasMembership) {
        if (!hasMembership) {
            return 0;
        }
        int discount = price * 30 / 100;
        return Math.min(discount, 8000);
    }

    private boolean askForContinueShopping() {
        while (true) {
            try {
                System.out.println("구매를 계속하시겠습니까? (Y/N)");
                return inputView.parseYesNoInput(Console.readLine());
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}