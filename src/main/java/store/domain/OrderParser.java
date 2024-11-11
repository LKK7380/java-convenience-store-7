package store.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrderParser {
    private static final Pattern ORDER_PATTERN = Pattern.compile("\\[(.*?)-(.*?)\\]");
    private static final String COMMA = ",";

    public Map<String, Integer> parse(String input) {
        validateBasicFormat(input);
        Map<String, Integer> orders = new HashMap<>();
        String[] items = input.split(COMMA);

        for (String item : items) {
            parseItem(item.trim(), orders);
        }

        validateNotEmpty(orders);
        return orders;
    }

    private void validateBasicFormat(String input) {
        boolean isValid = input.matches("(\\[.*?-.*?\\],)*\\[.*?-.*?\\]");
        if (!isValid) {
            throw new IllegalArgumentException();
        }
    }

    private void parseItem(String item, Map<String, Integer> orders) {
        Matcher matcher = ORDER_PATTERN.matcher(item);
        if (!matcher.matches()) {
            throw new IllegalArgumentException();
        }

        String name = matcher.group(1);
        String quantityStr = matcher.group(2);
        orders.put(name, parseQuantity(quantityStr));
    }

    private int parseQuantity(String quantityStr) {
        try {
            int quantity = Integer.parseInt(quantityStr);
            if (quantity < 1) {
                throw new IllegalArgumentException();
            }
            return quantity;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException();
        }
    }

    private void validateNotEmpty(Map<String, Integer> orders) {
        if (orders.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }
}