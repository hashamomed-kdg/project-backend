package be.kdg.programming6.project.common.events.order;


import java.util.Random;

public record RandomCoordinates(
        double lat,
        double lng
) {

    static Random random = new Random();

    public RandomCoordinates() {
        this(random.nextDouble(), random.nextDouble());
    }

}
