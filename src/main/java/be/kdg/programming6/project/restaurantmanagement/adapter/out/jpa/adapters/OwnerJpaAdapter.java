package be.kdg.programming6.project.restaurantmanagement.adapter.out.jpa.adapters;

import be.kdg.programming6.project.restaurantmanagement.adapter.out.jpa.entity.OwnerJpaEntity;
import be.kdg.programming6.project.restaurantmanagement.adapter.out.jpa.repository.OwnerJpaRepository;
import be.kdg.programming6.project.restaurantmanagement.domain.Owner;
import be.kdg.programming6.project.restaurantmanagement.domain.valueobject.EmailAddress;
import be.kdg.programming6.project.restaurantmanagement.domain.valueobject.OwnerId;
import be.kdg.programming6.project.restaurantmanagement.port.out.LoadOwnerPort;
import be.kdg.programming6.project.restaurantmanagement.port.out.SaveOwnerPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class OwnerJpaAdapter implements LoadOwnerPort, SaveOwnerPort {

    private final OwnerJpaRepository ownerRepo;

    public OwnerJpaAdapter(OwnerJpaRepository ownerRepo) {
        this.ownerRepo = ownerRepo;
    }

    @Override
    public Optional<Owner> loadOwnerById(OwnerId ownerId) {
        Optional <OwnerJpaEntity> ownerEntity = ownerRepo.findById(ownerId.uuid());
        if (ownerEntity.isEmpty()) {
            return Optional.empty();
        }
        Owner owner = new Owner(
                OwnerId.of(ownerEntity.get().getOwnerId()),
                EmailAddress.of(ownerEntity.get().getEmail()),
                ownerEntity.get().getPassword(),
                ownerEntity.get().getFullName()
        );
        return Optional.of(owner);
    }

    @Override
    public List<Owner> loadAllOwners() {
        List<OwnerJpaEntity> ownerEntities = ownerRepo.findAll();
        return ownerEntities.stream().map(entity -> new Owner(
                OwnerId.of(entity.getOwnerId()),
                EmailAddress.of(entity.getEmail()),
                entity.getPassword(),
                entity.getFullName()
        )).toList();
    }

    @Override
    public Optional<Owner> loadOwnerByEmail(String email) {
        Optional<OwnerJpaEntity> ownerEntity = ownerRepo.findByEmail((email));
        if (ownerEntity.isEmpty()) {
            return Optional.empty();
        }
        Owner owner = new Owner(
                OwnerId.of(ownerEntity.get().getOwnerId()),
                EmailAddress.of(ownerEntity.get().getEmail()),
                ownerEntity.get().getPassword(),
                ownerEntity.get().getFullName()
        );
        return Optional.of(owner);
    }

    @Override
    public Owner saveOwner(Owner owner) {
        OwnerJpaEntity entity = new OwnerJpaEntity(
                owner.getOwnerId().uuid(),
                owner.getEmail().email(),
                owner.getPassword(),
                owner.getFullName()
        );
        ownerRepo.save(entity);
        return owner;
    }
}
