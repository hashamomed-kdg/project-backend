package be.kdg.programming6.project.ordermanagement.domain.valueobject;

import java.math.BigDecimal;

public record OrderItem(
        DishId dishId,
        String name,
        BigDecimal price,
        long quantity
) {
    public BigDecimal getSubTotal() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }
}
