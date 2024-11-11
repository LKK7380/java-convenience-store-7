package store.util;

import camp.nextstep.edu.missionutils.DateTimes;
import store.domain.Product;
import store.domain.Promotion;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileReader {
    private static final String COMMA = ",";

    public List<Product> readProducts(String filename) {
        List<Product> products = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(filename))) {
            skipHeader(reader);
            String line;
            while ((line = reader.readLine()) != null) {
                if(!line.isBlank()) {
                    products.add(createProduct(line));
                }
            }
            return products;
        } catch (IOException e) {
            throw new IllegalStateException("상품 파일을 읽을 수 없습니다.", e);
        }
    }

    private Product createProduct(String line) {
        String[] values = line.split(COMMA);
        return new Product(
                values[0],
                Integer.parseInt(values[1]),
                Integer.parseInt(values[2]),
                values[3].equals("null") ? null : values[3]
        );
    }

    public List<Promotion> readPromotions(String filename) {
        List<Promotion> promotions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(filename))) {
            skipHeader(reader);
            String line;
            while ((line = reader.readLine()) != null) {
                if(!line.isBlank()) {
                    promotions.add(createPromotion(line));
                }
            }
            return promotions;
        } catch (IOException e) {
            throw new IllegalStateException("프로모션 파일을 읽을 수 없습니다.", e);
        }
    }

    private Promotion createPromotion(String line) {
        String[] values = line.split(COMMA);
        return new Promotion(
                values[0],
                DateTimes.now(),
                DateTimes.now().plusMonths(1),
                Integer.parseInt(values[1])
        );
    }

    private void skipHeader(BufferedReader reader) throws IOException {
        reader.readLine();
    }
}