package store.domain;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Inventory {
    private final Map<String, Product> products;

    public Inventory(List<Product> products) {
        this.products = new LinkedHashMap<>();
        for (Product product : products) {
            this.products.put(product.getName(), product);
        }
    }

    public void decreaseStock(String productName, int quantity) {
        Product product = getProduct(productName);
        if (product.getPromotionQuantity() > 0) {
            decreasePromotionStock(product, quantity);
            return;
        }
        product.decreaseQuantity(quantity);
    }

    private void decreasePromotionStock(Product product, int quantity) {
        try {
            product.decreasePromotionQuantity(quantity);
        } catch (IllegalArgumentException e) {
            int remainingPromotionQuantity = product.getPromotionQuantity();
            product.decreasePromotionQuantity(remainingPromotionQuantity);
            product.decreaseQuantity(quantity - remainingPromotionQuantity);
        }
    }

    public boolean hasEnoughStock(String productName, int quantity) {
        Product product = getProduct(productName);
        return (product.getQuantity() + product.getPromotionQuantity()) >= quantity;
    }

    private Product getProduct(String productName) {
        if (!products.containsKey(productName)) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다");
        }
        return products.get(productName);
    }

    public Map<String, Product> getProducts() {
        return new LinkedHashMap<>(products);
    }
}