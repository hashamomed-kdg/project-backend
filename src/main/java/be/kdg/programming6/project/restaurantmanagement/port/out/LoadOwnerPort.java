package be.kdg.programming6.project.restaurantmanagement.port.out;


import be.kdg.programming6.project.restaurantmanagement.domain.Owner;
import be.kdg.programming6.project.restaurantmanagement.domain.valueobject.OwnerId;

import java.util.List;
import java.util.Optional;

public interface LoadOwnerPort {
    Optional<Owner> loadOwnerById(OwnerId ownerId);

    List<Owner> loadAllOwners();

    Optional<Owner> loadOwnerByEmail(String email);
}
