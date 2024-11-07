package store.domain;

import java.time.LocalDateTime;

public class Promotion {
    private final String name;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final int requiredQuantity;

    public Promotion(String name, LocalDateTime startDate, LocalDateTime endDate, int requiredQuantity) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.requiredQuantity = requiredQuantity;
    }

    public String getName() {
        return name;
    }

    public int getRequiredQuantity() {
        return requiredQuantity;
    }
}