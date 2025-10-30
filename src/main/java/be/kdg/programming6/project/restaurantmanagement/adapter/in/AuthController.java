package be.kdg.programming6.project.restaurantmanagement.adapter.in;

import be.kdg.programming6.project.restaurantmanagement.adapter.in.request.AuthResponse;
import be.kdg.programming6.project.restaurantmanagement.adapter.in.request.LoginRequest;
import be.kdg.programming6.project.restaurantmanagement.adapter.in.request.SignupRequest;
import be.kdg.programming6.project.restaurantmanagement.domain.Owner;
import be.kdg.programming6.project.restaurantmanagement.domain.valueobject.EmailAddress;
import be.kdg.programming6.project.restaurantmanagement.port.in.LoginCommand;
import be.kdg.programming6.project.restaurantmanagement.port.in.LoginUseCase;
import be.kdg.programming6.project.restaurantmanagement.port.in.SignupCommand;
import be.kdg.programming6.project.restaurantmanagement.port.in.SignupUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final SignupUseCase signupUseCase;
    private final LoginUseCase loginUseCase;

    public AuthController(SignupUseCase signupUseCase, LoginUseCase loginUseCase) {
        this.signupUseCase = signupUseCase;
        this.loginUseCase = loginUseCase;
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@RequestBody SignupRequest request) {
        SignupCommand command = new SignupCommand(
                EmailAddress.of(request.email()),
                request.password(),
                request.fullName()
        );
        Owner owner = signupUseCase.signup(command);

        return ResponseEntity.ok(new AuthResponse(
                owner.getOwnerId().uuid(),
                owner.getEmail().email(),
                owner.getFullName(),
                null // No restaurant yet
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        LoginCommand command = new LoginCommand(EmailAddress.of(request.email()), request.password());
        AuthResponse response = loginUseCase.login(command);

        return ResponseEntity.ok(response);
    }
}