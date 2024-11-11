package store.view;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InputViewTest {
    private static final String ERROR_MESSAGE = "[ERROR] 올바르지 않은 형식으로 입력했습니다";
    private final InputView inputView = new InputView();

    @Test
    @DisplayName("입력값이 형식에 맞으면 상품명과 수량을 파싱한다")
    void parseValidInput() {
        String input = "[콜라-3],[사이다-2]";

        Map<String, Integer> result = inputView.parseOrderInput(input);

        assertThat(result)
                .hasSize(2)
                .containsEntry("콜라", 3)
                .containsEntry("사이다", 2);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "콜라-3",
            "[콜라-3,사이다-2]",
            "[콜라-3][사이다-2]",
            "[콜라:3]",
            "[콜라-0]",
            "[콜라--1]",
            "[콜라-a]"
    })
    @DisplayName("잘못된 입력이면 예외가 발생한다")
    void validateInvalidInput(String input) {
        assertThatThrownBy(() -> inputView.parseOrderInput(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ERROR_MESSAGE);
    }

    @Test
    @DisplayName("Y 입력시 true를 반환한다")
    void validateYesInput() {
        assertThat(inputView.parseYesNoInput("Y")).isTrue();
    }

    @Test
    @DisplayName("N 입력시 false를 반환한다")
    void validateNoInput() {
        assertThat(inputView.parseYesNoInput("N")).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {"y", "n", "yes", "no", "1", "0", ""})
    @DisplayName("Y/N이 아닌 값을 입력하면 예외가 발생한다")
    void validateInvalidYesNo(String input) {
        assertThatThrownBy(() -> inputView.parseYesNoInput(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ERROR_MESSAGE);
    }
}