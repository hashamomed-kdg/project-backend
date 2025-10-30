package be.kdg.programming6.project.restaurantmanagement.core;

import be.kdg.programming6.project.restaurantmanagement.domain.Owner;
import be.kdg.programming6.project.restaurantmanagement.domain.valueobject.OwnerId;
import be.kdg.programming6.project.restaurantmanagement.port.in.SignupCommand;
import be.kdg.programming6.project.restaurantmanagement.port.in.SignupUseCase;
import be.kdg.programming6.project.restaurantmanagement.port.out.LoadOwnerPort;
import be.kdg.programming6.project.restaurantmanagement.port.out.SaveOwnerPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class SignupUseCaseImpl implements SignupUseCase {

    private final LoadOwnerPort loadOwnerPort;
    private final SaveOwnerPort saveOwnerPort;


    public SignupUseCaseImpl(LoadOwnerPort loadOwnerPort, SaveOwnerPort saveOwnerPort) {
        this.loadOwnerPort = loadOwnerPort;
        this.saveOwnerPort = saveOwnerPort;
    }


    @Override
    @Transactional
    public Owner signup(SignupCommand command) {
        if (loadOwnerPort.loadOwnerByEmail(command.email().email()).isPresent()) {
            throw new IllegalArgumentException("Email already registered");
        }

        Owner owner = new Owner(
                OwnerId.create(),
                command.email(),
                command.password(),
                command.fullName()
        );

        return saveOwnerPort.saveOwner(owner);
    }
}
