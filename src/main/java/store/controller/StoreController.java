package store.controller;

import camp.nextstep.edu.missionutils.Console;
import store.domain.Inventory;
import store.domain.Order;
import store.domain.Product;
import store.domain.Promotion;
import store.service.OrderCalculator;
import store.service.discount.DiscountCalculator;
import store.view.InputView;
import store.view.OutputView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StoreController {
    private final InputView inputView;
    private final OutputView outputView;
    private final OrderCalculator orderCalculator;
    private final DiscountCalculator discountCalculator;
    private final Inventory inventory;

    public StoreController(Inventory inventory) {
        this.inventory = inventory;
        this.inputView = new InputView();
        this.outputView = new OutputView();
        this.orderCalculator = new OrderCalculator();
        this.discountCalculator = new DiscountCalculator(orderCalculator);
    }

    public void run() {
        do {
            processOrder();
        } while (askForContinueShopping());
    }

    private void processOrder() {
        Map<String, Integer> orderItems = getOrderInput();
        boolean hasMembership = getMembershipInput();
        OrderResult orderResult = createOrderResult(orderItems, hasMembership);
        processOrderResult(orderResult);
    }

    private OrderResult createOrderResult(Map<String, Integer> orderItems, boolean hasMembership) {
        List<Order> orders = new ArrayList<>();
        Map<String, Integer> freeItems = new HashMap<>();
        int totalPrice = 0;
        int promotionDiscount = 0;
        int membershipDiscount = 0;

        for (Map.Entry<String, Integer> entry : orderItems.entrySet()) {
            OrderItemResult itemResult = processOrderItem(entry, hasMembership);
            orders.add(itemResult.order());
            freeItems.putAll(itemResult.freeItems());
            totalPrice += itemResult.price();
            promotionDiscount += itemResult.promotionDiscount();
            membershipDiscount += itemResult.membershipDiscount();
        }

        return new OrderResult(orders, freeItems, totalPrice, promotionDiscount, membershipDiscount);
    }

    private OrderItemResult processOrderItem(Map.Entry<String, Integer> entry, boolean hasMembership) {
        Order order = new Order(entry.getKey(), entry.getValue());
        Product product = inventory.getProducts().get(order.getProductName());
        Promotion promotion = new Promotion(product.getPromotionName(), null, null, 2);

        Map<String, Integer> freeItems = new HashMap<>();
        int promotionDiscount = calculatePromotionDiscount(order, product, promotion, freeItems);
        int price = discountCalculator.calculateFinalPrice(order, product, promotion, hasMembership);
        int membershipDiscount = calculateMembershipDiscount(price, hasMembership);

        inventory.decreaseStock(order.getProductName(), order.getQuantity());

        return new OrderItemResult(order, freeItems, price, promotionDiscount, membershipDiscount);
    }

    private void processOrderResult(OrderResult orderResult) {
        Map<String, Integer> orderPrices = new HashMap<>();
        for (Order order : orderResult.orders()) {
            Product product = inventory.getProducts().get(order.getProductName());
            orderPrices.put(order.getProductName(), product.getPrice() * order.getQuantity());
        }

        outputView.printReceipt(
                orderResult.orders(),
                orderPrices,
                orderResult.freeItems(),
                orderResult.totalPrice(),
                orderResult.promotionDiscount(),
                orderResult.membershipDiscount()
        );
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

    private int calculatePromotionDiscount(Order order, Product product, Promotion promotion, Map<String, Integer> freeItems) {
        if (promotion != null && promotion.getPolicy().isValidPeriod(promotion)) {
            int freeQuantity = promotion.getPolicy().calculateDiscountQuantity(order.getQuantity());
            if (freeQuantity > 0) {
                freeItems.put(order.getProductName(), freeQuantity);
                return freeQuantity * product.getPrice();
            }
        }
        return 0;
    }

    private int calculateMembershipDiscount(int price, boolean hasMembership) {
        return hasMembership ? price * 30 / 100 : 0;
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

record OrderResult(
        List<Order> orders,
        Map<String, Integer> freeItems,
        int totalPrice,
        int promotionDiscount,
        int membershipDiscount
) {}

record OrderItemResult(
        Order order,
        Map<String, Integer> freeItems,
        int price,
        int promotionDiscount,
        int membershipDiscount
) {}