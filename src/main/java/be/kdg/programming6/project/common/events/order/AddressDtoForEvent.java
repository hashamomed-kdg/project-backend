package be.kdg.programming6.project.common.events.order;

public record AddressDtoForEvent(
        String street, String number, String postalCode, String city
) {
}
