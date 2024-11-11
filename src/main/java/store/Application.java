package store;

import store.controller.StoreController;
import store.domain.Inventory;
import store.domain.Product;
import store.domain.Promotion;
import store.util.FileReader;

import java.util.List;

public class Application {
    private static final String PRODUCTS_FILE = "src/main/resources/products.md";
    private static final String PROMOTIONS_FILE = "src/main/resources/promotions.md";

    public static void main(String[] args) {
        try {
            FileReader fileReader = new FileReader();
            List<Product> products = fileReader.readProducts(PRODUCTS_FILE);
            List<Promotion> promotions = fileReader.readPromotions(PROMOTIONS_FILE);

            for (Product product : products) {
                if (product.getPromotionName() != null && !product.getPromotionName().isEmpty()) {
                    for (Promotion promotion : promotions) {
                        if (promotion.getName().equals(product.getPromotionName())) {
                            product.setPromotionQuantity(product.getQuantity());
                            break;
                        }
                    }
                }
            }

            Inventory inventory = new Inventory(products);
            StoreController controller = new StoreController(inventory);
            controller.run();

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}