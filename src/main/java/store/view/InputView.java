package store.view;

import store.domain.OrderParser;

import java.util.Map;

public class InputView {
    private static final String ERROR_MESSAGE = "[ERROR] 올바르지 않은 형식으로 입력했습니다";
    private final OrderParser orderParser = new OrderParser();

    public Map<String, Integer> parseOrderInput(String input) {
        try {
            return orderParser.parse(input);
        } catch (Exception e) {
            throw new IllegalArgumentException(ERROR_MESSAGE);
        }
    }

    public boolean parseYesNoInput(String input) {
        if (!input.equals("Y") && !input.equals("N")) {
            throw new IllegalArgumentException(ERROR_MESSAGE);
        }
        return input.equals("Y");
    }
}