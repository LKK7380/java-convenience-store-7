package store.domain;

import java.time.LocalDateTime;

public class Promotion {
    private final String name;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final int requiredQuantity;

    public Promotion(String name, LocalDateTime startDate, LocalDateTime endDate, int requiredQuantity) {
        validateName(name);
        validateStartDate(startDate);
        validateEndDate(endDate);
        validateDate(startDate, endDate);
        validateRequiredQuantity(requiredQuantity);
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.requiredQuantity = requiredQuantity;
    }

    private void validateDate(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("[ERROR] 시작일이 종료일보다 늦을 수 없습니다.");
        }
    }

    private void validateRequiredQuantity(int requiredQuantity) {
        if (requiredQuantity < 1) {
            throw new IllegalArgumentException("[ERROR] 구매 필요 수량은 1 이상이어야 합니다.");
        }
    }

    private void validateName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("[ERROR] 프로모션 이름은 null일 수 없습니다.");
        }
        if (name.isBlank()) {
            throw new IllegalArgumentException("[ERROR] 프로모션 이름은 빈 값일 수 없습니다.");
        }
    }

    private void validateStartDate(LocalDateTime startDate) {
        if (startDate == null) {
            throw new IllegalArgumentException("[ERROR] 시작일은 null일 수 없습니다.");
        }
    }

    private void validateEndDate(LocalDateTime endDate) {
        if (endDate == null) {
            throw new IllegalArgumentException("[ERROR] 종료일은 null일 수 없습니다.");
        }
    }

    public String getName() {
        return name;
    }

    public int getRequiredQuantity() {
        return requiredQuantity;
    }
}